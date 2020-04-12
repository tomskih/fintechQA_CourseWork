package apitest;

import org.junit.Test;
import endpoints.CBREndPoints;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

public class CBRTest extends TestBase {

    public CBRTest() throws IOException {
        super("cbr_api.uri");
    }

    @Test
    public void getCBRCoursesTest() {
        getWith200Status(CBREndPoints.DAILY_COURSE)
                .and()
                .body("Valute.USD.ID", equalTo("R01235"));
    }

}