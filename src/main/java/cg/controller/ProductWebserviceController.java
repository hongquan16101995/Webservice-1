package cg.controller;

import cg.model.Product;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductWebserviceController {
    @Autowired
    private IProductService iProductService;

    //lấy full database
    @GetMapping
    public ResponseEntity<Iterable<Product>> showAll() {
        Iterable<Product> products = iProductService.findAll();
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //lấy 1 đối tượng theo id
    @GetMapping("/{id}")
    public ResponseEntity<Product> showOne(@PathVariable("id") Long id) {
        Optional<Product> product = iProductService.findById(id);
        if (!product.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    //tạo mới 1 đội tượng
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product productCreate = iProductService.save(product);
        return new ResponseEntity<>(productCreate, HttpStatus.CREATED);
    }

    //cập nhật 1 đối tượng có id
    @PutMapping("{id}")
    public ResponseEntity<Product> editProduct(@RequestBody Product productEdit, @PathVariable("id") Long id) {
        Optional<Product> product = iProductService.findById(id);
        if (!product.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productEdit.setId(product.get().getId());
        productEdit = iProductService.save(productEdit);
        return new ResponseEntity<>(productEdit, HttpStatus.OK);
    }

    //xóa 1 đối tượng theo id
    @DeleteMapping("{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") Long id) {
        Optional<Product> product = iProductService.findById(id);
        if (!product.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iProductService.delete(id);
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Product>> showAllByName(@RequestParam("search") String search) {
        Iterable<Product> products = iProductService.findAllByName(search);
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
