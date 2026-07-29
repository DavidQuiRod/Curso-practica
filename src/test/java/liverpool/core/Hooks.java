package liverpool.core;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void beforeScenario() {
        DriverManager.init();
    }

    @After
    public void afterScenario() {
        DriverManager.quit();
    }
}
