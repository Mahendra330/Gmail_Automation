Feature: Login Gmail
Description: This Feature will Test login Feature


Scenario: Successful Login with Valid Credentials
    Given Open the Browser
	When Enter URL
	And Provide Email as and password
	And click on login
	Then Verify Login Successful
	And Click on Profile
	Then Verify Email ID
	And close Browser
