package org.testing.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testing.utils.WebDriverUtils;

public class TestWebsiteRegisterPage extends TestWebsiteCore {

	// Constructor
	public TestWebsiteRegisterPage(WebDriver driver) {
		this.driver = driver;
		utils = new WebDriverUtils(driver);
		PageFactory.initElements(driver, this);
	}
	
	// element of the whole registration panel
	@FindBy(xpath = "//div[@class='account_creation']")
	private WebElement registerPanel;
	
	// button element in registration
	@FindBy(xpath = "//button[@id='submitAccount']")
	private WebElement registerButton;
	
	// first name input element in registration
	@FindBy(xpath = "//input[@id='customer_firstname']")
	private WebElement firstNameInput;
	
	// last name input element
	@FindBy(xpath = "//input[@id='customer_lastname']")
	private WebElement lastNameInput;
	
	// email input element
	@FindBy(xpath = "//input[@id='email']")
	private WebElement emailInput;
	
	// password input element
	@FindBy(xpath = "//input[@id='passwd']")
	private WebElement passwordInput;
	
	// address input element
	@FindBy(xpath = "//input[@id='address1']")
	private WebElement addressInput;
	
	// city input element
	@FindBy(xpath = "//input[@id='city']")
	private WebElement cityInput;
	
	// state dropdown element
	@FindBy(xpath = "//select[@id='id_state']")
	private WebElement stateDropdown;
	
	// zipcode input element
	@FindBy(xpath = "//input[@id='postcode']")
	private WebElement zipcodeInput;
	
	// mobile phone input element
	@FindBy(xpath = "//input[@id='phone_mobile']")
	private WebElement phoneInput;
	
	public void enterFirstName(String firstName) {
		utils.fillInput(firstNameInput, firstName);
	}
	
	public void enterLastName(String lastName) {
		utils.fillInput(lastNameInput, lastName);
	}
	
	public void enterEmail(String email) {
		utils.fillInput(emailInput, email);
	}
	
	public void enterPassword(String password) {
		utils.fillInput(passwordInput, password);
	}
	
	public void enterAddress(String address) {
		utils.fillInput(addressInput, address);
	}
	
	public void enterCity(String city) {
		utils.fillInput(cityInput, city);
	}
	
	// select state from dropdown
	public void selectState(String state) {
		Select stateDropdownElement = new Select(stateDropdown);
		stateDropdownElement.selectByVisibleText(state);
	}
	
	public void enterZipcode(String zipcode) {
		utils.fillInput(zipcodeInput, zipcode);
	}
	
	public void enterMobilePhone(String phone) {
		utils.fillInput(phoneInput, phone);
	}
	
	// click on register button
	public void clickRegister() {
		registerButton.click();
	}
	
	// check, if user is redirected to registration page
	public boolean isInRegistrationPage() {
		utils.waitForVisibility(registerPanel);
		return registerPanel.isDisplayed();
	}

}
