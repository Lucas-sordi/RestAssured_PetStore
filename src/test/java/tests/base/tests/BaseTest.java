package tests.base.tests;

import config.EnvironmentConfig;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        EnvironmentConfig.setBaseURI(EnvironmentConfig.Environment.PRD);
    }
}
