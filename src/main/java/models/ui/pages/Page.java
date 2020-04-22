package models.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class Page {

    public static class Header {
        public static SelenideElement header = Selenide.$(By.xpath("//div[@data-qa-file='htmlToReactElementWithInnerHtml']"));
        public static SelenideElement logo = header.$(By.xpath("./div/div/a[@href='/']"));
        public ElementsCollection links= header.$$(By.tagName("a")).filterBy(Condition.visible);
        public ElementsCollection hiddenLinks= header.$$(By.tagName("a")).filterBy(Condition.not(Condition.visible));
        public WebElement additionalMenu = header.$(By.tagName("svg"));
        public SelenideElement highlightedLink = links.find(Condition.cssClass("header__n-Ztx"));
    }

    public static class Footer {
        public SelenideElement footer = Selenide.$(By.xpath("//div[@data-module-type='productFooter']"));
        public ElementsCollection links= footer.$$(By.tagName("a")).filterBy(Condition.visible);
    }

    public Page open(){
        Selenide.open("/");
        return this;
    }

    public Page back(){
        Selenide.back();
        return this;
    }

    public void isExistElement(SelenideElement element){
        element.shouldBe(Condition.visible);
    }

    public boolean isActive(SelenideElement element) {
        String linkHref = element.getAttribute("href");
        System.out.println(linkHref);
        if (linkHref.equals(getWebDriver().getCurrentUrl())) {
            return true;
        } else {
            return false;
        }
    }

//    public String currentRelativeUrl() throws MalformedURLException {
//        return new Uri(getWebDriver().getCurrentUrl()).getPath();
//    }




//    public void moveToElement(WebElement element) {
//        action.moveToElement(element);
//    }
}
