
package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;


public class OrderPage {

    private final WebDriver driver;

    private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By metroStationOption = By.xpath(".//div[contains(@class,'select-search__select')]//li[1]");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");
    private final By dataField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    private final By rentalPeriodOption = By.xpath("//div[contains(@class,'Dropdown-menu')]//div[contains(@class,'Dropdown-option')][1]");
    private final By checkbox = By.id("black");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");
    private final By confirmButton = By.xpath(".//button[text()='Да']");
    private final By successfulOrder = By.xpath("//div[contains(@class,'Order_Modal') and not(contains(.,'Хотите оформить заказ?'))]");
    private final By confirmModal = By.xpath("//div[contains(@class,'Order_Modal__')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void firstForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metro);

        WebElement station = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(metroStationOption));
        station.click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void secondForm(String date, String comment){
        WebElement dateInput = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(dataField));

        dateInput.click();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("react-datepicker")));

        WebElement rental = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        rental.click();

        WebElement period = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(rentalPeriodOption));
        period.click();

        driver.findElement(checkbox).click();
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton(){
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void clickConfirmButton(){
        WebElement modal = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(confirmModal));

        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(confirmButton)).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(modal));
    }


    public String getSuccessfulOrder(){
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(successfulOrder)).getText();
    }

}
