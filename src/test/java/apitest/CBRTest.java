package apitest;

import com.google.gson.Gson;
import endpoints.CBREndPoints;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.api.BaseResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.LocalDate;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;

public class CBRTest extends TestBase {
    ThreadLocal<ValidatableResponse> response = new ThreadLocal<>();
    Gson gson = new Gson();

    public CBRTest() throws IOException {
        super("cbr_api.uri");
    }

    @Test
    @Description("���� � ������� ��")
    public void getCBRCoursesTest() throws ParseException {
        getResponseWith200Status();
        checkExpectedCurrencies();
        checkDates();

    }

    @Step("12. ���������, ��� ��� ������� 200 ��. 13. ���������, ��� ��������� Content-Type ������������ ����������������\n")
    public void getResponseWith200Status() {
        getWith200Status(CBREndPoints.DAILY_COURSE);
    }
    @Step("14. ��������� � ������� JSON Schema, ��� � ������ ����� ���������� ������� \"USD\" � \"EUR\"")
    public void checkExpectedCurrencies() {
        getWith200Status(CBREndPoints.DAILY_COURSE)
                .body(matchesJsonSchemaInClasspath("ResponseJSONSchema.json")) //��������� ������� ������� � ������� json schema
                .body("Valute.USD.ID", equalTo("R01235"))
                .body("Valute.EUR.ID", equalTo("R01239"));
    }
    @Step("15. ���������, ��� \"Date\" ������������ ���������� ����, \"Timestamp\" - �����������.")
    public void checkDates() {
        String json = getWith200Status(CBREndPoints.DAILY_COURSE).extract().response().getBody().prettyPrint();
        BaseResponse baseResponse = gson.fromJson(json, (Type) BaseResponse.class);

        String responseDate = baseResponse.getDate().substring(0,10); //����� ������, � ��������������� SimpleDateFormat � ������� ������ �� �� �����
        String responsePreviousDate = baseResponse.getPreviousDate().substring(0,10); //todo ���������� �� ����

        Assert.assertEquals(responseDate, LocalDate.now().toString());
        Assert.assertEquals(responsePreviousDate, LocalDate.now().minusDays(1).toString());

        //16. ��������� ����� ���� � ������� (������� � �������)
        Double courseUSD = baseResponse.getValute().getUSD().getValue();
        Double courseEUR = baseResponse.getValute().getEUR().getValue();

        System.out.println("CBR courses" + courseUSD + ' ' + courseEUR);

        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss");
        //String s = responseDate.replace("Z", "+00:00");
        // Date parsedDate = dateFormat.parse(responseDate);
    }

//    @Step("���������� ����� �� �������� � ������� ��")
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