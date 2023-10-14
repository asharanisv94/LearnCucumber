package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.TestContext;

import org.junit.*;

import static io.restassured.RestAssured.*;


public class ViewStoreStepDefinition {

	private TestContext context;
	
	public ViewStoreStepDefinition(TestContext context) {
		super();
		this.context = context;
	}

	@Given("store API is available")
	public void store_api_is_available() {
		System.out.println("Given Method");
	    baseURI = "http://localhost:3030/";
	}

	@When("I invoke stores api with get method")
	public void i_invoke_stores_api_with_get_method() {
		context.response =  given().log().all().when().get("stores");
		
	    System.out.println("When Method");
	}

	//Validate the response code
	@Then("the response code should be {int}")
	public void the_response_code_should_be(Integer int1) {
	   System.out.println("Then Method");
	   System.out.println("Context.response in ViewStoreStepDefinition : "+ context.response.asString());
	   
	   Assert.assertEquals(Long.toString(context.response.statusCode()), Long.toString(int1));
	}
	
	@When("I invoke {string} api with get method")
	public void invokeSingleStoreApi(String endpoint) {
		
		context.response = given().log().all().when().get(endpoint,4);
		
	}
	
	@When("I invoke stores api with get method and {int}")
	public void i_invoke_stores_api_with_get_method(int limit) {
		context.response = (Response) given().log().all().queryParam("$limit",limit).when().get("stores");
		
	}
	
	@When("I invoke stores api with get method for single store")
	public void invokeGetMethodForSingleStore() {
	
		context.response = given().get("stores/{id}",context.storeId);
	}

}
