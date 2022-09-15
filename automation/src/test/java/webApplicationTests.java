import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class webApplicationTests {
    @Test
    public void RestAssuredTest() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://localhost:80";
        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("");
        // Get the status code of the request.
        //If request is successful, status code will be 200
        int statusCode = response.getStatusCode();
        // Assert that correct status code is returned.
        assertEquals(statusCode, 200, "The status code is not 200!");
    }

    @Test
    public void NameTest(){
        new ChromeDriver();
        ChromeDriver driver;
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("http://localhost");
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue(bodyText.contains("Itai Markovetzky"), "Not the correct name!");
        driver.quit();
    }
}