package org.testing.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import io.restassured.response.ValidatableResponse;

public class TestAPI {

    @DisplayName("Test MessageService.get()")
    @Test
    void testGet() {
        //assertEquals("Hello JUnit 5", MessageService.get());
    	ValidatableResponse getDrivers = given().when().get("https://ergast.com/api/f1/drivers.json").then().statusCode(200);
		Object driverId = getDrivers.extract().path("MRData.DriverTable.Drivers.driverId");
		System.out.println(driverId);
    }

}
