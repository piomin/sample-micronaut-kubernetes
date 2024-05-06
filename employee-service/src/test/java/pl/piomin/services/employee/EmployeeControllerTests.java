package pl.piomin.services.employee;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import pl.piomin.services.employee.model.Employee;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@Property(name = "in-memory-store.enabled", value = "true")
public class EmployeeControllerTests {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void add() {
        Employee employee = Instancio.of(Employee.class)
                .ignore(Select.field(Employee::getId))
                .create();
        employee = client.toBlocking()
                .retrieve(HttpRequest.POST("/employees", employee), Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getId());
    }

    @Test
    void findAll() {
        Employee[] employees = client.toBlocking().retrieve("/employees", Employee[].class);
        assertTrue(employees.length > 0);
    }
}
