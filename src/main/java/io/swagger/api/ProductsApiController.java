package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.NewProduct;
import io.swagger.model.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.shankar.service.ProductService;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-06T00:28:46.116Z")
@Controller
public class ProductsApiController implements ProductsApi {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ResponseEntity<Product> addProduct(
			@ApiParam(value = "Product object", required = true) @RequestBody NewProduct product) {
		System.out.println("In save product" + product.getName());
		Product savedProduct = null;
		try {
			if (product.getName() == null || product.getName().equals("")) {
				return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
			}

			Product productObj = new Product();
			productObj.setName(product.getName());
			productObj.setPrice(product.getPrice());
			productObj.setTags(product.getTags());

			savedProduct = productService.saveProduct(productObj);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
	}

	public ResponseEntity<Product> findProductByCode(@ApiParam(value = "code (unique ID) of product to get",required=true ) @PathVariable("code") String code) {
        
    	Product product = productService.getProductByCode(code); 
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

	public ResponseEntity<List<Product>> findProducts(
			@ApiParam(value = "tags to filter products by") @RequestParam(value = "tag", required = false) String tag) {
		
		List<Product> products = productService.listAllProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		
	}

}
