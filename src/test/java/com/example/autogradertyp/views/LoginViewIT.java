package com.example.autogradertyp.views;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;


import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginViewIT extends TestBenchTestCase {


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
    public void loginAsValidUser() {
        WebDriver driver = setup("lgoin");
        LoginFormElement login = $(LoginFormElement.class).first();
        login.getUsernameField().setValue("F1");
        login.getPasswordField().setValue("111");
        login.getSubmitButton().click();
        Assert.assertFalse($(LoginFormElement.class).exists());
        driver.close();
    }

    @Test
    public void loginAsInvalidUser() {
        WebDriver driver = setup("lgoin");
        LoginFormElement login = $(LoginFormElement.class).first();
        login.getUsernameField().setValue("F1");
        login.getPasswordField().setValue("invaild");
        login.getSubmitButton().click();
        Assert.assertTrue($(LoginFormElement.class).exists());
        driver.close();
    }

    @Test
    public void RegisterUser() {
        WebDriver driver = setup("register");
        TextFieldElement studentNumber = $(TextFieldElement.class).id("studentNumber");
        TextFieldElement userName = $(TextFieldElement.class).id("userName");
        TextFieldElement email = $(TextFieldElement.class).id("email");
        TextFieldElement password = $(TextFieldElement.class).id("password");
        ButtonElement createButton = $(ButtonElement.class).id("createUserButton");

        studentNumber.setValue("201580");
        userName.setValue("Fahd Alsahali");
        email.setValue("fahd@gmail.com");
        password.setValue("123");
        createButton.click();
        LoginFormElement login = $(LoginFormElement.class).first();
        login.getUsernameField().setValue("Fahd Alsahali");
        login.getPasswordField().setValue("123");
        login.getSubmitButton().click();
        Assert.assertFalse($(LoginFormElement.class).exists());
        driver.close();
    }

}
