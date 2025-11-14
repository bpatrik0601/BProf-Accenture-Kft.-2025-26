package runners;

/* 
import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber --> Cucumber runner annotation is outdated/deprecated and is not recommended to be used for JUnit5
*/

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME; // for steps' package
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME; // for features' location
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@ConfigurationParameter(
   key = GLUE_PROPERTY_NAME,
   value = "steps" // steps package name
)

@ConfigurationParameter(
    key = FEATURES_PROPERTY_NAME,
    value = "src/test/resources/features" // path to features
)

@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty, summary, html:target/cucumber-report.html, json:target/cucumber-report.json" // riport plugins
    // pretty: console output (colorized, step-by-step log on console)
    // summary: summary in console (total, passed, failed, skipped <-- how many scenarios, steps, time)
    // html: HTML report (browser viewable)
    // json: JSON report (computer processable riport for CI/CD integration)
)

public class RunCucumberTest {
    // This class is empty on purpose — it only triggers Cucumber's JUnit 5 runner.
}