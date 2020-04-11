import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class ExchangePage {
    public SelenideElement headerBlock = (SelenideElement) By.xpath("//div[@data-block-type='headerSlim']");

    public ExchangePage open(){
        Selenide.open("/");
        return this;
    }
    public void isExistElement(SelenideElement element){
        element.shouldBe(Condition.visible);
    }

    public class CurrencySelect {
        public By CurrencySelect = By.xpath("//div[@data-qa-file='CurrencySelects']");

    }


}
