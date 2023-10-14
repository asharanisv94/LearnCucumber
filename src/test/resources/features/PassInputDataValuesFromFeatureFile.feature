
@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @tag1
  #Scenario: Execute test for 1 set of input value 
  # Given i am on login page
 #  When i enter the username and password
  # Then i should be taken to home page


  Scenario: Generate a single random number using single range 
  Given I provide 1, 100 and 2
  When i invoke the randomNumberApi 
  Then status code is 200
  And it returns 2 randomNumber
  
  Scenario Outline: Generate multiple random numbers
  Given I provide <min>, <max> and <count>
  When i invoke the randomNumberApi 
  Then status code is 200
  And it returns <count> randomNumber
  Examples:
  |min|max|count|
  |1|100|1|
  |5|50|2|
  |100|500|4|



  