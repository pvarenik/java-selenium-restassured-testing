import io.github.bonigarcia.wdm.WebDriverManager;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import constants.*;

public class UITest {
    private WebDriver driver;
    private String baseUrl;

    @BeforeClass(groups = "UI")
    public void setup() {
        baseUrl = System.getProperty("baseUrl", "https://www.xm.com");
    }


    /*
     * Height = 0 and width = 0 start browser maximized
     * 1024x768 and 800x600 are commented, because it starts the mobile view mode
     *                      and there is no calendar in the mobile view.
     */
    @DataProvider(name = "browsers")
    public Object[][] browsers() {
        return new Object[][]{
                {"chrome", 0, 0},
                {"chrome", 1920, 1080},
//                {"chrome", 1024, 768},
//                {"chrome", 800, 600},
                {"firefox", 0, 0},
                {"firefox", 1920, 1080},
//                {"firefox", 1024, 768},
//                {"firefox", 800, 600},
//                {"safari", 0, 0},
//                {"safari", 1920, 1080},
//                {"safari", 1024, 768},
//                {"safari", 800, 600},
                {"edge", 0, 0},
                {"edge", 1920, 1080},
//                {"edge", 1024, 768},
//                {"edge", 800, 600},
        };
    }


    @Test(dataProvider = "browsers", groups = "UI")
    public void testEconomicCalendar(String browser, int width, int height) throws InterruptedException {
        setupDriver(browser, width, height);
        if (width == 0 && height == 0) {
            driver.manage().window().maximize();
        }
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage(baseUrl);
        Assert.assertTrue(homePage.isAtHomePage());

        homePage.acceptCookies();

        homePage.clickResearchAndEducation();
        ResearchAndEducationPage researchPage = new ResearchAndEducationPage(driver);
        researchPage.clickEconomicCalendar();

        EconomicCalendarPage calendarPage = new EconomicCalendarPage(driver);
        Assert.assertTrue(calendarPage.isAtEconomicCalendar());

        calendarPage.switchToIframe();

        calendarPage.selectDate(Constants.TODAY);
        calendarPage.isDateCorrect(Constants.TODAY);

        calendarPage.selectDate(Constants.TOMORROW);
        calendarPage.isDateCorrect(Constants.TOMORROW);

        calendarPage.selectDate(Constants.NEXT_WEEK);
        calendarPage.isDateCorrect(Constants.NEXT_WEEK);

        calendarPage.switchBackToTheDefaultContent();

        homePage.clickResearchAndEducation();
        researchPage.clickEducationalVideos();
        EducationalVideosPage videosPage = new EducationalVideosPage(driver);
        videosPage.clickIntroLesson();

        VideoPage videoPage = new VideoPage(driver);
        videoPage.playVideo();
        Assert.assertTrue(videoPage.isVideoPlaying());
        Thread.sleep(5000); // playing video for 5 seconds
        Assert.assertTrue(videoPage.isVideoPlaying());
    }

    private void setupDriver(@NotNull String browser, int width, int height) {
        switch (browser) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(String.format("--window-size=%d,%d", width, height));
                driver = new ChromeDriver(options);
                break;
            }
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(String.format("--width=%d", width), String.format("--height=%d", height));
                driver = new FirefoxDriver(options);
                break;
            }
            case "safari": {
                WebDriverManager.safaridriver().setup();
                SafariOptions options = new SafariOptions();
                // Safari does not have window size options
                driver = new SafariDriver(options);
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments(String.format("--window-size=%d,%d", width, height));
                driver = new EdgeDriver(options);
                break;
            }
        }
    }

    @AfterMethod(groups = "UI")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
