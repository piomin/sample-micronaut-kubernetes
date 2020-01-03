package pl.piomin.services.organization.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import pl.piomin.services.organization.model.Department;

import java.util.List;

@Client(id = "department-service", path = "/departments")
public interface DepartmentClient {

	@Get("/pl/piomin/services/organization/{organizationId}")
    List<Department> findByOrganization(Long organizationId);
	
	@Get("/pl/piomin/services/organization/{organizationId}/with-employees")
    List<Department> findByOrganizationWithEmployees(Long organizationId);
	
}
