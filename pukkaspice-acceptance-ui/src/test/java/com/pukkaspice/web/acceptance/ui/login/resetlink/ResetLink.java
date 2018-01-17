package com.pukkaspice.web.acceptance.ui.login.resetlink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pukkaspice.data.DataSetup;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class ResetLink {

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

    
    @Given("^a user has been sent an email with the following link \"([^\"]*)\" and clicks they click on it$")
    public void a_user_has_been_sent_an_email_with_the_following_link_and_clicks_they_click_on_it(String resetPasswordUrl) throws Throwable {
        driver.get(resetPasswordUrl);
    }

    @And("^enters valid password of \"([^\"]*)\"$")
    public void enters_valid_password_of(String validPassword) throws Throwable {
        driver.findElement(By.xpath("(//form)[2]//input[@name='password']")).sendKeys(validPassword);
    }
    
    @And("^click on the \"([^\"]*)\" button$")
    public void click_on_the_button(String sendButtonText) throws Throwable {
        driver.findElement(By.xpath("//button[text()[contains(.,'" + sendButtonText + "')]]")).click();
    }

    @Then("^they are taken to the secondary login page with the following message displayed \"([^\"]*)\"$")
    public void they_are_taken_to_the_secondary_login_page_with_the_following_message_displayed(String infoMessage) throws Throwable {
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Expected to be taken to members area dashboard but wasn't.", "http://localhost:8080/secure/login", currentUrl);
        
        String text = driver.findElement(By.className("alert")).getText();
        assertTrue("Expected correct error message to contain specified error message", text.contains(infoMessage));
    }
}
