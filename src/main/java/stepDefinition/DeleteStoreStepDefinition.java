package stepDefinition;

import io.cucumber.java.en.When;
import utils.TestContext;
import static io.restassured.RestAssured.*;

public class DeleteStoreStepDefinition {

	private TestContext context;
	
	
	public DeleteStoreStepDefinition(TestContext testContext) {
		this.context=testContext;
	}


	@When("I invoke stores api with delete method for single store")
	public void deleteStore() {
		context.response = given().delete("stores/{id}",context.storeId);
	}
}
