package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResearchAndEducationPage {
    private final WebDriver driver;
    private final By economicCalendarLink = By.linkText("Economic Calendar");
    private final By educationalVideosLink = By.linkText("Educational Videos");

    public ResearchAndEducationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickEconomicCalendar() {
        driver.findElement(economicCalendarLink).click();
    }

    public void clickEducationalVideos() {
        driver.findElement(educationalVideosLink).click();
    }
}
