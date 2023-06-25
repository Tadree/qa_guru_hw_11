package ru.jiehk;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class IssueNameCheckTest {
    public static final int ISSUE = 81;
    public static final String REPO = "eroshenkoam/allure-example";
    public static final String ISSUENAME = "issue_to_test_allure_report";


    @Test
    void issueNameCheckWithListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com");
        $(".header-search-input").setValue(REPO).pressEnter();
        $(linkText(REPO)).click();
        $("#issues-tab").click();
        $("#issue_" + ISSUE + "_link").shouldHave(text(ISSUENAME));
    }

    @Test
    void issueNameCheckWithLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPO + " с помощью поисковой строки", () -> {
            $(".header-search-input").setValue(REPO).pressEnter();
        });
        step("Переходим в репозиторий " + REPO + " из результатов поиска", () -> {
            $(linkText(REPO)).click();
        });
        step("Переходим во вкладку Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем, что Issues с номером " + ISSUE + " имеет название " + ISSUENAME, () -> {
            $("#issue_" + ISSUE + "_link").shouldHave(text(ISSUENAME));
            attachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES)));
        });
    }

    @Test
    void issueNameCheckWithAnnotationSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps webSteps = new WebSteps();
        webSteps
                .openMainPage()
                .searchForRepository(REPO)
                .clickOnRepositoryLink(REPO)
                .clickOnIssueTab()
                .issueNameCheck(ISSUE, ISSUENAME).
                takeScreenshot();
    }
}
