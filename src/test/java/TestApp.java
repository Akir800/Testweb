import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button_view_extra")).click(); //Отправить
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void testNameHyphen() {
        driver.findElement(By.name("name")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.name("phone")).sendKeys("+79261549122");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void testNameEnglish() {
        driver.findElement(By.name("name")).sendKeys("Vladimir Gusaroff");
        driver.findElement(By.name("phone")).sendKeys("+79261549122");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void testNameNumber() {
        driver.findElement(By.name("name")).sendKeys("Vladimir1 Gusaroff1");
        driver.findElement(By.name("phone")).sendKeys("+79261549122");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void testNumberPlus() {
        driver.findElement(By.name("name")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.name("phone")).sendKeys("89261549122");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        //System.out.println(elements.get(1).getText());

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);

    }

    @Test
    public void testNumberTen() {
        driver.findElement(By.name("name")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.name("phone")).sendKeys("+79261549122.");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testNumberTwelve() {
        driver.findElement(By.name("name")).sendKeys("Владимир Малков-Бельский");
        driver.findElement(By.name("phone")).sendKeys("+792615491222");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

}