package com.pukkaspice.web.acceptance.ui.login.globalsteps;

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

public class GlobalLogin {

    private Logger logger = Logger.getLogger(GlobalLogin.class.getName());

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

    @Given("^I am that I am on the home page of the website$")
    public void i_am_that_I_am_on_the_home_page_of_the_website() throws Throwable {
        driver.get("http://localhost:8080");
    }

    @And("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String loginButtonText) throws Throwable {
        driver.findElement(By.linkText(loginButtonText)).click();
    }

    @And("^I enter a valid email address of \"([^\"]*)\"$")
    public void i_enter_a_valid_email_address_of(String validEmailAddress) throws Throwable {
        driver.findElement(By.name("emailAddress")).sendKeys(validEmailAddress);
    }

    @And("^I enter a valid password of \"([^\"]*)\"$")
    public void i_enter_a_valid_password_of(String validPassword) throws Throwable {
        driver.findElement(By.name("password")).sendKeys(validPassword);
    }

    @And("^I click the \"([^\"]*)\" button$")
    public void i_click_the_button(String loginButtonText) throws Throwable {
        driver.findElement(By.xpath("//button[text()[contains(.,'" + loginButtonText + "')]]")).click();
    }

    @Then("^I should be taken to members area dashboard$")
    public void i_should_be_taken_to_members_area_dashboard() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken to members area dashboard but wasn't.", "http://localhost:8080/secure/member/dashboard", currentUrl);
    }

    @Given("^I enter an invalid email address of \"([^\"]*)\"$")
    public void i_enter_an_invalid_email_address_of(String invlidEmailAddress) throws Throwable {
        driver.findElement(By.name("emailAddress")).sendKeys(invlidEmailAddress);
    }

    @Then("^I should be taken to secondary login page$")
    public void i_should_be_taken_to_secondary_login_page() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken to members area dashboard but wasn't.", "http://localhost:8080/secure/login", currentUrl);
    }

    @Then("^An error message of \"([^\"]*)\" should be displayed\\.$")
    public void a_warning_message_of_should_be_displayed(String errorMessage) throws Throwable {
        String text = driver.findElement(By.className("alert")).getText();

        assertTrue("Expected correct error message to contain specified error message", text.contains(errorMessage));
    }

    @Given("^I enter an invalid password of \"([^\"]*)\"$")
    public void i_enter_an_invalid_password_of(String inValidPassword) throws Throwable {
        driver.findElement(By.name("password")).sendKeys(inValidPassword);
    }

}
