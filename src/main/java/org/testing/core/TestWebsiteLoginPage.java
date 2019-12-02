package org.testing.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testing.utils.WebDriverUtils;

public class TestWebsiteLoginPage extends TestWebsiteCore {

	// Constructor
	public TestWebsiteLoginPage(WebDriver driver) {
		this.driver = driver;
		this.driver.get(defaultURL);
		utils = new WebDriverUtils(driver);
		PageFactory.initElements(driver, this);
	}

	// email input element for logging
	@FindBy(xpath = "//div[contains(@class,'form-group')]//input[@id='email']")
	WebElement emailLoginInput;

	// password input element for logging
	@FindBy(xpath = "//div[contains(@class,'form-group')]//input[@id='passwd']")
	WebElement passwordLoginInput;

	// button element for logging
	@FindBy(xpath = "//button[@id='SubmitLogin']")
	WebElement loginButton;

	// email input element for registration
	@FindBy(xpath = "//div[contains(@class,'form-group')]//input[@id='email_create']")
	WebElement emailRegisterInput;

	// button element for registration
	@FindBy(xpath = "//button[@id='SubmitCreate']")
	WebElement registerButton;

	// fill email input for logging
	public void enterLoginEmail(String username) {
		utils.fillInput(emailLoginInput, username);
	}

	// fill password input for logging
	public void enterLoginPassword(String password) {
		utils.fillInput(passwordLoginInput, password);
	}

	// fill email input for registration
	public void enterRegisterEmail(String username) {
		utils.fillInput(emailRegisterInput, username);
	}

	// click on login button
	public void clickLogin() {
		loginButton.click();
	}

	// click on register button
	public void clickRegister() {
		registerButton.click();
	}
}
