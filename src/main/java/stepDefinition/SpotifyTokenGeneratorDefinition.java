package stepDefinition;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SpotifyTokenGeneratorDefinition {

	WebDriver driver;
	String accessToken;
	String authGrant;

//	@Before
	@Given("spotify auth token is generated")
	public void beforeTest() throws InterruptedException {
//		System.setProperty("webdriver.chrome.driver", "C:\\Driver\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		WebDriverManager.chromedriver().setup();
	}

	@When("aut grant code is generated")
	public void generateAuthGrantCode() throws InterruptedException {
		authGrant = getCode1();
	}

	@Then("generate the refresh token")
	public void generateToken() throws InterruptedException {
		accessToken = getCode2();
	}

	public String getCode1() throws InterruptedException {
		driver.get(
				"https://accounts.spotify.com/authorize?response_type=code&client_id=c256b6a9b8214e409c443b4508dd3816&redirect_uri=https://pivotcoachingacademy.ca/about-us");

		driver.manage().window().maximize();
		driver.findElement(By.id("login-username")).sendKeys("asharanisv94@gmail.com");
		driver.findElement(By.id("login-password")).sendKeys("admin123");
		driver.findElement(By.id("login-button")).click();

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		Thread.sleep(1500);
		WebElement foo = wait.until(ExpectedConditions
				.elementToBeClickable(driver.findElement(By.cssSelector("button[data-testid='auth-accept']"))));

		foo.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']")));

		System.out.println("Pivot page displayed :" + driver
				.findElement(
						By.xpath("//span[text()='Copyright © 2021 PIVOT COACHING ACADEMY - All Rights Reserved.']"))
				.isDisplayed());

		String url = driver.getCurrentUrl();

		authGrant = url.substring(url.indexOf("=") + 1).trim();
		System.out.println("authGrant---> " + authGrant);
		return authGrant;

	}

	public String getCode2() {

		String clientId = "c256b6a9b8214e409c443b4508dd3816";
		String clientSecret = "ef96b442709c4db5ba2638b1024d596f";
		String credentials = clientId + ":" + clientSecret;
		String base64EncodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

		Response response = (Response) RestAssured.given().header("Authorization", "Basic " + base64EncodedCredentials)
				.contentType("application/x-www-form-urlencoded").formParam("grant_type", "authorization_code")
				.formParam("code", authGrant).formParam("redirect_uri", "https://pivotcoachingacademy.ca/about-us")
				.log().all().when().post("https://accounts.spotify.com/api/token");

//		Response response = (Response) RestAssured.given()
//				.header("Authorization","Basic +"Encoded ClientId:ClientSecret")
//				.contentType("application/x-www-form-urlencoded").formParam("grant_type", "authorization_code")
//				.formParam("code", authGrant).formParam("redirect_uri", "https://pivotcoachingacademy.ca/about-us")
//				.log().all().when().post("https://accounts.spotify.com/api/token");

		assertEquals(200, response.statusCode());
		accessToken = response.jsonPath().getString("access_token");
		String refreshToken = response.jsonPath().getString("refresh_token");
		System.out.println("REsponse Body-->" + response.getBody().asString());

		System.out.println("accessToken---> " + accessToken);
		System.out.println("refreshToken---> " + refreshToken);

		return accessToken;

	}

//	@Test

	public void getPlaylist() {

		Response response = (Response) RestAssured.given().header("Authorization", "Bearer " + accessToken).log().all()
				.when().get("https://api.spotify.com/v1/playlists/37i9dQZEVXbMda2apknTqH");

		assertEquals(200, response.statusCode());

	}

	@After
	public void afterTest() {
		driver.quit();
	}

}
