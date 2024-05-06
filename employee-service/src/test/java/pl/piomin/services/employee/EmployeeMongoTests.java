package pl.piomin.services.employee;

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
import pl.piomin.services.employee.model.Employee;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeMongoTests {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    private static Long id;

    @Test
    @Order(1)
    void add() {
        Employee employee = Instancio.of(Employee.class)
                .set(Select.field(Employee::getDepartmentId), 1L)
                .set(Select.field(Employee::getOrganizationId), 1L)
                .ignore(Select.field(Employee::getId))
                .create();
        employee = client.toBlocking()
                .retrieve(HttpRequest.POST("/employees", employee), Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getId());
        id = employee.getId();
    }

    @Test
    @Order(2)
    void findAll() {
        Employee[] employees = client.toBlocking().retrieve("/employees", Employee[].class);
        assertTrue(employees.length > 0);
    }

    @Test
    @Order(2)
    void findById() {
        Employee employee = client.toBlocking().retrieve("/employees/" + id, Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getId());
    }

    @Test
    @Order(2)
    void findByDepartment() {
        Employee[] employees = client.toBlocking().retrieve("/employees/department/" + 1L, Employee[].class);
        assertTrue(employees.length > 0);
    }

    @Test
    @Order(2)
    void findByOrganization() {
        Employee[] employees = client.toBlocking().retrieve("/employees/organization/" + 1L, Employee[].class);
        assertTrue(employees.length > 0);
    }
}
