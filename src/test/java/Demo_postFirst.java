import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Demo_postFirst {

    public static Map<String,String> params = new HashMap<>();

    @BeforeClass
    public void postPrepare(){
//        params.put("userName",RestUtils.getRandomUserName());
//        params.put("passWord",RestUtils.getRandomUserName());
//        params.put("hospCode","QEH");

        params.put("userName","ISSUETEST");
        params.put("passWord","P@ssw0rd");
        params.put("hospCode","QEH");

        //URI usually include domain and portal
        baseURI = "http://192.168.101.220:8081";
        basePath = "/signon";

    }
    /*
    when no prerequisite for given part , it is optional to specify given ,as follow
     given()

    If only specify with @Test tag , then the execution would follow alphabetical order i.e apple before banana
    if specify with priority parameter , then follow the ascending number order (priority default is 0)
    .and() it's only for given and then section
    verify single content
        .assertThat().body("message",equalTo("Incorrect LAN ID or password"));
       multiple content

    * */

    @Test(priority = 1)
    public void signOnFail(){
        given()
                .contentType("application/json")
                .body(params)
        .when()
                .post()

        .then()
                .statusCode(401)
                .assertThat().body("message",equalTo("Incorrect LAN ID or password"));
    }

    @Test
    public void signOnPass(){
        Response response =
        given()
                .contentType("application/json")
                .body(params)


                .when()
                .post()

                .then()
                .statusCode(200)
                .assertThat().body("isSystemAdmin",equalTo("Y"))
                .assertThat().body("filterList.rcpWardLocation",equalTo("C5"))
                .assertThat().body("filterList",hasValue(oneOf("all","testward","C5")))
                .assertThat().body("filterList",hasValue(("111")))
//        .log().body()
                .extract().response();

        Assert.assertTrue(response.getHeader("Date").startsWith(
                LocalDate.now().format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))));
    }
}
