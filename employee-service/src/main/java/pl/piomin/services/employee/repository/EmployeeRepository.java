package pl.piomin.services.employee.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.BsonInt64;
import pl.piomin.services.employee.model.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class EmployeeRepository {

	private List<Employee> employees = new ArrayList<>();

	@Inject
	MongoClient mongoClient;

	public Employee add(Employee employee) {
		employee.setId(repository().countDocuments() + 1);
		repository().insertOne(employee);
		return employee;
	}
	
	public Employee findById(Long id) {
		return repository().find().first();
	}
	
	public List<Employee> findAll() {
		return employees;
	}
	
	public List<Employee> findByDepartment(Long departmentId) {
		List<Employee> employees = new ArrayList<>();
		repository().find().iterator().forEachRemaining(employee -> employees.add(employee));
		return employees.stream().filter(a -> a.getDepartmentId().equals(departmentId)).collect(Collectors.toList());
	}
	
	public List<Employee> findByOrganization(Long organizationId) {
		List<Employee> employees = new ArrayList<>();
		repository().find().iterator().forEachRemaining(employee -> employees.add(employee));
		return employees.stream().filter(a -> a.getOrganizationId().equals(organizationId)).collect(Collectors.toList());
	}

	private MongoCollection<Employee> repository() {
		return mongoClient.getDatabase("admin").getCollection("employee", Employee.class);
	}

}
