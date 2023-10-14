@ViewStoresFeature
Feature: View Stores
  As a user I should be able to view the different stores

  @viewAllStores @ignore
  Scenario: Get All Stores
    Given store API is available
    When I invoke stores api with get method
    Then the response code should be 200

	@viewSingleStoreDetails @sanity @regression
 Scenario: Get A Single Store Details
    Given store API is available
    When I invoke "stores/{id}" api with get method
    Then the response code should be 200


Scenario Outline:  Invoke the store API with limit
Given store API is available
When I invoke stores api with get method and <limit>
 Then the response code should be 200
 Examples:
 |limit|
 |1|
 |4|
 |10|
