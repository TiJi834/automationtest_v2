package org.testing.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TestWebsiteDashboardPage {
	WebDriver driver;

	// Constructor
	public TestWebsiteDashboardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// element that is only visible if logged in to account dashboard
	@FindBy(xpath = "//*[@id='my-account']")
	WebElement accountInfoElement;

	// check, if user is logged in to account dashboard
	public boolean isInDashboard() {
		return accountInfoElement.isDisplayed();
	}

}
