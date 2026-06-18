
import org.junit.*;
import pageObjects.MainPage;
import pageObjects.OrderPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Parameterized.class)
public class OrderTest{

    private static WebDriver driver;

    private final String button;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String comment;

    public OrderTest(String button, String name, String surname, String address, String metro, String phone, String date, String comment){
        this.button = button;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"top", "Никита", "Никитов", "Москва, ул. Пушкина, 1", "Пыхтино", "89885553535", "20.06.2026", "Хорошего дня"},
                {"bottom", "Иван", "Иванов", "Москва, ул. Наташи Ковшовой, 20", "Сокольники", "89183553535", "19.06.2026", "Спасибо"}
        };
    }


    @BeforeClass
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

// Попался баг при создании заказа в Хром. Всё работает в Мозиле
/*    @BeforeClass
    public static void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }*/

    @Test
    public void checkOrderFlow() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.open();
        mainPage.acceptCookie();

        if (button.equals("top")) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }


        orderPage.firstForm(name, surname, address, metro, phone);
        orderPage.secondForm(date, comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmButton();

        Assert.assertTrue(orderPage.getSuccessfulOrder().contains("Заказ оформлен"));

    }

    @AfterClass
    public static void teardown() {
        driver.quit();
    }

}









