package com.shankar.service;

import io.swagger.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private Map<String,Product> products;
    
    @PostConstruct
    public void initData(){
    	products = new HashMap<String, Product>();
    }
    
    @Override
    public List<Product> listAllProducts() {
        return new ArrayList<Product>(products.values());
    }

    private String getNextCode(){
    	int nextCode = 1;
    	if(!products.isEmpty()){
    		nextCode = products.keySet().size()+1;
    	}
    	return ""+nextCode;
    }
    
	@Override
	public Product getProductByCode(String code) {
		return products.get(code);
	}

	@Override
	public Product saveProduct(Product product) {
		System.out.println("In save product"+product.getName());
        if (product != null){
        	if(product.getName() == null){
        		throw new RuntimeException("Product name is null");
        	}
            product.setCode(getNextCode());
            products.put(product.getCode(), product);

            return product;
        } else {
            throw new RuntimeException("Product is null");
        }
	}

}
