package pages;

import constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VideoPage {
    private final WebDriver driver;
    private final By videoFrame = By.cssSelector("div.videowrapper > iframe");
    private final By videoPlayer = By.cssSelector("div.player");
    private final WebDriverWait wait;

    public VideoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Constants.WAIT_DURATION);
    }

    public void playVideo() {
        switchToIframe();
        wait.until(ExpectedConditions.elementToBeClickable(videoPlayer));
        wait.until(ExpectedConditions.attributeContains(videoPlayer, "class", "paused"));
        WebElement video = driver.findElement(videoPlayer);
        video.click();
        switchBackToTheDefaultContent();
    }

    public void switchToIframe() {
        WebElement iframe = driver.findElement(videoFrame);
        driver.switchTo().frame(iframe);
    }

    public void switchBackToTheDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public boolean isVideoPlaying() {
        switchToIframe();
        wait.until(ExpectedConditions.attributeContains(videoPlayer, "class", "playing"));
        boolean result = driver.findElement(videoPlayer).getAttribute("class").contains("playing");
        switchBackToTheDefaultContent();
        return result;
    }
}
