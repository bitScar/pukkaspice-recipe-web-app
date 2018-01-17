Feature: User recieves reset link in email

Scenario: User clicks on link to reset password
	Given a user has been sent an email with the following link "http://localhost:8080/secure/resetpassword/ac61b5cb-dac1-4989-aece-8a987377d9a6" and clicks they click on it
	And enters valid password of "doggydad"
	And click on the "Send" button
	Then they are taken to the secondary login page with the following message displayed "Your password has been reset. Please try logging in now."
	