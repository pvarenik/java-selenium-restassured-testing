package pages;

import constants.Constants;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.testng.Assert;

public class EconomicCalendarPage {
    private final WebDriver driver;
    private final By slider = By.cssSelector("mat-slider[role='slider']");
    private final By highlightedDate = By.cssSelector("button[aria-pressed='true']");
    private final By sliderLabel = By.cssSelector("span.tc-timeframe");
    private final By pastEventsButton = By.cssSelector("button.tc-past-events-load-button");
    private final WebDriverWait wait;

    public EconomicCalendarPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isAtEconomicCalendar() {
        return driver.getTitle().contains("Economic Calendar");
    }

    public void switchToIframe() {
        WebElement iframe = driver.findElement(By.cssSelector("iframe[title='iframe']"));
        driver.switchTo().frame(iframe);

        wait.until(ExpectedConditions.visibilityOfElementLocated(pastEventsButton));
    }

    public void switchBackToTheDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void selectDate(@NotNull String period) {
        driver.findElement(sliderLabel).click();
        WebElement sliderElement = driver.findElement(slider);
        switch (period) {
            case Constants.TODAY:
                moveSlider(sliderElement, Constants.TODAY_SLIDER_MOVES);
                break;
            case Constants.TOMORROW:
                moveSlider(sliderElement, Constants.TOMORROW_SLIDER_MOVES);
                break;
            case Constants.NEXT_WEEK:
                moveSlider(sliderElement, Constants.NEXT_WEEK_SLIDER_MOVES);
                break;
        }
    }

    private void moveSlider(@NotNull WebElement sliderElement, int times) {
        sliderElement.sendKeys(Keys.HOME);
        for (int i = 0; i < times; i++) {
            sliderElement.sendKeys(Keys.ARROW_RIGHT);
        }
    }

    public void isDateCorrect(@NotNull String expectedDate) {
        Calendar c = Calendar.getInstance();
        wait.until(ExpectedConditions.visibilityOfElementLocated(highlightedDate));
        switch (expectedDate) {
            case Constants.TODAY:
                compareDates(c, driver.findElement(highlightedDate));
                break;
            case Constants.TOMORROW:
                c.setTime(new Date());
                c.add(Calendar.DATE, 1);
                compareDates(c, driver.findElement(highlightedDate));
                break;
            case Constants.NEXT_WEEK:
                int daysUntilNextMonday = 7 - c.get(Calendar.DAY_OF_WEEK) + 2; // getting days to the end of the week, then adding 2 to get Monday
                c.setTime(new Date());
                c.add(Calendar.DATE, daysUntilNextMonday);
                List<WebElement> dates = driver.findElements(highlightedDate);
                Assert.assertEquals(dates.size(), 2, "Time period was displayed incorrectly.");
                for (WebElement e : dates) {
                    compareDates(c, e);
                    c.add(Calendar.DATE, Constants.NEXT_WEEK_DAYS);
                }
                break;
        }
    }

    public void compareDates(@NotNull Calendar c, @NotNull WebElement e) {
        String dateActualText, dateActualAttribute, dateExpected, dateExpectedAttribute;
        dateExpected = new SimpleDateFormat(Constants.DATE_FORMAT_DAY).format(c.getTime());
        dateExpectedAttribute = new SimpleDateFormat(Constants.DATE_FORMAT_FULL).format(c.getTime());
        dateActualText = e.getText();
        dateActualAttribute = e.getAttribute("aria-label");
        Assert.assertEquals(dateActualAttribute, dateExpectedAttribute, "Incorrect full date.");
        Assert.assertEquals(dateActualText, dateExpected, "Incorrect date.");
    }
}
