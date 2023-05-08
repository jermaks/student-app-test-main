package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

public class Notifications {

    private final WebDriverWait webDriverWait;

    public Notifications() {
        WebDriver driver = DriverManager.getInstance();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-notice-message")
    private WebElement notificationMessageElement;

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-notice-description")
    private WebElement notificationDescriptionElement;

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-notice-close")
    private WebElement popUpCloseButton;

    @FindBy(how = How.XPATH, using = "//div[@id='name_help']/div")
    private WebElement nameFieldErrorMessage;

    @FindBy(how = How.XPATH, using = "//div[@id='email_help']/div")
    private WebElement emailFieldErrorMessage;

    @FindBy(how = How.XPATH, using = "//div[@id='gender_help']/div")
    private WebElement genderFieldErrorMessage;

    public WebElement getPopUpCloseButton() {
        return popUpCloseButton;
    }

    public String getMessageFromNotification() {
        webDriverWait.until(ExpectedConditions.visibilityOf(notificationMessageElement));
        return notificationMessageElement.getText();
    }

    public String getDescriptionFromNotification() {
        webDriverWait.until(ExpectedConditions.visibilityOf(notificationDescriptionElement));
        return notificationDescriptionElement.getText();
    }

    public String getTextFromNameFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(nameFieldErrorMessage));
        return nameFieldErrorMessage.getText();
    }

    public String getTextFromEmailFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(emailFieldErrorMessage));
        return emailFieldErrorMessage.getText();
    }

    public String getTextFromGenderFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(genderFieldErrorMessage));
        return genderFieldErrorMessage.getText();
    }
}