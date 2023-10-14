@myfeature
Feature: Create Store Scenarios

  @myscenario 
  Scenario Outline: Create Store using input values from examples
   Given store API is available
   And create request for the method using following values
   |name|address|city|state|zip|
   |<name>|<address>|<city>|<state>|<zip>|
   When I invoke stores api with post method
	Then the response code should be 201
    
   Examples:
   |name|address|city|state|zip|
   |BestBuy|Bramlea City Centre|Brampton|ON|LC3 X8R|
    |BestBuy|Square one|Mississauga|ON|LU9 X8R|
    
    @sanity
    Scenario: Create Store using input from json file
     Given store API is available
     And populate the request with json from "CreateStoreRequest.json"
      When I invoke stores api with post method
			Then the response code should be 201