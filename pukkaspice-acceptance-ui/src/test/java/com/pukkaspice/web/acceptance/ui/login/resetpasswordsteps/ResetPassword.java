package com.pukkaspice.web.acceptance.ui.login.resetpasswordsteps;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pukkaspice.data.DataSetup;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ResetPassword {
    
    private Logger logger = Logger.getLogger(ResetPassword.class.getName());

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

    @Given("^I am on the request password reset link page$")
    public void i_am_on_the_request_password_reset_link_page() throws Throwable {
        driver.get("http://localhost:8080/secure/requestresetpassword");
    }
    

    @When("^I enter a valid email address of \"([^\"]*)\"$")
    public void i_enter_avalid_email_address_of(String validEmailAddress) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='emailAddress']")).sendKeys(validEmailAddress);
    }
    
    @And("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String sendButtonText) throws Throwable {
        driver.findElement(By.xpath("//button[text()[contains(.,'" + sendButtonText + "')]]")).click();
    }

    @Then("^a message of \"([^\"]*)\" is displayed$")
    public void a_message_of_is_displayed(String checkEmailMessage) throws Throwable {
        String message = driver.findElement(By.xpath("//p[contains(text(),'Please')]")).getText();
        assertTrue(message.contains(checkEmailMessage));
    }
    
    @When("^I enter an invalid email address of \"([^\"]*)\"$")
    public void i_enter_an_invalid_email_address_of(String validEmailAddress) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='emailAddress']")).sendKeys(validEmailAddress);
    }
    
    @Then("^a email address field message of \"([^\"]*)\" is displayed$")
    public void a_email_address_field_message_of_is_displayed(String expectedFieldError) throws Throwable {
        String fieldError = driver.findElement(By.id("emailAddress.errors")).getText();
        logger.info(fieldError);
        assertEquals(fieldError, expectedFieldError);
    }
    
    @Then("^I expect the text entered to be truncated to a length of (\\d+)$")
    public void i_expect_the_text_entered_to_be_truncated_to_a_length_of(int maxEmailLength) throws Throwable {
        String emailEntered = driver.findElement(By.xpath("(//form)[2]//input[@name='emailAddress']")).getAttribute("value");
        assertEquals("Expected text entered to be truncated but wasn't.", emailEntered.length(), maxEmailLength);
    }

}
