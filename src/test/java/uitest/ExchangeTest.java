package uitest;
import apitest.TestBase;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.google.gson.Gson;
import endpoints.CBREndPoints;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import models.api.BaseResponse;
import models.ui.pages.ExchangePage;
import models.ui.pages.Page;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDate;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.IsEqual.equalTo;

public class ExchangeTest extends TestBase{
    ExchangePage exchangePage = new ExchangePage();
    ExchangePage.Header header = new ExchangePage.Header();
    ExchangePage.Footer footer = new ExchangePage.Footer();

    ThreadLocal<ValidatableResponse> response = new ThreadLocal<>();
    Gson gson = new Gson();

    public ExchangeTest() throws IOException {
        super("cbr_api.uri");
    }

    @BeforeClass
    public static void setUp() {
//        ChromeDriverManager.getInstance().setup();
        Configuration.baseUrl = "https://www.tinkoff.ru";
        ExchangePage exchangePage = new ExchangePage();
    }

    @Test
    @DisplayName("UI-тесты")
    @Description("Все ui-тесты")
    public void ExchangeTest() throws IOException {
        openPage();
        isPageLoad();
        isExistsPageElements();
        checkActiveLinkIsHighlighted();
        checkFooterLinks();
        checkDefaultCurrencySelect();
        checkCurrencyOnPage();
    }

    @Step ("1. Перейти на https://www.tinkoff.ru/about/exchange/ (страница курса валют в tinkoff.ru")
    public void openPage() {
        exchangePage.open();
    }
    @Step ("2. Проверить, что страница действительно загрузилась")
    public void isPageLoad()  {
        RestAssured.when()
                .get(Configuration.baseUrl)
                .then().assertThat().statusCode(SC_OK);
    }

    @Step ("3. Проверить, что загрузился хэдер со всеми основными элементами. Здесь же проверить доступность всех ссылок")
    @Description("Проверяем, что есть хедер, лого, ссылки, и проверяем что ссылки рабочие")
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


    @Step ("4. Проверить, что выделен раздел 'Курсы валют'")
    public void checkActiveLinkIsHighlighted() {
        Assert.assertTrue(exchangePage.isActive(header.highlightedLink));
    }

    @Step ("5. Проверить, что корректно отображается футер со всеми основными элементами. Здесь же проверить доступность ссылок")
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

    @Step ("6. Проверить, что по умолчанию в селекторах выбора валют выставлены Рубль-->Евро соотвественно, а в таблице курс Евро к Рублю")
    public void checkDefaultCurrencySelect() {
        String LeftSelect = exchangePage.currencySelect.get(0).getText();
        String RightSelect = exchangePage.currencySelect.get(1).getText();
        String sellRateTitle = exchangePage.operationCurrenciesAndType.get(1).getText();
        String buyRateTitle = exchangePage.operationCurrenciesAndType.get(2).getText();

        Assert.assertEquals(LeftSelect, "Рубль");
        Assert.assertEquals(RightSelect, "Евро");

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

    @Test
    @Description("Тест с курсами ЦБ")
    public void getCBRCoursesTest() throws ParseException {
        getResponseWith200Status();
        checkExpectedCurrencies();
        checkDates();

    }

    @Step("12. Проверить, что АПИ вернуло 200 ОК. 13. Проверить, что заголовок Content-Type соотвествует действительности\n")
    public void getResponseWith200Status() {
        getWith200Status(CBREndPoints.DAILY_COURSE);
    }
    @Step("14. Проверить с помощью JSON Schema, что в ответе точно содержатся объекты \"USD\" и \"EUR\"")
    public void checkExpectedCurrencies() {
        getWith200Status(CBREndPoints.DAILY_COURSE)
                .body(matchesJsonSchemaInClasspath("ResponseJSONSchema.json")) //проверяем респонс целиком с помощью json schema
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }
    @Step("15. Проверить, что \"Date\" отображается завтрашний день, \"Timestamp\" - сегодняшний.")
    public void checkDates() {
        String json = getWith200Status(CBREndPoints.DAILY_COURSE).extract().response().getBody().prettyPrint();
        BaseResponse baseResponse = gson.fromJson(json, (Type) BaseResponse.class);

        String responseDate = baseResponse.getDate().substring(0,10); //режем строку, с форматированием SimpleDateFormat с часовым поясом не оч вышло
        String responsePreviousDate = baseResponse.getPreviousDate().substring(0,10);

        Assert.assertEquals(responseDate, LocalDate.now().toString());
        Assert.assertEquals(responsePreviousDate, LocalDate.now().minusDays(1).toString());

        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss");
        //String s = responseDate.replace("Z", "+00:00");
        // Date parsedDate = dateFormat.parse(responseDate);

        Double courseUSD = baseResponse.getValute().getUSD().getValue();
        Double courseEUR = baseResponse.getValute().getEUR().getValue();
    }


//    @Step("Сравниваем курсы на странице с курсами ЦБ")
//    public void compareRates() {
//        ExchangePage exchangePage = new ExchangePage();
//        int i = 0;
//
//        while (i < exchangePage.ratesOnPage.size()) {
//
//            String stringRate = (ratesOnPage.get(i).getText()).replace(",", ".");
//            getRatesOnPage.add(Double.parseDouble(stringRate));
//            i++;
//        }
//
//    }


    @Attachment
    public String getJsonResponseBody(ValidatableResponse response) {
        return response.extract().response().getBody().prettyPrint();
    }

}
