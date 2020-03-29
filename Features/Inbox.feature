Feature: Login Gmail
Description: This Feature will Test login Feature


Scenario: Successful Login with Valid Credentials
    Given Open the Browser
	When Enter URL
	And Provide Email as and password
	And click on login
	And click on Inbox
	And check Number of Email
	And check Number of New Emails
	And close Browser