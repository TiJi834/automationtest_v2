package org.testing.core;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import net.andreinc.mockneat.MockNeat;

public class TestAPI {

	@Test
	void test_Register_User() {
		String randomEmail = MockNeat.threadLocal().emails().val();
		String randomPassword = MockNeat.threadLocal().passwords().val();
		String randomFullName = MockNeat.threadLocal().names().val();
		String firstName = randomFullName.split(" ")[0];
		String lastName = randomFullName.split(" ")[1];

		HashMap<String, String> postContent = new HashMap<>();
		postContent.put("FirstName", firstName);
		postContent.put("LastName", lastName);
		postContent.put("UserName", firstName);
		postContent.put("Password", randomPassword);
		postContent.put("Email", randomEmail);

		Response response = given().contentType(ContentType.JSON).body(postContent)
				.post("http://restapi.demoqa.com/customer/register");
		response.then().statusCode(201);
	}

	@Test
	void test_Authentication() {
		Response response = given().auth().preemptive().basic("ToolsQA", "TestPassword").when()
				.get("http://restapi.demoqa.com/authentication/CheckForAuthentication");
		response.then().statusCode(200);
		System.out.println(response.getBody().asString());
	}

	@Test
	void test_GetDriverIds_and_Print() {
		Response getDrivers = given().get("https://ergast.com/api/f1/drivers.json");
		Object driverId = getDrivers.path("MRData.DriverTable.Drivers.driverId");
		System.out.println(driverId);
	}

	@Test
	void test_GetList_of_Drivers_Biography() {
		Object listOfDriversBiography = get("https://ergast.com/api/f1/drivers.json")
				.path("MRData.DriverTable.Drivers.findAll { it.nationality == 'American' }");
		System.out.println(listOfDriversBiography);
	}

	@Test
	public void test_NumberOfCircuits_ShouldBe20_Parameterized() {

		String season = "2017";
		int numberOfRaces = 20;

		given().pathParam("raceSeason", season).when().get("http://ergast.com/api/f1/{raceSeason}/circuits.json").then()
				.assertThat().body("MRData.CircuitTable.Circuits.circuitId", hasSize(numberOfRaces));
	}

	@Test
	public void test_ScenarioRetrieveFirstCircuitFor2017SeasonAndGetCountry_ShouldBeAustralia() {

		// First, retrieve the circuit ID for the first circuit of the 2017 season
		String circuitId = given().when().get("http://ergast.com/api/f1/2017/circuits.json").then().extract()
				.path("MRData.CircuitTable.Circuits.circuitId[0]");

		// Then, retrieve the information known for that circuit and verify it is
		// located in Australia
		given().pathParam("circuitId", circuitId).when().get("http://ergast.com/api/f1/circuits/{circuitId}.json")
				.then().assertThat().body("MRData.CircuitTable.Circuits.Location[0].country", equalTo("Australia"));
	}

	@Test
	public void test_GoogleMaps_XML_Response() {
		RestAssured.baseURI = "https://maps.googleapis.com";
		String googlemapsURL = "/maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyCuvVtEjxi5WIh50mliS0j2QP1B1BcRAy4";

		Response googleMapsResponse = given().get(googlemapsURL);
		String googleMapsResponseString = googleMapsResponse.andReturn().asString();
		XmlPath xmlPath = new XmlPath(googleMapsResponseString).setRootPath("PlaceSearchResponse");
		String status = xmlPath.get("status");
		List<Object> resultsNames = xmlPath.getList("result.name");
		System.out.println(status);
		System.out.println(resultsNames);
	}

}
