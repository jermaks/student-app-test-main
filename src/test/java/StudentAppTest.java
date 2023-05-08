import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.AddStudentPage;
import page_objects.AllStudentsPage;
import page_objects.EditStudentPage;
import page_objects.Notifications;

import java.lang.reflect.Method;
import java.time.Duration;

import static constants.AllConstants.GenderConstants.*;
import static constants.AllConstants.Messages.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.ConfigHelper.getConfig;
import static utils.DriverManager.*;

public class StudentAppTest {
    private WebDriverWait driverWait;
    private AllStudentsPage allStudentsPage;
    private AddStudentPage addStudentPage;
    private Notifications notifications;
    private EditStudentPage editStudentPage;

    private final Faker dataFaker = new Faker();

    @BeforeMethod(alwaysRun = true)
    public void initialize(Method method) {
        testName = (method.getName());
        driverWait = new WebDriverWait(getInstance(), Duration.ofSeconds(10));
        getInstance().get(getConfig().getString("student.app.hostname"));
        allStudentsPage = new AllStudentsPage();
        addStudentPage = new AddStudentPage();
        notifications = new Notifications();
        editStudentPage = new EditStudentPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!getConfig().getBoolean("student.app.run.locally")) markRemoteTest(result);
        closeDriver();
    }

    @Test(description = "Add male gender student and check successful message")
    public void addMaleGenderStudent() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.waitAndSetGender(MALE);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_SUCCESSFULLY_ADDED);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_ADDED_TO_THE_SYSTEM, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Add female gender student and check successful message")
    public void addFemaleGenderStudent() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.waitAndSetGender(FEMALE);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_SUCCESSFULLY_ADDED);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_ADDED_TO_THE_SYSTEM, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Add other gender student and check successful message")
    public void addOtherGenderStudent() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.waitAndSetGender(OTHER);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_SUCCESSFULLY_ADDED);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_ADDED_TO_THE_SYSTEM, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Attempt to add student without name and check error message")
    public void addStudentWithoutName() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String email = dataFaker.internet().emailAddress();
        addStudentPage.waitAndSetValueForNameField("");
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.waitAndSetGender(MALE);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getTextFromNameFieldErrorMessage(), NAME_FIELD_ERROR_MESSAGE);
    }

    @Test(description = "Attempt to add student without email and check error message")
    public void addStudentWithoutEmail() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField("");
        addStudentPage.waitAndSetGender(MALE);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getTextFromEmailFieldErrorMessage(), EMAIL_FIELD_ERROR_MESSAGE);
    }

    @Test(description = "Attempt to add student without gender and check error message")
    public void addStudentWithoutGender() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getTextFromGenderFieldErrorMessage(), GENDER_FIELD_ERROR_MESSAGE);
    }

    @Test(description = "Attempt to add student with incorrect email type and check error message")
    public void addStudentWithIncorrectEmail() {
        allStudentsPage.waitAndClickOnAddStudentButton();

        String name = dataFaker.name().firstName();
        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField("incorrect.email.com");
        addStudentPage.waitAndSetGender(MALE);
        addStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), INCORRECT_EMAIL_TYPE_ERROR);
        assertEquals(notifications.getDescriptionFromNotification(), VALIDATION_FAILED_400);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Delete first student and check successful message")
    public void deleteFirstStudent() {
        allStudentsPage.waitAndClickOnFirstStudentDeleteButton();
        allStudentsPage.clickYesToDeleteAStudent();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_DELETED);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Delete last student on current page and check successful message")
    public void deleteLastStudentOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentDeleteButtonOnCurrentPage();
        allStudentsPage.clickYesToDeleteAStudent();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_DELETED);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student name and check successful message")
    public void editFirstStudentName() {
        allStudentsPage.waitAndClickOnFirstStudentEditButton();

        String name = dataFaker.name().firstName();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit last student name on current page and check successful message")
    public void editLastStudentNameOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentEditButtonOnCurrentPage();

        String name = dataFaker.name().firstName();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student email and check successful message")
    public void editFirstStudentEmail() {
        allStudentsPage.waitAndClickOnFirstStudentEditButton();

        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit last student email on current page and check successful message")
    public void editLastStudentEmailOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentEditButtonOnCurrentPage();

        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student gender to other and check successful message")
    public void editFirstStudentGenderToOther() {
        allStudentsPage.waitAndClickOnFirstStudentEditButton();

        editStudentPage.waitAndEditGender(OTHER);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit last student gender to other on current page and check successful message")
    public void editLastStudentGenderToOtherOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentEditButtonOnCurrentPage();

        editStudentPage.waitAndEditGender(OTHER);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student name and email and check successful message")
    public void editFirstStudentNameAndEmail() {
        allStudentsPage.waitAndClickOnFirstStudentEditButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit last student name and email on current page and check successful message")
    public void editLastStudentNameAndEmailOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentEditButtonOnCurrentPage();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student name, email and gender and check successful message")
    public void editFirstStudentNameEmailAndGender() {
        allStudentsPage.waitAndClickOnFirstStudentEditButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.waitAndEditGender(OTHER);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Edit first student name, email and gender on current page and check successful message")
    public void editLastStudentNameEmailAndGenderOnCurrentPage() {
        allStudentsPage.waitAndClickOnLastStudentEditButtonOnCurrentPage();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();
        editStudentPage.waitAndEditValueForNameField(name);
        editStudentPage.waitAndEditValueForEmailField(email);
        editStudentPage.waitAndEditGender(OTHER);
        editStudentPage.clickOnSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_UPDATE_WAS_SUCCESSFUL);
        assertEquals(notifications.getDescriptionFromNotification(), String.format(WAS_UPDATED, name));

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test()
    public void checkTitle() {
        assertEquals(getInstance().getTitle(), "acodemy - React App");
    }
}