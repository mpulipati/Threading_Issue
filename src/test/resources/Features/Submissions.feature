#Author: manoj.pulipati@cgifederal.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Submissions
  I want to use this template for my feature file

  @Submissions @Smoke
  Scenario: Bulk Upload Submission
    Given User login to OPP
    And user navigates to "OPP_SIT" and "OPP_SIT_AppSrvOP02_20.2.5" homepage
    When user selects user type as "Applicable Manufacturer or Group Purchasing Organization"
    And user navigates to Bulk File Upload Submissions Page
    And user enters payment details
    Then user sees the confirmation message

  @Submissions @Regression
  Scenario: Manual Data Entry
    Given User login to OPP
    And user navigates to "OPP_SIT" and "OPP_SIT_AppSrvOP02_20.2.5" homepage
    When user selects user type as "Applicable Manufacturer or Group Purchasing Organization"
    And user navigates to Manual Data Entry Submissions Page
    And user submits the "General Payments" submission
    Then user verifies the submission in "General Payments" submissions

  @Ownership
  Scenario: Manual Submission-Ownership or Investment Interest
    Given User login to OPP
    And user navigates to "OPP_SIT" and "OPP_SIT_AppSrvOP02_20.2.5" homepage
    When user selects user type as "Applicable Manufacturer or Group Purchasing Organization"
    And user navigates to Manual Data Entry Submissions Page
    And user submits the "Ownership or Investment Interest" submission
    Then user verifies the submission in "Ownership or Investment Interest" submissions
