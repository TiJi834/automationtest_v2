package org.testing.utils;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
	WebDriver driver;
	
	public void unfocusElement() {
		driver.findElement(By.xpath("//body")).click();
	}

	// Constructor
	public WebDriverUtils(WebDriver driver) {
		this.driver = driver;
	}
	
	// clear input and enter value inside of the element
	public void fillInput(WebElement element, String inputText) {
		element.clear();
		element.sendKeys(inputText);
	}
	
	// wait until WebElement is visible
	public void waitForVisibility(WebElement element) throws Error {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}

	// wait until text is present in WebElement
	public void waitForTextToAppear(String textToAppear, WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
	}
	
	// find WebElement from list of WebElements which contains specific text
	public WebElement findElementByText(List<WebElement> webElements, String text) {
	    return webElements
	            .stream()
	            .filter(webElement -> Objects.equals(webElement.getText(), text))
	            .findFirst()
	            .orElse(null);
	}
	
	// find WebElement from list of WebElements by specific selector
	public WebElement findElementBySelector(List<WebElement> webElements, By selector) {
	    return webElements
	            .stream()
	            .filter(webElement -> !webElement.findElements(selector).isEmpty())
	            .findFirst()
	            .orElse(null);
	}
	
	// find WebElement on page by text
	public List<WebElement> findTextOnPage (String text) {
		return driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
	}
	
	// find WebElement by text and then by closest selector
	public WebElement findTextElementItsCloseElement (String textElement, By closeElement) {
		List<WebElement> elementsList = findTextOnPage(textElement);
		return findElementBySelector(elementsList, closeElement);
	}
}
