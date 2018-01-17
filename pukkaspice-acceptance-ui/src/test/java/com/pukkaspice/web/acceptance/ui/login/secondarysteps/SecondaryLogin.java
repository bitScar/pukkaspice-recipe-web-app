package com.pukkaspice.web.acceptance.ui.login.secondarysteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pukkaspice.data.DataSetup;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SecondaryLogin {
    private WebDriver driver = new FirefoxDriver();

    @Before
    public void before() throws Exception {
        DataSetup dataSetup = new DataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", false);
        dataSetup.doSetup();
    }

    @After
    public void after() {
         driver.close();
    }
    
    @Given("^I am that I am on the secondary login page of the website$")
    public void i_am_that_I_am_on_the_secondary_login_page_of_the_website() throws Throwable {
        driver.get("http://localhost:8080/secure/login");
    }
    
    

    @Given("^I enter a valid email address of \"([^\"]*)\"$")
    public void i_enter_a_valid_email_address_of(String validEmailAddress) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='emailAddress']")).sendKeys(validEmailAddress);
    }

    @Given("^I enter a valid password of \"([^\"]*)\"$")
    public void i_enter_a_valid_password_of(String validPassword) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='password']")).sendKeys(validPassword);
    }

    @Given("^I click the \"([^\"]*)\" button$")
    public void i_click_the_button(String loginButtonText) throws Throwable {
        driver.findElement(By.xpath("(//button[text()[contains(.,'" + loginButtonText + "')]])[2]")).click();
    }

    @Then("^I should be taken to members area dashboard$")
    public void i_should_be_taken_to_members_area_dashboard() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken to members area dashboard but wasn't.", "http://localhost:8080/secure/member/dashboard", currentUrl);
    }
    
    
    
    @Given("^I enter an invalid email address of \"([^\"]*)\"$")
    public void i_enter_an_invalid_email_address_of(String inValidEmailAddress) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='emailAddress']")).sendKeys(inValidEmailAddress);
    }

    @Then("^An error message of \"([^\"]*)\" should be displayed\\.$")
    public void an_error_message_of_should_be_displayed(String errorMessage) throws Throwable {
        String text = driver.findElement(By.className("alert")).getText();

        assertTrue("Expected correct error message to contain specified error message", text.contains(errorMessage));
    }

    @Given("^I enter an invalid password of \"([^\"]*)\"$")
    public void i_enter_an_invalid_password_of(String inValidPassword) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='password']")).sendKeys(inValidPassword);
    }


    @When("^I click on the link \"([^\"]*)\"$")
    public void i_click_on_the(String resetPasswordLinkText) throws Throwable {
        driver.findElement(By.linkText(resetPasswordLinkText)).click();
    }

    @Then("^I should be taken to a page that they can request to reset their password$")
    public void i_should_be_taken_to_a_page_that_they_can_request_to_reset_their_password() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken password reset page but wasn't.", "http://localhost:8080/secure/requestresetpassword", currentUrl);
    }
}
