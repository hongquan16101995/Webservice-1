package cg.service.impl;

import cg.model.Category;
import cg.model.Product;
import cg.repository.ICategoryRepository;
import cg.repository.IProductRepository;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Override
    public Iterable<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> findAllByName(String name) {
        return iProductRepository.findAllByNameContaining(name);
    }

    @Override
    public Iterable<Category> fillAddCate() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Page<Product> findPage(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }
}
