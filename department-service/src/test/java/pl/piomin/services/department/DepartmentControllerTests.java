package pl.piomin.services.department;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pl.piomin.services.department.model.Department;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentControllerTests {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    private static Long id;

    @Test
    @Order(1)
    void add() {
        Department department = Instancio.of(Department.class)
                .set(Select.field(Department::getOrganizationId), 1L)
                .ignore(Select.field(Department::getId))
                .create();
        department = client.toBlocking()
                .retrieve(HttpRequest.POST("/departments", department), Department.class);
        assertNotNull(department);
        assertNotNull(department.getId());
        id = department.getId();
    }

    @Test
    @Order(2)
    void findAll() {
        Department[] departments = client.toBlocking().retrieve("/departments", Department[].class);
        assertTrue(departments.length > 0);
    }

    @Test
    @Order(3)
    void findById() {
        Department department = client.toBlocking().retrieve("/departments/" + id, Department.class);
        assertNotNull(department);
        assertNotNull(department.getId());
    }

    @Test
    @Order(4)
    void findByOrganization() {
        Department[] departments = client.toBlocking().retrieve("/departments/organization/" + 1L, Department[].class);
        assertTrue(departments.length > 0);
    }
}
