package ru.jiehk;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {

     @Step("Открываем главную страницу")
    public WebSteps openMainPage(){
         open("https://github.com");
         return this;
     }
     @Step("Ищем репозиторий {repo} с помощью поисковой строки")
    public WebSteps searchForRepository(String repo){
         $(".header-search-input").setValue(repo).pressEnter();
         return this;
     }
     @Step("Переходим в репозиторий {repo} из результатов поиска")
    public WebSteps clickOnRepositoryLink(String repo){
         $(linkText(repo)).click();
         return this;
     }
     @Step("Переходим во вкладку Issues")
    public WebSteps  clickOnIssueTab(){
         $("#issues-tab").click();
         return this;
     }
     @Step("Проверяем, что Issues с номером {number} имеет название {repo}")
    public WebSteps issueNameCheck(int number, String repo){
         $("#issue_" + number + "_link").shouldHave(text(repo));
         return this;
     }
     @Attachment(value =  "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot(){
         return ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
     }
}





