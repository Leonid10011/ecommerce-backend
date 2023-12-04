import com.lbcoding.ecommerce.dto.SizeDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SizeControllerTest {


    @Test
    @Transactional
    public void create(){
        SizeDTO sizeDTO = new SizeDTO(
                0,
                "test"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(sizeDTO)
                .when().post("/sizes/")
                .then()
                .statusCode(201);
    }

    @Test
    @Transactional
    public  void getByID(){
        SizeDTO sizeDTO = new SizeDTO(
                0,
                "testID"
        );

        Response res = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(sizeDTO)
                .post("/sizes/");

        SizeDTO resSize = res.getBody().as(SizeDTO.class);

        RestAssured.given()
                .when().get("/sizes/" + resSize.getSize_id())
                .then()
                .statusCode(200);
    }

    @Test
    @Transactional
    public  void getByNAME(){
        SizeDTO sizeDTO = new SizeDTO(
                0,
                "testName"
        );

        Response res = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(sizeDTO)
                .post("/sizes/");

        SizeDTO resSize = res.getBody().as(SizeDTO.class);

        System.out.println("resSisze By ID: " + resSize.getSize_id() + " name: " + resSize.getName());

        RestAssured.given()
                .when().get("/sizes/byName/testName")
                .then()
                .statusCode(200);
    }

    @Test
    @Transactional
    public  void update(){
        SizeDTO updateDTO = new SizeDTO(
                0,
                "test2"
        );

        Response res = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .post("/sizes/");

        res.then().statusCode(201);

        SizeDTO responsBody = res.getBody().as(SizeDTO.class);

        updateDTO.setSize_id(responsBody.getSize_id());
        updateDTO.setName("test3");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDTO)
                .when().put("/sizes/")
                .then()
                .statusCode(200);
    }
}
