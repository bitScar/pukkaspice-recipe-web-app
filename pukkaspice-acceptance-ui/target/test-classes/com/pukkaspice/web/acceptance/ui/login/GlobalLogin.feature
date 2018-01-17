Feature: Member using global login

Background:
	Given I am that I am on the home page of the website 
	And I click on the "SIGN IN" button

Scenario: A member trying to login with valid credentials
	And I enter a valid email address of "chris@codecrocodile.com"
	And I enter a valid password of "doggydad"
	And I click the "Log Me In" button
	Then I should be taken to members area dashboard

Scenario: A member trying to login with valid password, but invalid email address
	And I enter an invalid email address of "chris@invalid.com"
	And I enter a valid password of "doggydad" 
	And I click the "Log Me In" button
	Then I should be taken to secondary login page
	Then An error message of "Your email address or password was incorrect. Please try again or reset your password with the link provided below." should be displayed.

Scenario: A member trying to login with valid email address, but invlid password
	And I enter a valid email address of "chris@codecrocodile.com"
	And I enter an invalid password of "incorrectPassword"
	And I click the "Log Me In" button
	Then I should be taken to secondary login page
	Then An error message of "Your email address or password was incorrect. Please try again or reset your password with the link provided below." should be displayed.