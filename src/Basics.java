import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

//import files.Payload;
import files.ReusableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Validate if add place API is working as expected
		//Given - All input details
		//When - Submit the API
		//Then - Validate the response
		
		//Content of the file to String -> Content of file convert into Byte -> Byta Data to String
		 
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		// Add place ->
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\addPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); //for parsing JSON
		String placeId = js.getString("place_id");
		
		//Update Place with new address
		
		String newAddress = "70 Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
		// Get Place to validate if New Address is present in response
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(newAddress, actualAddress);
		
	}
}
