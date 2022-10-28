package com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	Product findByCode(@Param("code") String code);

    @Override
    List<Product> findAll();
}
