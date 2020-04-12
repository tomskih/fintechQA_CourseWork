package apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Properties;

public class TestBase {
    public RequestSpecification requestSpecification;
    public Properties props;

    @SneakyThrows
    public TestBase(String baseURI) throws IOException {
        props = new Properties();
        props.load(getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")
        );
        //Rest Assured config
        RestAssured.baseURI = props.getProperty(baseURI);
        //basic request setting
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }

    protected ValidatableResponse getWith200Status(String endPoint) {
        return requestSpecification
                .get(endPoint)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
