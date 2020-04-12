import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;


public class ExchangePage extends Page{

    @Override
    public Page open(){
        Selenide.open("https://www.tinkoff.ru/about/exchange/");
        return this;
    }


    public class CurrencySelect {
        public By CurrencySelect = By.xpath("//div[@data-qa-file='CurrencySelects']");

    }


}
