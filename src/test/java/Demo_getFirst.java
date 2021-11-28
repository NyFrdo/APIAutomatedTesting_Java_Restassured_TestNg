import org.testng.annotations.Test;

import java.util.HashMap;
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
public class Demo_getFirst {
    public static String auth = "";
    public static Map<String,String> params = new HashMap<>();

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

    @Test
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

//    @Test
    public void getAreas(){
        given()
                .when()
                .get("http://192.168.101.220:8085/portering/getAreas")

                .then();
    }
}
