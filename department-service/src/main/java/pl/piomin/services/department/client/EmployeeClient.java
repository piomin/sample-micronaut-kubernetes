package pl.piomin.services.department.client;

import java.util.List;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import pl.piomin.services.department.model.Employee;

@Client(id = "employee", path = "/employees")
public interface EmployeeClient {

	@Get("/department/{departmentId}")
	List<Employee> findByDepartment(Long departmentId);

}
