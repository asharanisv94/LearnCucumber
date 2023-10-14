@Stores
Feature: Scenarios for Stores API

Background: Create a store and save its store id
Given store API is available
And populate the request with json from "CreateStoreRequest.json"
When I invoke stores api with post method
Then the response code should be 201
And extract the storeId
			
Scenario: Verify single store details using its id
Given store API is available
When I invoke stores api with get method for single store
Then the response code should be 200

#Scenario: Update a store using its id

Scenario: Delete a store using its id
Given store API is available
When I invoke stores api with delete method for single store
Then the response code should be 200
When I invoke stores api with get method for single store
Then the response code should be 404