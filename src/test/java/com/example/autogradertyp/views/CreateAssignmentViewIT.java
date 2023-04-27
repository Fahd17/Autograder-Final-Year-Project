package com.example.autogradertyp.views;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.datetimepicker.testbench.DateTimePickerElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.select.testbench.SelectElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.flow.component.upload.testbench.UploadElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateAssignmentViewIT extends TestBenchTestCase {


    public WebDriver setup(String route){
        System.setProperty("webdriver.chrome.driver", ".\\src\\driver\\chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(option);
        setDriver(driver);
        driver.get("http://localhost:8089/" + route);
        return driver;
    }

    @Test
    public void createAssignmentCheck () {
        WebDriver driver = setup("login");
        LoginFormElement login = $(LoginFormElement.class).first();
        login.getUsernameField().setValue("F1");
        login.getPasswordField().setValue("111");
        login.getSubmitButton().click();

        ButtonElement manageCourses = $(ButtonElement.class).id("ManageCourses");
        manageCourses.click();
        ButtonElement newAssignment = $(ButtonElement.class).id("NewAssignment");
        newAssignment.click();
        TextFieldElement assignmentName = $(TextFieldElement.class).id("AssignmentName");
        assignmentName.setValue("TestAssignment");
        SelectElement selectCourse = $(SelectElement.class).id("SelectCourse");
        selectCourse.selectByText("Crypto");

        DateTimePickerElement deadlinePicker = $(DateTimePickerElement.class).id("DeadlinePicker");
        deadlinePicker.setDateTime(LocalDateTime.of(2023, 4, 28, 11, 50));

        UploadElement testCasesUpload = $(UploadElement.class).id("TestCaseFileUpload");
        File file = new File("C:\\Users\\fahds\\IdeaProjects\\autograder-TYP\\" +
                "submissions_directory\\factorial_test_cases");
        testCasesUpload.upload(file);
        ButtonElement createAssignment = $(ButtonElement.class).id("CreateAssignment");
        createAssignment.click();
        String assignmentMenuURL = "http://localhost:8089/assignments";
        driver.navigate().to(assignmentMenuURL);

        ButtonElement assignmentOption = $(ButtonElement.class).id("TestAssignment");
        assignmentOption.click();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertNotEquals(actualUrl, assignmentMenuURL);
    }


}
