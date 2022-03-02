package cg.repository;

import cg.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findAllByNameContaining(String name);
}
