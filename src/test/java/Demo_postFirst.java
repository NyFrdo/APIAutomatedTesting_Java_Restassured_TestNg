import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
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

//    @Test
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
        given()
                .contentType("application/json")
                .body(params)

                .when()
                .post()

                .then()
                .statusCode(200)
                .assertThat().body("isSystemAdmin",equalTo("Y"))
                .assertThat().body("rcpWardLocation",equalTo("C5"));
    }
}
