package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.createIssueRequest.CreateIssueRequest;
import model.createIssueResponses.CreateIssueMetadataResponse;
import model.createIssueResponses.Issuetype;
import utils.JsonReader;
import utils.TestContext;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import com.google.gson.Gson;

public class CreateIssueAPIStepDefinition {

	private TestContext context;
	String baseURI;
	
	public CreateIssueAPIStepDefinition(TestContext context) {
		super();
		this.context = context;
	}

	Gson gson = new Gson();
	CreateIssueMetadataResponse createMetadataResponse ;
	
	

	

	    @Given("I have the base URI")
	    public void i_have_the_base_URI() {
	       baseURI = Configuration.getBaseURI();
	        System.out.println("Base URI >>: " + baseURI);
	       
	    }

	

	
	@When("I invoke the CreateIssueMetadata API")
	public void i_invoke_the_create_issue_metadata_api() {
	    // Write code here that turns the phrase above into concrete actions
	  baseURI = "https://asharanisv.atlassian.net";
	 context.response =  given().header("Authorization","Basic YXNoYXJhbmlzdjk0QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBxZ3NlNFlKYTNhMUwtY3I3Mno3OE0xU0lsQXVaLWVNeF9uU1RIVDFsMEdqTDJrU0IwbFZpTGJhWGhRd1NBRWotWG5hYmZjSjY3ZGJpN1RINUVCUUk0U1VFVkYwMFVEai00NTBBZXIxQUtmLVRNUWkxa29jZGtEdDBwLWItSWg3MkpBQm1aWTAxVHNMbmQxWmRHbmMzbXlqU3RCY0dxNDQ5VDNWUXRIVjFPRFU9NkNGMkIzQzA=").
			 		contentType("application/json").get("/rest/api/3/issue/createmeta");
	 System.out.println("context "+context.response.getBody().toString());
	}

	@Then("I extract the projectId and {string}")
	public void i_extract_the_project_id_and_issue_type_id(String issueTypeId) {
		
		
		//Step 2 - Parse the response and store the values in CreateIssueMetadataResponse pojo class
		createMetadataResponse = context.response.as(CreateIssueMetadataResponse.class);
		
		//Step 3 - Extract the values from the pojo class using getter methods
		
		context.projectId = createMetadataResponse.getProjects().get(0).getId();
		System.out.println("projectId : "+context.projectId);
		
		List<Issuetype> issueTypes = createMetadataResponse.getProjects().get(0).getIssuetypes();
		
		for(Issuetype issuetype: issueTypes) {
			
			if(issuetype.getName().equals(issueTypeId)) {
				context.issueTypeId = issuetype.getId();
			}
		}
		
		System.out.println("issueid : "+context.issueTypeId);
		 
	}

	@When("i invoke the createIssueAPI")
	public void i_invoke_the_create_issue_api() {
	   
		//Read the json file
		JSONObject jsonObject = JsonReader.readJsonFile("CreateIssue.json");
		
		//Populate this Json into the pojo classes		
		
		CreateIssueRequest createIssueRequest = gson.fromJson(jsonObject.toString(), CreateIssueRequest.class);
		
		// set in the dynamic values
		
		createIssueRequest.getFields().getProject().setId(context.projectId);
		createIssueRequest.getFields().getIssuetype().setId(context.issueTypeId);
		
		context.response = given().header("Authorization","Basic YXNoYXJhbmlzdjk0QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBxZ3NlNFlKYTNhMUwtY3I3Mno3OE0xU0lsQXVaLWVNeF9uU1RIVDFsMEdqTDJrU0IwbFZpTGJhWGhRd1NBRWotWG5hYmZjSjY3ZGJpN1RINUVCUUk0U1VFVkYwMFVEai00NTBBZXIxQUtmLVRNUWkxa29jZGtEdDBwLWItSWg3MkpBQm1aWTAxVHNMbmQxWmRHbmMzbXlqU3RCY0dxNDQ5VDNWUXRIVjFPRFU9NkNGMkIzQzA=").
		 		contentType("application/json").body(createIssueRequest).log().all().post("/rest/api/3/issue/");
		
		
	    
	}

	@Then("verify the story id is present in response")
	public void verify_the_story_id_is_present_in_response() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("----------------id "+context.response.body().jsonPath().getString("id"));
	    Assert.assertNotNull(context.response.body().jsonPath().getString("id"));
	}
	
	@Then("validate the response with schema {string}")
	public void validateSchema(String schemaFileName) {
		//context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schemas/"+schemaFileName)));
		
		context.response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schemas/"+schemaFileName)));
	}

	
}
