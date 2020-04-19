package models.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;


public class ExchangePage extends Page {

    public ElementsCollection CurrencySelect = $$(By.xpath("//div[@data-qa-file='CurrencySelects']"));

    @Override
    public Page open(){
        Selenide.open("https://www.tinkoff.ru/about/exchange/");
        return this;
    }






}
