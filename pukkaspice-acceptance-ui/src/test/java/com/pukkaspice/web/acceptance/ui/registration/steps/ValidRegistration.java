package com.pukkaspice.web.acceptance.ui.registration.steps;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pukkaspice.data.DataSetup;
import com.pukkaspice.web.acceptance.ui.contactpage.steps.ValidContactMessage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ValidRegistration {

    private Logger logger = Logger.getLogger(ValidContactMessage.class.getName());

    private WebDriver driver = new FirefoxDriver();

    @Before
    public void before() throws Exception {
        DataSetup dataSetup = new DataSetup("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/pukkaspice", "root", "", false);
        dataSetup.doSetup();
    }

    @After
    public void after() {
        // driver.close();
    }

    @Given("^that I am on the registration form page of the website$")
    public void that_I_am_on_the_registration_form_page_of_the_website() throws Throwable {
        driver.get("http://localhost:8080/secure/join");
    }

    @Given("^I enter my first name of \"([^\"]*)\", surname of \"([^\"]*)\", email address of \"([^\"]*)\", and password of \"([^\"]*)\"$")
    public void i_enter_my_first_name_of_surname_of_email_address_of_and_password_of(String firstName, String surname, String emailAddress, String password) throws Throwable {
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("surname")).sendKeys(surname);

        driver.findElement(By.id("emailAddress")).sendKeys(emailAddress);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @Given("^I press \"([^\"]*)\" button$")
    public void i_press_button(String buttonText) throws Throwable {
        driver.findElement(By.xpath("//button[text()[contains(.,'" + buttonText + "')]]")).click();
    }

    @Then("^I should be taken straight to members area dashboard$")
    public void i_should_be_taken_straight_to_members_area_dashboard() throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken to members area dashboard but wasn't.", "http://localhost:8080/secure/member/dashboard", currentUrl);
    }

}
