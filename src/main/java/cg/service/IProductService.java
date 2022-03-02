package cg.service;

import cg.model.Product;

import java.util.Optional;

public interface IProductService {
    Iterable<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Long id);

    Iterable<Product> findAllByName(String name);
}
