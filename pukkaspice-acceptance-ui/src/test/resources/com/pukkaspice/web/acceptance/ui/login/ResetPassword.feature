Feature: Member reseting their password

Background: 
	Given I am on the request password reset link page
	
Scenario: Entering a valid email address that is correct for the member
	When I enter a valid email address of "chris@codecrocodile.com"
	And I click on the "Send" button 
	Then a message of "Please check your email for a password reset instructions." is displayed
	
Scenario: Entering a valid email address but is incorrect for the member, and doesn't exist in our database
	# We don't want to present to a hacker validity of email addresses they try so just say that it has been sent
	When I enter a valid email address of "invalid@invalid.com"
	And I click on the "Send" button 
	Then a message of "Please check your email for a password reset instructions." is displayed

Scenario: Entering an invalid email address
	When I enter an invalid email address of "invalid@invalid@.com"
	And I click on the "Send" button 
	Then a email address field message of "Invalid email address" is displayed	

Scenario: Restricting length of email address entered
	When I enter a valid email address of "more-than-200-long-111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
	Then I expect the text entered to be truncated to a length of 200