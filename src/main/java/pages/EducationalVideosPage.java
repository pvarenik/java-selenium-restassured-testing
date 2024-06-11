package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EducationalVideosPage {
    private final WebDriver driver;
    private final By chapterIntro = By.xpath("//button[text()='Intro to the Markets']");
    private final By lessonIntro = By.partialLinkText("Introduction to the Financial Markets");
    private final WebDriverWait wait;

    public EducationalVideosPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickIntroLesson() {
        wait.until(ExpectedConditions.elementToBeClickable(chapterIntro));
        driver.findElement(chapterIntro).click();
        wait.until(ExpectedConditions.elementToBeClickable(lessonIntro));
        driver.findElement(lessonIntro).click();
    }
}
