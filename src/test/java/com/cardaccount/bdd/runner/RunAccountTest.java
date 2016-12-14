package com.cardaccount.bdd.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:target/cucumber"},
        glue = "com.cardaccount.bdd.accountsteps",
        features = "classpath:cucumber/account.feature"
)
public class RunAccountTest {
}
