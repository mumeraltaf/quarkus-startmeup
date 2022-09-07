package org.umer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.umer.domain.Fruit;
import org.umer.service.FruitService;


import javax.inject.Inject;

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
    @Inject
    FruitService fruitService;

    @Test
    public void testAddingAFruit() throws Exception {

        Fruit testFruit = new Fruit("test", "testRedApple","red is test");
        Fruit insertedFruit = fruitService.add(testFruit);
        Assertions.assertEquals(insertedFruit.getName(),testFruit.getName());

    }

    @Test
    public void testAddingAndGettingFruit() throws Exception {

        Fruit testFruit = new Fruit("test2", "testRedApple2","red is test2");
        fruitService.add(testFruit);

        Fruit fruitInserted = fruitService.findById(testFruit.getId());

        Assertions.assertEquals(fruitInserted.getName(),testFruit.getName());

    }

    @Test
    public void simpleAdditionTest() {

        int a = 2;
        int b = 4;

        Assertions.assertEquals(6,a+b);

    }

}