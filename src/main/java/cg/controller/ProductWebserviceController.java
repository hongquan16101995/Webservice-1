package cg.controller;

import cg.model.Category;
import cg.model.Product;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
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

    //lấy list product theo pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> showAllPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Product> products = iProductService.findPage(pageable);
        if (!products.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //lấy list category
    @GetMapping("/cate")
    public ResponseEntity<Iterable<Category>> showAllCate() {
        Iterable<Category> categories = iProductService.fillAddCate();
        if (!categories.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
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

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file,
                                         @RequestParam("name")String name) {
        System.out.println(file.getOriginalFilename());
        System.out.println(name);
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @PostMapping("/upload1")
    public ResponseEntity<String> upload1(@RequestPart("file")MultipartFile file,
                                          @RequestPart("product") Product product) {
        System.out.println(file.getOriginalFilename());
        System.out.println(product.getName());
        product.setDescription(file.getOriginalFilename());
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);
        iProductService.save(product);
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }
}
