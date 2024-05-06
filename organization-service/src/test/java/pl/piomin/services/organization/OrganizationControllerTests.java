package pl.piomin.services.organization;

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
import pl.piomin.services.organization.model.Organization;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationControllerTests {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    private static Long id;

    @Test
    @Order(1)
    void add() {
        Organization organization = Instancio.of(Organization.class)
                .ignore(Select.field(Organization::getId))
                .create();
        organization = client.toBlocking()
                .retrieve(HttpRequest.POST("/organizations", organization), Organization.class);
        assertNotNull(organization);
        assertNotNull(organization.getId());
        id = organization.getId();
    }

    @Test
    @Order(2)
    void findAll() {
        Organization[] organizations = client.toBlocking().retrieve("/organizations", Organization[].class);
        assertTrue(organizations.length > 0);
    }

    @Test
    @Order(2)
    void findById() {
        Organization organization = client.toBlocking().retrieve("/organizations/" + id, Organization.class);
        assertNotNull(organization);
        assertNotNull(organization.getId());
    }

}
