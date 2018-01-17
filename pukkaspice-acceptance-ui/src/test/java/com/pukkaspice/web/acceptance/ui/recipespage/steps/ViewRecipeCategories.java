package com.pukkaspice.web.acceptance.ui.recipespage.steps;

import java.util.logging.Logger;

import com.pukkaspice.web.acceptance.ui.contactpage.steps.ValidContactMessage;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ViewRecipeCategories {
    
    private Logger logger = Logger.getLogger(ViewRecipeCategories.class.getName());
    
    @Before
    public void setupDataBase() {
        logger.info("Setup db for view recipes...");
    }
    
    @Given("^That I am an anonymous user on the recipes page of the website$")
    public void that_I_am_an_anonymous_user_on_the_recipes_page_of_the_website() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @When("^I click on each caegory$")
    public void i_click_on_each_caegory() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @Then("^theu should display properly$")
    public void theu_should_display_properly() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }


}
