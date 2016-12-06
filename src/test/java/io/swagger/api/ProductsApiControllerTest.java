package io.swagger.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import io.swagger.configuration.SwaggerDocumentationConfig;
import io.swagger.model.NewProduct;
import io.swagger.model.Product;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shankar.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SwaggerDocumentationConfig.class)
public class ProductsApiControllerTest {

    @Mock 
    private ProductService productService;

    @InjectMocks 
    private ProductsApiController productsApiController;

    private ObjectMapper mapper;
   
    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); 

        mockMvc = MockMvcBuilders.standaloneSetup(productsApiController).build();
        
        mapper = new ObjectMapper();
        
    }


    private List<Product> getTestProductList(){
        List<Product> products = new ArrayList<>();
        
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setCode("1");
        product1.setName("Product 1");
        
        product2.setCode("2");
        product2.setName("Product 2");
        
        products.add(product1);
        products.add(product2);
        
        return products;
    }
    
    private Product getTestProduct(){
    	Product product1 = new Product();
        product1.setCode("1");
        product1.setName("Product 1");
       
        return product1;
    }
    
    @Test
    public void testList() throws Exception{


        //Create a mock Service. 
        when(productService.listAllProducts()).thenReturn(getTestProductList()); 

        MockHttpServletRequestBuilder getRequest = get("/products");
        getRequest.header("Content-Type", MediaType.APPLICATION_JSON.toString());
        getRequest.header("Accept", MediaType.APPLICATION_JSON.toString());
        mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductByCode() throws Exception{
        String code = "1";

        
        when(productService.getProductByCode(code)).thenReturn(getTestProduct());

        MockHttpServletRequestBuilder getRequest = get("/products/1");
        getRequest.header("Content-Type", MediaType.APPLICATION_JSON.toString());
        getRequest.header("Accept", MediaType.APPLICATION_JSON.toString());

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json("{'code':'1'}"));
    }

    @Test
    public void testSave() throws Exception {
        String code = "1";
        String name = "Test 2";
        Double price = 12.00d;

        Product returnProduct = new Product();
        returnProduct.setCode(code);
        returnProduct.setName(name);
        returnProduct.setPrice(price);
        
        NewProduct newProduct = new NewProduct();
        newProduct.setName(name);
        newProduct.setPrice(price);
        String json = mapper.writeValueAsString(newProduct);
        
        when(productService.saveProduct(returnProduct)).thenReturn(returnProduct);

        MockHttpServletRequestBuilder postRequest = post("/products");
        postRequest.header("Content-Type", MediaType.APPLICATION_JSON.toString());
        postRequest.header("Accept", MediaType.APPLICATION_JSON.toString());
        postRequest.contentType(MediaType.APPLICATION_JSON).content(json);
        
        mockMvc.perform(postRequest).andExpect(status().isOk());


    }

}
