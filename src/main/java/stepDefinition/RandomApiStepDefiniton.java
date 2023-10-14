package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import gherkin.deps.com.google.gson.JsonArray;

public class RandomApiStepDefiniton {

	Response response;
	RequestSpecification request;
	
	@Given("I provide {int}, {int} and {int}")
	public void provideInputValue(int min,int max,int count) {
		baseURI="http://www.randomnumberapi.com/api/v1.0/random";
		
		request = given().queryParams("min",min, "max",max,"count",count);
	}
	
	@When("i invoke the randomNumberApi")
	public void invokeApi() {
		response = request.when().log().all().get();
		System.out.println("Print Response : ");
		response.prettyPrint();
	}
	
	@Then("status code is {int}")
	public void verifyResponseCode(int code) {
		Assert.assertEquals(response.statusCode(), code);
	}
	
	@Then("it returns {int} randomNumber")
	public void verifyResponseBody(int count) {
		
			
		List<JSONObject> responseArray = response.jsonPath().get();
		
		System.out.println(responseArray.toString());
		
		Assert.assertEquals(responseArray.size(),count);
		
		
	}
}
