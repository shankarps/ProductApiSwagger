package com.shankar.service;

import io.swagger.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts();

    Product getProductByCode(String code);

    Product saveProduct(Product product);
}