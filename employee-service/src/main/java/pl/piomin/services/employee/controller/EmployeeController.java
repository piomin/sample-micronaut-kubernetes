package pl.piomin.services.employee.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.piomin.services.employee.model.Employee;
import pl.piomin.services.employee.repository.EmployeeRepository;

import java.util.List;

@Controller("/employees")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@Post
	public Employee add(@Body Employee employee) {
		LOGGER.info("Employee add: {}", employee);
		return repository.add(employee);
	}

	@Get("/{id}")
	public Employee findById(Long id) {
		LOGGER.info("Employee find: id={}", id);
		return repository.findById(id);
	}

	@Get
	public List<Employee> findAll() {
		LOGGER.info("Find all");
		return repository.findAll();
	}

	@Get("/department/{departmentId}")
	public List<Employee> findByDepartment(Long departmentId) {
		LOGGER.info("Find: departmentId={}", departmentId);
		return repository.findByDepartment(departmentId);
	}

	@Get("/organization/{organizationId}")
	public List<Employee> findByOrganization(Long organizationId) {
		LOGGER.info("Find: organizationId={}", organizationId);
		return repository.findByOrganization(organizationId);
	}

}
