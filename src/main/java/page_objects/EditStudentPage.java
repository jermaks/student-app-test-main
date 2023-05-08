package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

public class EditStudentPage {

    private final WebDriver driver = DriverManager.getInstance();
    private final WebDriverWait webDriverWait;

    public EditStudentPage() {
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "name")
    private WebElement nameField;

    @FindBy(how = How.ID, using = "gender")
    private WebElement genderDropDown;

    @FindBy(how = How.XPATH, using = "//div[@class='ant-select ant-select-in-form-item css-diro6f ant-select-single ant-select-show-arrow']")
    private WebElement editGenderDropDown;

    @FindBy(how = How.ID, using = "email")
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = "//div[@class='ant-form-item-control-input-content']//button")
    private WebElement submitButton;

    public void waitAndClearValueForNameField() {
        webDriverWait.until(ExpectedConditions.visibilityOf(nameField));
        nameField.clear();
    }

    public void waitAndEditValueForNameField(String name) {
        webDriverWait.until(ExpectedConditions.visibilityOf(nameField));
        nameField.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        nameField.sendKeys(name);
    }

    public void waitAndEditGender(String gender) {
        editGenderDropDown.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='" + gender.toUpperCase() + "']")));
        driver.findElement(By.xpath("//div[@title='" + gender.toUpperCase() + "']")).click();
    }

    public void waitAndEditValueForEmailField(String email) {
        webDriverWait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        emailField.sendKeys(email);
    }

    public void clickOnSubmitButton() {
        webDriverWait.until(ExpectedConditions.visibilityOf(submitButton));
        submitButton.click();
    }
}