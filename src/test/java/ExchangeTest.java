import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.sun.jndi.toolkit.url.Uri;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ExchangeTest {
    ExchangePage exchangePage = new ExchangePage();
    ExchangePage.Header header = new ExchangePage.Header();
    ExchangePage.Footer footer = new ExchangePage.Footer();
//    protected WebDriver driver;

    @BeforeClass
    public static void setUp() {
//        ChromeDriverManager.getInstance().setup();
        Configuration.baseUrl = "https://www.tinkoff.ru";
        ExchangePage exchangePage = new ExchangePage();

    //1. Перейти на https://www.tinkoff.ru/about/exchange/ (страница курса валют в tinkoff.ru)
        exchangePage.open();
    }

   @Test
    public void isExistsPageElements() {
        int i = 0;
        exchangePage.isExistElement(Page.Header.header);
        exchangePage.isExistElement(Page.Header.logo);
         while (i < header.links.size()) {   //просто прокликиваем по всем видимым ссылкам и проверяем наличие хедера
            header.links.get(i).click();
            Page.Header.header.shouldBe(Condition.exist);
            exchangePage.back();
            i++;
        }
        //нужно ещё прокликать скрытые
        //разобраться с chromedriver и mouseMove
       // System.out.println(exchangePage.headerHiddenLinks);
       // exchangePage.moveToElement(exchangePage.headerAdditionalMenu);
    }


    @Test
    public void checkActiveLinkIsHighlighted() { //4. Проверить, что выделен раздел "Курсы валют"
        Assert.assertTrue(exchangePage.isActive(header.highlightedLink));
    }

    @Test
    public void checkFooterLinks() {
        footer.links.get(0).shouldBe(Condition.visible);
        System.out.println(footer.links.size());
    }


}
