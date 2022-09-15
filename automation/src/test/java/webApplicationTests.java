
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.http.Method;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class webApplicationTests {
    @Test
    public void testUI(){
        new ChromeDriver();
        ChromeDriver driver;
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("http://localhost");
        if (driver.getTitle().contains("HIT - DevOps course")){
            System.out.println("Good!");
        }
        else
        {
            System.out.println("Bad!");
        }

        driver.quit();
    }

    @Test
    public void RestAssuredTest() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://localhost:80";
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Print the status and message body of the response received from the server
        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.prettyPrint());
    }

    @Test
    public void RestAssuredTest2() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://localhost:80";
        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("");

        // Get the status code of the request.
        //If request is successful, status code will be 200
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        if (statusCode /*actual value*/== 200 /*expected value*/)
        {
            System.out.println(statusCode);
        }
    }

    @Test
    public void testName(){
        new ChromeDriver();
        ChromeDriver driver;
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("http://localhost");


        String bodyText = driver.findElement(By.tagName("body")).getText();

        if (bodyText.contains("Itai Markovetzky")){
            System.out.println("Good!");
        }
        else
        {
            System.out.println("Bad!");
        }

        //Your test



        driver.quit();
    }
}
