package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AllStudentsPage {

    private final WebDriverWait webDriverWait;

    public AllStudentsPage() {
        WebDriver driver = DriverManager.getInstance();
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='ant-table-title']//button")
    private WebElement addStudentButton;

    @FindBy(how = How.XPATH, using = "//label[@class='ant-radio-button-wrapper css-diro6f']/span[text()='Delete']")
    private WebElement firstStudentDeleteButtonOnPage;

    @FindBy(how = How.XPATH, using = "//tr[@class='ant-table-row ant-table-row-level-0'][last()]/td[last()]/div/label/span[2]")
    private WebElement lastStudentDeleteButtonOnPage;

    @FindBy(how = How.XPATH, using = "//button[@class='ant-btn css-diro6f ant-btn-primary ant-btn-sm']/span")
    private WebElement yesButtonToDeleteStudent;

    public void waitAndClickOnAddStudentButton() {
        webDriverWait.until(elementToBeClickable(addStudentButton));
        addStudentButton.click();
    }

    public void waitAndClickOnFirstStudentDeleteButtonOnAnyPage() {
        webDriverWait.until(elementToBeClickable(firstStudentDeleteButtonOnPage));
        firstStudentDeleteButtonOnPage.click();
    }

    public void waitAndClickOnLastStudentDeleteButtonOnAnyPage() {
        webDriverWait.until(elementToBeClickable(lastStudentDeleteButtonOnPage));
        lastStudentDeleteButtonOnPage.click();
    }

    public void clickYesToDeleteAStudent() {
        webDriverWait.until(elementToBeClickable(yesButtonToDeleteStudent));
        yesButtonToDeleteStudent.click();
    }

    public WebElement getAddStudentButton() {
        return addStudentButton;
    }
}