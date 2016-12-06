package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.NewProduct;
import io.swagger.model.Product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-06T00:28:46.116Z")

@Api(value = "products", description = "the products API")
public interface ProductsApi {

    @ApiOperation(value = "", notes = "Creates a new product.", response = Product.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "created product", response = Product.class),
        @ApiResponse(code = 400, message = "Invalid parameters provided", response = Product.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = Product.class)
        })
    @RequestMapping(value = "/products",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Product> addProduct(@ApiParam(value = "Product object" ,required=true ) @RequestBody NewProduct product);


    @ApiOperation(value = "", notes = "Returns a product based on unique ID (code)", response = Product.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "product object", response = Product.class),
        @ApiResponse(code = 404, message = "Product not found", response = Product.class)  })
    @RequestMapping(value = "/products/{code}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<Product> findProductByCode(@ApiParam(value = "code (unique ID) of product to get",required=true ) @PathVariable("code") String code);


    @ApiOperation(value = "", notes = "Returns all products from the system", response = Product.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "product response", response = Product.class),
        @ApiResponse(code = 200, message = "unexpected error", response = Product.class) })
    @RequestMapping(value = "/products",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<List<Product>> findProducts(@ApiParam(value = "tags to filter products by") @RequestParam(value = "tag", required = false) String tag);

}
