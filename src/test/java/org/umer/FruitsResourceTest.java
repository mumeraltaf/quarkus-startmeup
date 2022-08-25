package org.umer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@QuarkusTest
public class FruitsResourceTest {

    @Test
    public void testFruitsEndpoint() {
        given()
          .when().get("/fruits")
          .then()
             .statusCode(200);
    }

}