package pages;

import constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EducationalVideosPage {
    private final WebDriver driver;
    private final By chapterIntro = By.xpath("//button[text()='Intro to the Markets']");
    private final By lessonIntro = By.partialLinkText("Introduction to the Financial Markets");
    private final WebDriverWait wait;

    public EducationalVideosPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.WAIT_DURATION);
    }

    public void clickIntroLesson() {
        wait.until(ExpectedConditions.elementToBeClickable(chapterIntro));
        driver.findElement(chapterIntro).click();
        wait.until(ExpectedConditions.elementToBeClickable(lessonIntro));
        driver.findElement(lessonIntro).click();
    }
}
