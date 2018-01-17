Feature: The ability for a user to send a message from the contact page.

#@web
Scenario: Send a valid message from the contact page
Given That I am an anonymous user on the homepage of the website
When I click on "Contact" button to navigate to contact page
And enter a name of "Test User"; email address of "testuser@test.com", and message of "This is a test message", and click submit message button
Then the user will be presentated with a success message of "Thank you, your message has been successfully sent to us."


