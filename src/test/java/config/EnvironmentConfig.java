package config;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

public class EnvironmentConfig {

    public enum Environment {
        LOCAL, PRD
    }

    public static void setBaseURI(Environment environment) {
        switch (environment) {
            case LOCAL:
                RestAssured.baseURI = "https://localhost:8080";
                break;
            case PRD:
                RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()); // ignora verificacao do certificado ssl
                RestAssured.baseURI = "https://petstore.swagger.io/v2";
                break;
        }
    }
}