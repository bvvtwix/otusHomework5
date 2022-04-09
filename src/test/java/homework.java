import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class homework {

    private Logger logger = LogManager.getLogger(homework.class);
    private ConfigServer cfg = ConfigFactory.create(ConfigServer.class);
    private WebDriver driver;

    private String kioskMode = "--kiosk";
    private String headlessMode = "headless";
    private String maxMode = "--start-maximized";

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void initDriver(String mode){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(mode);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    // 1)
    @Test
    public void headless() throws InterruptedException {
        // Открыть Chrome в headless режиме
        initDriver(headlessMode);

        // Перейти на https://duckduckgo.com/
        driver.get(cfg.urlDuckDuck());
        logger.info("duckduckgo opened");

        // В поисковую строку ввести ОТУС
        driver.findElement(By.id("search_form_input_homepage")).sendKeys("ОТУС", Keys.ENTER);
        logger.info("try to find ОТУС");

        // Проверить что в поисковой выдаче первый результат Онлайн‑курсы для профессионалов, дистанционное обучение
        String actualString = driver.findElement(By.xpath("//div[@id='r1-0']//a[contains(text(), 'Онлайн‑курсы для профессионалов')]")).getText();
        Assert.assertTrue(actualString.contains("Онлайн‑курсы для профессионалов"));
        logger.info("ОТУС found");
    }

    // 2)
    @Test
    public void kiosk() throws InterruptedException {
        //Открыть Chrome в режиме киоска
        initDriver(kioskMode);

        //Перейти на https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818
        driver.get( cfg.urlw3layouts());
        logger.info("demo.w3layouts.com opened");

        //Нажать на любую картинку
        driver.findElement(By.xpath("//li[@data-id='id-1']")).click();
        logger.info("click to picture");

        //Проверить что картинка открылась в модальном окне
        Assert.assertTrue(driver.findElement(By.cssSelector(".pp_content_container")).isDisplayed());
        logger.info("modal window found");
    }

    // 3)
    @Test
    public void cookies() throws InterruptedException {
        // Открыть Chrome в режиме полного экрана
        initDriver(maxMode);
        logger.info("full screen browser opened");

        //Перейти на https://otus.ru
        driver.get(cfg.urlOtus());
        logger.info("otus opened");

        // Авторизоваться под каким-нибудь тестовым пользователем
        auth();

        // Вывести в лог все cookie
        printCookies();
    }

    private void printCookies(){
        logger.info("Cookies:");

        Set<Cookie> coolies = driver.manage().getCookies();

        for (Cookie cookie : coolies){
            logger.info(String.format("%s : %s" , cookie.getName(), cookie.getValue()));
        }

    }

    private void auth() throws InterruptedException {
        driver.findElement(By.cssSelector("button[data-modal-id=\"new-log-reg\"]")).click();
        driver.findElement(By.cssSelector("input.new-input[name='email'][type='text']")).sendKeys(cfg.email());
        driver.findElement(By.cssSelector("input.new-input[name='password'][type='password']")).sendKeys(cfg.pass());
        driver.findElement(By.cssSelector("button[type='submit'][class='new-button new-button_full new-button_blue new-button_md']")).click();
        logger.info("Auth completed");
    }

}
