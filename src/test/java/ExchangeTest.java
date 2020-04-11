import org.junit.BeforeClass;
import org.junit.Test;

public class ExchangeTest {
    ExchangePage exchangePage = new ExchangePage();

    @BeforeClass
    public void openPage(){
        exchangePage.open();
    }


    @Test
    public void isExistsPageElements(){
        exchangePage.isExistElement(exchangePage.headerBlock);
    }

}
