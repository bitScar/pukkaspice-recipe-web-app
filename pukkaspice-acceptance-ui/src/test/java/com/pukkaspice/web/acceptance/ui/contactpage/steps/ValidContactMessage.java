package com.pukkaspice.web.acceptance.ui.contactpage.steps;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.pukkaspice.web.acceptance.ui.util.TomcatEmbedded;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ValidContactMessage {
    
    private Logger logger = Logger.getLogger(ValidContactMessage.class.getName());
    
    private WebDriver driver = new FirefoxDriver();
    
    
    @Before
    public void before() {
        // TODO setup database with expected data
    }
    
    @After
    public void after() {
        driver.close();
    }
    
    @Given("^That I am an anonymous user on the homepage of the website$")
    public void that_I_am_an_anonymous_user_on_the_homepage_of_the_website() throws Throwable {
        driver.get(TomcatEmbedded.getAppBaseURL() + "home");
    }
    
    @When("^I click on \"([^\"]*)\" button to navigate to contact page$")
    public void i_click_on_button_to_navigate_to_contact_page(String contactButtonText) throws Throwable {
        driver.findElement(By.xpath("//a[text()[contains(.,'Contact')]]")).click();
    }

    @When("^enter a name of \"([^\"]*)\"; email address of \"([^\"]*)\", and message of \"([^\"]*)\", and click submit message button$")
    public void enter_a_name_of_email_address_of_and_message_of_and_click_submit_message_button(String arg1, String arg2, String arg3) throws Throwable {
        driver.findElement(By.name("name")).sendKeys(arg1);
        driver.findElement(By.name("email")).sendKeys(arg2);
        driver.findElement(By.name("message")).sendKeys(arg3);
        driver.findElement(By.xpath("//button[text()[contains(.,'Submit Message')]]")).click();
    }

    @Then("^the user will be presentated with a success message of \"([^\"]*)\"$")
    public void the_user_will_be_presentated_with_a_success_message_of(String arg1) throws Throwable {
        WebElement findElement = driver.findElement(By.className("alert-success"));
        assertThat("Sucess message expected, but not found or is incorrect text.", findElement.getText(), containsString(arg1));
    }

}
