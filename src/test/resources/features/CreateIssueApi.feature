Feature:Create Issue APIs Tests

#Background:
#Given I have the base URI
#When I invoke the CreateIssueMetadata API
#Then the response code should be 200


#@CreateStory
#Scenario: Test Create Issue APIs
#When i invoke the createIssueAPI 
#Then the response code should be 201
#And verify the story id is present in response 

@CreateIssue
Scenario Outline: Test Create Issue APIs
Given I have the base URI
When I invoke the CreateIssueMetadata API
Then the response code should be 200
And I extract the projectId and "<issueType>"
When i invoke the createIssueAPI 
Then the response code should be 201
And verify the story id is present in response 
 Examples:
 |issueType|
 |Story|
 |Bug|