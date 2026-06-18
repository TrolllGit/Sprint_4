
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.MainPage;
import static org.junit.Assert.assertEquals;

// Нужно исправить: отсутствуют импорты

@RunWith(Parameterized.class)
public class FaqTest {

    private static WebDriver driver;

    private final String questionText;
    private final String expectedAnswer;

    public FaqTest(String questionText, String expectedAnswer){
        this.questionText = questionText;
        this.expectedAnswer = expectedAnswer;
    }

    //Можно улучшить: открытие и закрытие драйвера нужно для всех тестов, можно вынести их на уровень выше
    @BeforeClass
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    //можно улучшить: Добавить @Parameterized.Parameters(name = "...") для лучшей читаемости
    @Parameterized.Parameters(name = "{0}")
    public static Object[][] expectedAnswer() {
        return new Object[][]  {

//Можно улучшить: сверять с текстом вопроса, а не с номером, порядок может измениться
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }



    @Test
    public void faqAnswerShouldBeVisibleAfterClick(){
        MainPage mainPage = new MainPage(driver);

        mainPage.open();
        mainPage.acceptCookie();
        mainPage.clickFaqQuestion(questionText);

        String actualAnswer = mainPage.getFaqAnswerText(questionText);
        assertEquals(expectedAnswer, actualAnswer);
    }


    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}