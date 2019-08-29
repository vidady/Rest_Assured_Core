package rest.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Description;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class practice extends TestBase{
	private static Logger log=LogManager.getLogger(practice.class.getName());
	public FileInputStream fis;
	public Properties prop;

	@BeforeTest
	public void initialize() throws IOException {
		fis = new FileInputStream("src\\main\\java\\rest\\rest\\env.properties");
		prop = new Properties();
		prop.load(fis);
	}

	@Test(enabled = false)
	public void test() {

		RestAssured.baseURI = prop.getProperty("baseUrl");
		given().param("location", "-33.8670522,151.1957362").param("radius", "500")
		.param("key", prop.getProperty("key")).when().get("/maps/api/place/nearbysearch/json").then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
		.body("results[0].name", equalTo("Sydney"));

	}
	
    
	@Test(enabled = true,description="This is to verify create place API")
	public void createPlaceAPI() throws IOException, ClassNotFoundException {
		Location loc = new Location();
		WheaterPost wp = new WheaterPost();
		loc.setLat(-33.8669710);
		loc.setLng(151.1958750);
		wp.setLocation(loc);
		wp.setAccuracy(50);
		wp.setName("Vivek Post");
		wp.setPhone_number("999999998");
		wp.setAddress("Testing address");
		String str[] = { "shoe_store" };
		wp.setTypes(str);
		wp.setWebsite("http://www.google.com.au");
		wp.setLanguage("en-AU");

		ReusableMethods.SerializeToFile(wp, "Wheather_Serialized");
		WheaterPost w=(WheaterPost)ReusableMethods.DeserializedFromFileToObject("Wheather_Serialized");
		System.out.println(w.getPhone_number());

		RestAssured.baseURI = "http://216.10.245.166";
		Response res = given().queryParam("key", prop.getProperty("key")).body(w).log().all().when()
				.post(Resources.placePostData()).then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().log().all().body("status", equalTo("OK")).and().extract().response();
		JsonPath js = ReusableMethods.rawToJson(res);
		js.get("place_id");
		System.out.println(js.get("place_id").toString());
		System.out.println(js.get("scope.size()").toString());
		
	}


	@Test(dataProvider="dataProvider", priority=1,description="This is static data provider test")
	public void test003(Hashtable<String,String>data) {
		initial_test_tasks(data);
		RestAssured.baseURI = "http://216.10.245.166";
		Response res=given().header("Content-Type","application/json").
				body("{\r\n\r\n\"name\":\"Learn Appium Automation with Java\",\r\n\"isbn\":\""+data.get("name")+"\",\r\n\"aisle\":\""+data.get("val")+"\",\r\n\"author\":\"John foe\"\r\n}\r\n \r\n").
				when().post("/Library/Addbook.php").then().log().all().statusCode(400).extract().response();
		JsonPath jp=ReusableMethods.rawToJson(res);
		String id=jp.get("ID");
		System.out.println(id);
		


	}

	@Test(dataProvider="getData",priority=2,enabled=false)
	public void test004(String name, int val) {

		RestAssured.baseURI = "http://216.10.245.166";
		Response res=given().header("Content-Type","application/json").
				body("{\r\n \r\n\"ID\" : \""+name+Integer.toString(val)+"\"\r\n \r\n} \r\n").
				when().post("/Library/DeleteBook.php").then().log().all().extract().response();
		log.info("PASS");
	}

	@DataProvider
	public Object[][] getData(){
		Object data[][]= {{"viv",7},{"dub",8}};
		return data;
	}


}
