Feature: Member using secondary login

Background:
	Given I am that I am on the secondary login page of the website 

Scenario: A member trying to login with valid credentials
	And I enter a valid email address of "chris@codecrocodile.com"
	And I enter a valid password of "doggydad"
	And I click the "Log Me In" button
	Then I should be taken to members area dashboard
	
Scenario: A member trying to login with valid password, but invalid email address
	And I enter an invalid email address of "chris@invalid.com"
	And I enter a valid password of "doggydad" 
	And I click the "Log Me In" button
	Then An error message of "Your email address or password was incorrect. Please try again or reset your password with the link provided below." should be displayed.

Scenario: A member trying to login with valid email address, but invlid password
	And I enter a valid email address of "chris@codecrocodile.com"
	And I enter an invalid password of "incorrectPassword"
	And I click the "Log Me In" button
	Then An error message of "Your email address or password was incorrect. Please try again or reset your password with the link provided below." should be displayed.	

Scenario: A member has forgot there password and wants to be able reset it
	When I click on the link "Have you forgot your password?"
	Then I should be taken to a page that they can request to reset their password 