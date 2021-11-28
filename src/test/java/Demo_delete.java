import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Demo_delete {

    @Test
    public void delete(){

//
     when()
             .delete()
             .then().statusCode(200);
    }
}
