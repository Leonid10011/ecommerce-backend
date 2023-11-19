import com.lbcoding.ecommerce.dto.CategoryDTO;
import com.lbcoding.ecommerce.model.Category;
import com.lbcoding.ecommerce.repository.CategoryRepository;
import com.lbcoding.ecommerce.service.CategoryService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.NonUniqueResultException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoryServiceTest {

    @InjectMock
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryService categoryService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategorySuccess(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");

        Response response = categoryService.create(categoryDTO);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(categoryRepository, times(1)).create(any(Category.class));
    }

    @Test
    public void testCreateCategoryAlreadyExists(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");

        doThrow(new NonUniqueResultException()).when(categoryRepository).create(any(Category.class));

        Response response = categoryService.create(categoryDTO);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }
}
