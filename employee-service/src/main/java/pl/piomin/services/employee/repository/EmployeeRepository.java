package pl.piomin.services.employee.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.micronaut.context.annotation.Property;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.piomin.services.employee.model.Employee;

@Singleton
public class EmployeeRepository {

	private MongoClient mongoClient;

	@Property(name = "mongodb.database")
	private String mongodbDatabase;
	@Property(name = "mongodb.collection")
	private String mongodbCollection;

	EmployeeRepository(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public Employee add(Employee employee) {
		employee.setId(repository().countDocuments() + 1);
		repository().insertOne(employee);
		return employee;
	}

	public Employee findById(Long id) {
		return repository().find().first();
	}

	public List<Employee> findAll() {
		final List<Employee> employees = new ArrayList<>();
		repository()
				.find()
				.iterator()
				.forEachRemaining(employees::add);
		return employees;
	}

	public List<Employee> findByDepartment(Long departmentId) {
		final List<Employee> employees = new ArrayList<>();
		repository()
				.find(Filters.eq("departmentId", departmentId))
				.iterator()
				.forEachRemaining(employees::add);
		return employees;
	}

	public List<Employee> findByOrganization(Long organizationId) {
		final List<Employee> employees = new ArrayList<>();
		repository()
				.find(Filters.eq("organizationId", organizationId))
				.iterator()
				.forEachRemaining(employees::add);
		return employees;
	}

	private MongoCollection<Employee> repository() {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		return mongoClient.getDatabase(mongodbDatabase).withCodecRegistry(pojoCodecRegistry)
				.getCollection(mongodbCollection, Employee.class);
	}

}
