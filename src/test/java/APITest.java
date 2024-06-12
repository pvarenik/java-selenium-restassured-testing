import constants.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APITest {

    @BeforeClass(groups = "API")
    public void setup() {
        RestAssured.baseURI = System.getProperty("baseURI", Constants.DEFAULT_API_URI);
    }

    @Test(groups = "API")
    public void testLatestFilmAndTallestCharacter() {
        // Step 1: Find the film with the latest release date
        Response response = given().get("/films/");
        String latestFilmUrl = response.jsonPath().getString("results.max() { it.release_date }.url");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200 , "Incorrect status code returned");

        // Step 2: Find the tallest person in the latest film
        response = given().get(latestFilmUrl);
        int maxHeight = 0, height = 0;
        String tallestCharacterUrl = "";
        for(Object character: response.jsonPath().getList("characters")) {
            response = given().get(character.toString());
            height = response.jsonPath().getInt("height");
            if(height > maxHeight) {
                maxHeight = height;
                tallestCharacterUrl = character.toString();
            }
        }
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200 , "Incorrect status code returned");

        // Step 3: Find the tallest person ever in any Star Wars film
        response = given().get("/people/");
        String tallestPersonUrl = response.jsonPath().get("results.max() { Integer.valueOf(it.height) }.url");
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200 , "Incorrect status code returned");

        // Verify the URLs are not null
        Assert.assertNotNull(latestFilmUrl);
        System.out.println("Latest release: " + latestFilmUrl);
        Assert.assertNotNull(tallestCharacterUrl);
        System.out.println("Tallest person in this film: " + tallestCharacterUrl);
        Assert.assertNotNull(tallestPersonUrl);
        System.out.println("Tallest person in all films: " + tallestPersonUrl);
    }

    @Test(groups = "API")
    public void testPeopleJsonSchemaValidation() {
        Response response = given().get("/people/");
        response.then().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("people-schema.json"));
    }
}
