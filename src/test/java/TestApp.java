import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApp {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @Test
    public void test() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Яна");
        elements.get(1).sendKeys("+79151555755");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameHyphen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79261549122");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameEnglish() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vladimir Gusaroff");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79261549122");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vladimir1 Gusaroff1");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79261549122");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testPhoneNumberNoPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89261549122");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNumberTen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+792615491");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNumberTwelve() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+792615491223");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79261549122");
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBoxAndNamePhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String actual = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void testNameNull() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79261549122");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String actual = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }}
