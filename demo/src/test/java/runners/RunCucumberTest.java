package runners;

/* 
import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber --> Cucumber runner annotation is outdated/deprecated and is not recommended to be used for JUnit5
*/

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME; // for steps' package
import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME; // for features' location


@Suite
@SelectPackages("tests.java.steps")

@ConfigurationParameter(
   key = GLUE_PROPERTY_NAME,
   value = "tests.java.steps" // package name
)

@ConfigurationParameter(
    key = FEATURES_PROPERTY_NAME,
    value = "src/test/resources/features" // path to features
)

public class RunCucumberTest {
    // This class is empty on purpose — it only triggers Cucumber's JUnit 5 runner.
}