//Нужно исправить: Добавить package во все файлы
package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static pageObjects.Urls.URL;

public class MainPage {

    private final WebDriver driver;
//можно улучшить: вынести константу в отдельный класс
//Нужно исправить: Нейминг констант в верхнем регистре


    // Кнопка Заказать вверху страницы
    private final By topOrderButton = By.xpath("//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    // Кнопка Заказать внизу страницы
    private final By bottomOrderButton = By.xpath("//div[@class='Home_FinishButton__1_cWm']/button[text()='Заказать']");
    // Кнопка принятия cookies
    private final By cookieButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public void open(){
        driver.get(URL);
    }

    public void acceptCookie() {
        if (!driver.findElements(cookieButton).isEmpty()) {
            driver.findElement(cookieButton).click();
        }
    }

    public void clickTopOrderButton(){
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    public void clickBottomOrderButton(){
        WebElement button = new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(bottomOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(bottomOrderButton)).click();
    }

    private By faqQuestion(String questionText) {
        return By.xpath("//div[@class='accordion__heading' and contains(., '" + questionText + "')]");
    }

    private By faqAnswer(String questionText) {
        return By.xpath("//div[@class='accordion__panel' and preceding-sibling::div[@class='accordion__heading' and contains(., '" + questionText + "')]]");
    }

    public void clickFaqQuestion(String questionText) {
        WebElement question = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.presenceOfElementLocated(faqQuestion(questionText)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(faqQuestion(questionText)))
                .click();
    }

    public String getFaqAnswerText(String questionText) {
        return new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(faqAnswer(questionText)))
                .getText();
    }
}


