Feature: An visitor of the website should be able to register as a member.

Scenario: A visitor of the website enters his/her valid details in member registration form correctly.
	Given that I am on the registration form page of the website
	And I enter my first name of "Tommas", surname of "Smith", email address of "tommas@pukkaspice.net", and password of "!abadPassW0rd"
	And I press "Log me in" button
	Then I should be taken straight to members area dashboard
