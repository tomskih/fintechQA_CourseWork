package uitest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.ui.pages.ExchangePage;
import models.ui.pages.Page;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
    @DisplayName("Проверка наличия элементов на странице")
    @Description("This is an example test suite")
    public void isExistsPageElements() {
        int i = 0;
        exchangePage.isExistElement(Page.Header.header);
        exchangePage.isExistElement(Page.Header.logo);
        while (i < header.links.size()) {   //?????? ???????????? ?? ???? ??????? ??????? ? ????????? ??????? ??????
            header.links.get(i).click();
            Page.Header.header.shouldBe(Condition.exist);
            exchangePage.back();
            i++;
        }
        //????? ??? ?????????? ???????
        //??????????? ? chromedriver ? mouseMove
        // System.out.println(exchangePage.headerHiddenLinks);
        // exchangePage.moveToElement(exchangePage.headerAdditionalMenu);
    }


    @Test
    public void checkActiveLinkIsHighlighted() { //4. ?????????, ??? ??????? ?????? "????? ?????"
        Assert.assertTrue(exchangePage.isActive(header.highlightedLink));
    }

    @Test
    public void checkFooterLinks() {
        footer.links.get(0).shouldBe(Condition.visible);
        System.out.println(footer.links.size());
    }

    @Test
    public void checkDefaultCurrencySelect() {
        String LeftSelect = exchangePage.currencySelect.get(0).getText();
        String RightSelect = exchangePage.currencySelect.get(1).getText();
        String sellRateTitle = exchangePage.operationCurrenciesAndType.get(0).getText();
        String buyRateTitle = exchangePage.operationCurrenciesAndType.get(1).getText();

        Assert.assertEquals(LeftSelect, "Рубль");
        Assert.assertEquals(RightSelect, "Евро");

        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(sellRateTitle, LeftSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(buyRateTitle, LeftSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(sellRateTitle, RightSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(buyRateTitle, RightSelect));
    }

    @Test
    public void checkCurrencyOnPage() {
        exchangePage.getRatesOnPage();
    }

//    @Test
//    public void checkNewCurrencySelect() {
//        System.out.println(exchangePage.currencySelect.get(0).getText());
//        exchangePage.currencySelect
//                .get(0)
//                .click();
//        $(By.xpath(".//div[2]/div[3]/div/div")).click();
//
//        System.out.println(exchangePage.currencySelect.get(0).getText());
//
//        ;


 //   }


}
