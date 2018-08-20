package com.cg.product.StepDefinition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.Assert;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cg.product.bean.Product;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class OnlineShoppingFeatureStepDefinition {
	
	private TestRestTemplate restTemplate;
	private ResponseEntity<String> responseEntityString;
	private ResponseEntity<Product> responseEntityProduct;
	
	@Before
	public void setUpTestEnv() {
		restTemplate=new TestRestTemplate();
	}
	
	
	
	@When("^user give call to '/sayHello' service$")
	public void user_give_call_to_sayHello_service() throws Throwable {
		responseEntityString=restTemplate.getForEntity("http://localhost:9999/sayHello", String.class) ;
	}

	@Then("^user should receive service status as 'OK' and response message 'Hello World from RESTFullWebServices'$")
	public void user_should_receive_service_status_as_OK_and_response_message_Hello_World_from_RESTFullWebServices() throws Throwable {
		 Assert.assertEquals(HttpStatus.OK, responseEntityString.getStatusCode()); 
		   Assert.assertEquals("Hello World from REST controller", responseEntityString.getBody());
	}

	@When("^user submit valid product detail$")
	public void user_submit_valid_product_detail() throws Throwable {
	   Product product=getProduct();
	   MultiValueMap<String, Object> map=new LinkedMultiValueMap<>();
	   map.add("productCode", product.getProductCode());
	   map.add("name", product.getName());
	   map.add("price",Double.toString(product.getPrice()));
	   map.add("description", product.getDescription());
	   map.add("manufactureDate", product.getManufactureDate());
	   HttpHeaders headers=new HttpHeaders();
	   headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	   HttpEntity<MultiValueMap<String, Object>> httpEntity=new HttpEntity<MultiValueMap<String,Object>>(map,headers);
	   responseEntityProduct=restTemplate.postForEntity("http://localhost:9999/acceptProductDetails",httpEntity,Product.class);
	}

	@Then("^Product details successfully should add and same product details should return by services$")
	public void product_details_successfully_should_add_and_same_product_details_should_return_by_services() throws Throwable {
	    Product expectedProduct= getProduct();
	    Product actualProduct=responseEntityProduct.getBody() ;
	    Assert.assertEquals(expectedProduct, actualProduct);
	}



	private Product getProduct() {
		Product p=new Product();
		p.setProductCode("mi06");
		p.setName("Mi");
		p.setPrice(15000);
		p.setDescription("Redmi note6");
		p.setManufactureDate("2017-05-12");
		return new Product("mi08", "Mi", 15000, "Redmi note6", "2017-05-12");
	}


}
