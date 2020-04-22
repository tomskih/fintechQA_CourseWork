package uitest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.ui.pages.ExchangePage;
import models.ui.pages.Page;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.apache.http.HttpStatus.SC_OK;

public class ExchangeTest {
    ExchangePage exchangePage = new ExchangePage();
    ExchangePage.Header header = new ExchangePage.Header();
    ExchangePage.Footer footer = new ExchangePage.Footer();

    @BeforeClass
    public static void setUp() {
//        ChromeDriverManager.getInstance().setup();
        Configuration.baseUrl = "https://www.tinkoff.ru";
        ExchangePage exchangePage = new ExchangePage();
    }

    @Test
    @DisplayName("UI-�����")
    @Description("��� ui-�����")
    public void ExchangeTest() throws IOException {
        openPage();
//        isPageLoad();
//        isExistsPageElements();
//        checkActiveLinkIsHighlighted();
     //   checkFooterLinks();
        checkDefaultCurrencySelect();
        checkCurrencyOnPage();
    }

    @Step ("1. ������� �� https://www.tinkoff.ru/about/exchange/ (�������� ����� ����� � tinkoff.ru")
    public void openPage() {
        exchangePage.open();
    }
    @Step ("2. ���������, ��� �������� ������������� �����������")
    public void isPageLoad()  {
        RestAssured.when()
                .get(Configuration.baseUrl)
                .then().assertThat().statusCode(SC_OK);
    }

    @Step ("3. ���������, ��� ���������� ����� �� ����� ��������� ����������. ����� �� ��������� ����������� ���� ������")
    @Description("���������, ��� ���� �����, ����, ������, � ��������� ��� ������ �������")
    public void isExistsPageElements() {
        int i = 0;
        exchangePage.isExistElement(Page.Header.header);
        exchangePage.isExistElement(Page.Header.logo);
        while (i < header.links.size()) {
            String href = header.links.get(i).getAttribute("href");
            RestAssured.when()
                    .get(href)
                    .then().assertThat().statusCode(SC_OK);
            i++;
        }
    }


    @Step ("4. ���������, ��� ������� ������ '����� �����'")
    public void checkActiveLinkIsHighlighted() {
        Assert.assertTrue(exchangePage.isActive(header.highlightedLink));
    }

    @Step ("5. ���������, ��� ��������� ������������ ����� �� ����� ��������� ����������. ����� �� ��������� ����������� ������")
    public void checkFooterLinks() {
        int i = 0;
        footer.links.get(0).shouldBe(Condition.visible);
        System.out.println(footer.links.size());
        while (i < footer.links.size()) {
            System.out.println(footer.links.get(i).getAttribute("href"));
            String href = footer.links.get(i).getAttribute("href");
            RestAssured.when()
                    .get(href)
                    .then().assertThat().statusCode(SC_OK);
            i++;
        }
    }

    @Step ("6. ���������, ��� �� ��������� � ���������� ������ ����� ���������� �����-->���� �������������, � � ������� ���� ���� � �����")
    public void checkDefaultCurrencySelect() {
        String LeftSelect = exchangePage.currencySelect.get(0).getText();
        String RightSelect = exchangePage.currencySelect.get(1).getText();
        System.out.println(exchangePage.operationCurrenciesAndType);
        String sellRateTitle = exchangePage.operationCurrenciesAndType.get(1).getText();
        String buyRateTitle = exchangePage.operationCurrenciesAndType.get(2).getText();

        Assert.assertEquals(LeftSelect, "�����");
        Assert.assertEquals(RightSelect, "����");

        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(sellRateTitle, LeftSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(buyRateTitle, LeftSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(sellRateTitle, RightSelect));
        Assert.assertTrue(exchangePage.areSelectedCurrenciesDisplayed(buyRateTitle, RightSelect));
    }

    @Step
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
