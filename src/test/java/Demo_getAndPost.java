import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


/*
    given()
        set cookies , add auth ,add param , set headers info
    when()
        get, post , put , Demo_delete
    then()
        validate status code , extract response , extract headers cookies & response body

 */
public class Demo_getAndPost {
    public static String auth = "";
    public static Map<String,String> params = new HashMap<>();
    public static String token ="";



    @BeforeTest
    public void postPrepare(){
//        params.put("userName",RestUtils.getRandomUserName());
//        params.put("passWord",RestUtils.getRandomUserName());
//        params.put("hospCode","QEH");
        //URI usually include domain and portal
//        baseURI = "http://192.168.101.220:8081";
//        basePath = "/signon";

        params.put("userName","ISSUETEST");
        params.put("passWord","P@ssw0rd");
        params.put("hospCode","QEH");
        signOnPass();




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

//    @Test(priority = 1)
    public void signOnFail(){
        given()
                .body(params)

                .when()
                .post("http://192.168.101.220:8081/signon")

                .then()
                .statusCode(401)
                .assertThat().body("message",equalTo("Incorrect LAN ID or password"));
    }

//    @Test
    public void signOnPass(){
        Response response =
                given()
                        .contentType("application/json")
                        .body(params)


                        .when()
                        .post("http://192.168.101.220:8081/signon")

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
        token = response.jsonPath().get("token");
    }

    @Test(priority = 1)
    public void getAreas(){
        params.clear();
        params.put("Authorization","Bearer "+token);
        params.put("cc_version","v0.1 r202111221700;v0.1 r202111221700");
        given()
                .headers(params)
                .when()
                .get("http://192.168.101.220:8085/portering/getAreas")

                .then()
                .statusCode(200)
                ;
    }

//    @Test
    public void getAvailableSite(){
        given()
                .when()
                .get("http://192.168.101.213:3000/user/loginAvailableSites")

                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .assertThat().body("respCode",equalTo(0))
//                .header("Date",);
                .header("Content-Type","application/json;charset=UTF-8");

    }



    //    @Test
    public void getHospList(){
        params.put("cc_version","v0.1 r202111221700;v0.1 r202111221700");
        given()
                .headers(params)
                .when()
                .get("http://192.168.101.220:8085/getHospList")

                .then()
                .statusCode(200)
                .body("value",hasItems("HO","QEH","VH","VH2"))
                .body("value",not(hasItem("VH22")))

        ;

    }
}
