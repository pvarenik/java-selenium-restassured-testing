package pages;

import constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    private final WebDriver driver;
    private final By researchAndEducationLink = By.cssSelector("li.main_nav_research");
    private final By acceptCookiesButton = By.cssSelector("button[aria-label='Close']");
    private final WebDriverWait wait;
    private final By modal = By.cssSelector("div.modal-backdrop");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.WAIT_DURATION);
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public void acceptCookies() {
        driver.findElement(acceptCookiesButton).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(modal, 0));
    }

    public boolean isAtHomePage() {
        return driver.getTitle().contains("XM");
    }

    public void clickResearchAndEducation() {
        driver.findElement(researchAndEducationLink).click();
    }
}
