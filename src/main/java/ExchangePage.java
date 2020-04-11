import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class ExchangePage {
    public SelenideElement headerBlock = Selenide.$(By.xpath("//div[@data-block-type='headerSlim']"));
    public SelenideElement logo = headerBlock.$(By.xpath("./div/div/a[@href='/']"));
    public ElementsCollection headerLinks= headerBlock.$$(By.tagName("a")).filterBy(Condition.visible);

    public ExchangePage open(){
        Selenide.open("/");
        return this;
    }

    public ExchangePage back(){
        Selenide.back();
        return this;
    }

    public void isExistElement(SelenideElement element){
        element.shouldBe(Condition.visible);
    }

    public class CurrencySelect {
        public By CurrencySelect = By.xpath("//div[@data-qa-file='CurrencySelects']");

    }


}
