import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.open;

public class ExchangeTest {
    ExchangePage exchangePage = new ExchangePage();

    @BeforeClass
    public static void setUp(){
        Configuration.baseUrl = "https://www.tinkoff.ru/about/exchange";
        ExchangePage exchangePage = new ExchangePage();
        exchangePage.open();
    }


    @Test //Проверить, что загрузился хэдер со всеми основными элементами. Здесь же проверить доступность всех ссылок
    public void isExistsPageElements() {
        int i = 0;
        exchangePage.isExistElement(exchangePage.headerBlock);
        exchangePage.isExistElement(exchangePage.logo);

        while (i < exchangePage.headerLinks.size()) {   //просто прокликиваем по всем видимым ссылкам и проверяем наличие хедера
            exchangePage.headerLinks.get(i).click();
            exchangePage.headerBlock.shouldBe(Condition.exist);
            exchangePage.open();
            i++;
        }

        //нужно ещё прокликать скрытые

    }

}
