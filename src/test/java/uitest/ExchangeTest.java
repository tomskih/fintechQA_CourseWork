package uitest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import models.ui.pages.ExchangePage;
import models.ui.pages.Page;

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

    @Test
    public void checkDefaultCurrencySelect() {
        System.out.println(exchangePage.CurrencySelect);
        System.out.println(exchangePage.CurrencySelect.size());
    }


}
