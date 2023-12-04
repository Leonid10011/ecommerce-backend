import com.lbcoding.ecommerce.dto.CategoryDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CategoryServiceTest {

    CategoryDTO categoryDTO = new CategoryDTO(
            0,
            "test"
    );

    @Test
    @Transactional
    public void create(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(categoryDTO)
                .when().post("/category/")
                .then()
                .statusCode(201)
                .body(is("Category created"));

        System.out.println(categoryDTO);
    }
    @Test
    @Transactional
    public void getById(){
        categoryDTO.setCategory_id(1);

        RestAssured.given()
                .when().get("/category/test")
                .then()
                .statusCode(200);
    }
    @Test
    @Transactional
    public void getAll(){
        RestAssured.given()
                .when().get("/category/")
                .then()
                .statusCode(200);
    }
}
