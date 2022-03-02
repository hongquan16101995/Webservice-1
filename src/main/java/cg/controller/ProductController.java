package cg.controller;

import cg.model.Product;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @GetMapping
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Product> products = iProductService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView showOne(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("index");
        Optional<Product> product = iProductService.findById(id);
        product.ifPresent(value -> modelAndView.addObject("product", value));
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("index");
        iProductService.delete(id);
        Iterable<Product> products = iProductService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createProduct(@ModelAttribute("product") Product product) {
        ModelAndView modelAndView = new ModelAndView("create");
        iProductService.save(product);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Optional<Product> product = iProductService.findById(id);
        product.ifPresent(value -> modelAndView.addObject("product", value));
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        ModelAndView modelAndView = new ModelAndView("edit");
        product.setId(id);
        iProductService.save(product);
        return modelAndView;
    }
}
