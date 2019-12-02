package org.testing.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testing.utils.WebDriverUtils;

public class TestWebsiteCore {

	WebDriver driver;
	WebDriverUtils utils;

	// URL of the test website
	String defaultURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

	// Panel with alert messages
	@FindBy(xpath = "//div[contains(@class,'alert')]//li")
	List<WebElement> alertPanel;
	
	// selector for searching closest parent with failed validation
	By failedValidationParentElement = By.xpath("//ancestor::div[contains(@class, 'form-error')]");

	// selector for searching closest parent with passed validation
	By passedValidationParentElement = By.xpath("//ancestor::div[contains(@class, 'form-ok')]");
	
	// checking for specific alert message in alert panel
	public boolean isAlertMessageFound(String alertMessage) {
		WebElement foundAlertMessage = utils.findElementByText(alertPanel, alertMessage);
		return (foundAlertMessage != null);
	}
	
	// returns true, if checking element is marked red so it failed validation
	public boolean isElementFailingValidation(String validationElement) {
		utils.unfocusElement();
		WebElement element = utils.findTextElementItsCloseElement(validationElement, failedValidationParentElement);
		return (element != null);
	}

	// returns true, if checking element is marked green so it passed validation
	public boolean isElementPassingValidation(String validationElement) {
		utils.unfocusElement();
		WebElement element = utils.findTextElementItsCloseElement(validationElement, passedValidationParentElement);
		return (element != null);
	}
}
