package com.pukkaspice.web.acceptance.ui.login;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
    features={"src/test/resources/com/pukkaspice/web/acceptance/ui/login/GlobalLogin.feature"},
    glue={
        "com.pukkaspice.web.acceptance.ui.util", 
        "com.pukkaspice.web.acceptance.ui.login.globalsteps"
    },
    plugin={"pretty", "html:target/cucumber"},
    strict=true
)
public class GlobalLoginIT {

}
