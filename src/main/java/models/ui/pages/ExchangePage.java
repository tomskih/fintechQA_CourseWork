package models.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$$;


public class ExchangePage extends Page {

    public ElementsCollection currencySelect = $$(By.xpath("//div[@data-qa-file='CurrencySelects']"))
            .filterBy(Condition.cssClass("DesktopExchange__controllersCol_QPTtL"));

    public ElementsCollection operationCurrenciesAndType = $$(By.xpath("//div[@data-qa-file='TableHeader']"));

    public ElementsCollection ratesOnPage = $$(By.xpath("//div[@class='Text__text_primary_28uo7']"));

    @Override
    public Page open(){
        Selenide.open("https://www.tinkoff.ru/about/exchange/");
        return this;
    }

    public boolean areSelectedCurrenciesDisplayed (String operationCurrenciesAndTypeString, String currency) {
        Map curMap = new HashMap();
        curMap.put("Евро", "\\u20ac\"");
        curMap.put("Рубль", "\\u20bd\"");
        curMap.put("Доллар", "\\u0024\"");
        System.out.println(operationCurrenciesAndTypeString);
        System.out.println(currency);

        return operationCurrenciesAndTypeString.contains(curMap.get(currency).toString());
    }

    public void getRatesOnPage() {
        int i = 0;
        System.out.println(ratesOnPage);
        ArrayList getRatesOnPage = new ArrayList();

        while (i < ratesOnPage.size()) {
            String stringRate = (ratesOnPage.get(i).getText()).replace(",", ".");
            getRatesOnPage.add(Double.parseDouble(stringRate));
            i++;
        }
        System.out.println(getRatesOnPage);
    }








}
