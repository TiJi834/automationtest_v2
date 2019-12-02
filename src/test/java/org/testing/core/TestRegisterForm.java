package org.testing.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testing.utils.WebDriverUtils;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.andreinc.mockneat.MockNeat;

class TestRegisterForm {

	static WebDriver driver;
	WebDriverUtils utils;

	@BeforeAll
	public static void setup() {
		// setting up configuration before tests
		WebDriverManager.chromedriver().version("77.0.3865.40").setup();
		driver = new ChromeDriver();
	}

	@Test
	public void registerOnTestWebsite() {

		String randomEmail = MockNeat.threadLocal().emails().val();
		
		// 1. Go to: http://automationpractice.com/index.php?controller=authentication
		// 2. Enter correct random email address and click �Create an Account�
		TestWebsiteLoginPage loginPage = new TestWebsiteLoginPage(driver);
		loginPage.enterRegisterEmail(randomEmail);
		loginPage.clickRegister();

		TestWebsiteRegisterPage registerPage = new TestWebsiteRegisterPage(driver);
		assertTrue(registerPage.isInRegistrationPage());

		// 3. Click on �Register� and check alert messages
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("You must register at least one phone number."));
		assertTrue(registerPage.isAlertMessageFound("lastname is required."));
		assertTrue(registerPage.isAlertMessageFound("firstname is required."));
		assertTrue(registerPage.isAlertMessageFound("passwd is required."));
		assertTrue(registerPage.isAlertMessageFound("address1 is required."));
		assertTrue(registerPage.isAlertMessageFound("city is required."));
		assertTrue(registerPage.isAlertMessageFound(
				"The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
		assertTrue(registerPage.isAlertMessageFound("This country requires you to choose a State."));

		// 4. Enter too long �First Name� and click on �Register�
		registerPage.enterFirstName("demodemodemodemodemodemodemodemodemo");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("firstname is too long. Maximum length: 32"));

		// 5. Enter "First Name" without capital letter and click on "Register"
		registerPage.enterFirstName("demo");
		registerPage.clickRegister();
		assertFalse(registerPage.isElementFailingValidation("First name"));
		assertFalse(registerPage.isAlertMessageFound("firstname is invalid."));

		// 6. Enter "First Name" with a whitespace in front and back of the name and click on "Register"
		registerPage.enterFirstName("   Demo    ");
		registerPage.clickRegister();
		assertFalse(registerPage.isElementFailingValidation("First name"));
		assertFalse(registerPage.isAlertMessageFound("firstname is invalid."));

		// 7. Fill "First Name" field with script injection code, click outside of the field to check field marking and click on "Register"
		registerPage.enterFirstName(
				"<script language=\"javascript\" type=\"text/javascript\" >window.onload = func(); function func() { alert('Hi'); }</script>");
		assertTrue(registerPage.isElementFailingValidation("First name"));
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("firstname is invalid."));

		// 8. Click on �First Name� field to select it, leave it empty and then click outside of the field
		registerPage.enterFirstName("");
		assertTrue(registerPage.isElementFailingValidation("First name"));

		// 9. Click on �First Name� field to select it, enter numeric value and then click outside of the field
		registerPage.enterFirstName("Demo1");
		assertTrue(registerPage.isElementFailingValidation("First name"));

		// 10. Click on �First Name� field to select it, enter name with special character, click outside of the field
		registerPage.enterFirstName("Demo!");
		assertTrue(registerPage.isElementFailingValidation("First name"));

		// 11. Click on �First Name� field to select it, enter correct name, click outside of the field to check field marking and click on �Register�
		registerPage.enterFirstName("Demo");
		assertTrue(registerPage.isElementPassingValidation("First name"));
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("firstname is invalid."));

		// 12. Check, if previous steps applies also to �Last Name� field.
		registerPage.enterLastName("Demo");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("lastname is invalid."));

		// 13. Fill �Email� field with too long email value and click �Register�
		registerPage.enterEmail("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest@mail.com");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("email is too long. Maximum length: 128"));
		
		// 14. Fill "Email" field with SQL injection code, click outside of the field to check field marking and click on "Register"
		registerPage.enterEmail("demo@mail.com OR 1=1");
		assertTrue(registerPage.isElementFailingValidation("Email"));
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("email is invalid."));
		
		// 15. Enter incorrect email address, click outside of the field
		registerPage.enterEmail("test");
		assertTrue(registerPage.isElementFailingValidation("Email"));
		
		// 16. Enter previously registered email address, click outside of the field to check field marking and click �Register�
		registerPage.enterEmail("test@mail.com");
		assertTrue(registerPage.isElementPassingValidation("Email"));
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("An account using this email address has already been registered."));
		
		// 17. Enter correct random email address again and click on "Register"
		registerPage.enterEmail(randomEmail);
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("email is invalid."));
		
		// 18. Click on "Password" field, type too short password, click outside of the field to check field marking and click �Register�
		registerPage.enterPassword("Pass");
		assertTrue(registerPage.isElementFailingValidation("Password"));
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("passwd is invalid."));
		
		// 19. Click on "Password" field, type too long password, click outside of the field to check field marking and click �Register�
		registerPage.enterPassword("passpasspasspasspasspasspasspasspass");
		assertFalse(registerPage.isElementFailingValidation("Password"));
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("passwd is too long. Maximum length: 32"));
		
		// 20. Fill "Address" field with too long address and click �Register�
		registerPage.enterAddress("addressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddressaddress");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("address1 is too long. Maximum length: 128"));
		
		// 21. Type address in "Address" field and click "Register"
		registerPage.enterAddress("address");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("address1 is required."));
		
		// 22. Click on "City" field, type only numeric values and click �Register�
		registerPage.enterCity("1111");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("city is invalid."));
		
		// 23. Choose state from the list and click "Register"
		registerPage.selectState("Alabama");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("This country requires you to choose a State."));
		
		// 24. Fill "Zip/Postal Code" with characters and click �Register�
		registerPage.enterZipcode("AAAAA");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
		
		// 25. Fill "Zip/Postal Code" with 6 numbers and click �Register�
		registerPage.enterZipcode("123456");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
		
		// 26. Fill "Zip/Postal Code" with correct format and click �Register�
		registerPage.enterZipcode("12345");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("The Zip/Postal code you've entered is invalid. It must follow this format: 00000"));
		
		// 27. Enter invalid "Mobile phone" number and click on "Register"
		registerPage.enterMobilePhone("aaaaaaaaa");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("phone_mobile is invalid."));
		
		// 28. Enter valid but too long "Mobile phone" number and click on "Register"
		registerPage.enterMobilePhone("123456789123456789123456789123456");
		registerPage.clickRegister();
		assertTrue(registerPage.isAlertMessageFound("phone_mobile is too long. Maximum length: 32"));
		
		// 29. Enter valid "Mobile phone" number and click on "Register"
		registerPage.enterMobilePhone("123456789");
		registerPage.clickRegister();
		assertFalse(registerPage.isAlertMessageFound("phone_mobile is too long. Maximum length: 32"));
		
		// 30. Enter valid "Password" and click on "Register"
		registerPage.enterPassword("Password1");
		registerPage.clickRegister();
		
		TestWebsiteDashboardPage dashboardPage = new TestWebsiteDashboardPage(driver);
		assertTrue(dashboardPage.isInDashboard());
	}

	@AfterAll
	public static void finish() {
		// quitting driver after tests
		driver.quit();
	}

}
