package pl.piomin.services.department.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.micronaut.context.annotation.Property;
import pl.piomin.services.department.model.Department;

@Singleton
public class DepartmentRepository {

	@Property(name = "mongodb.database")
	private String mongodbDatabase;
	@Property(name = "mongodb.collection")
	private String mongodbCollection;

	private MongoClient mongoClient;

	DepartmentRepository(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public Department add(Department department) {
		department.setId(repository().countDocuments() + 1);
		repository().insertOne(department);
		return department;
	}

	public Department findById(Long id) {
		return repository().find().first();
	}

	public List<Department> findAll() {
		final List<Department> departments = new ArrayList<>();
		repository()
				.find()
				.iterator()
				.forEachRemaining(departments::add);
		return departments;
	}

	public List<Department> findByOrganization(Long organizationId) {
		final List<Department> departments = new ArrayList<>();
		repository()
				.find(Filters.eq("organizationId", organizationId))
				.iterator()
				.forEachRemaining(departments::add);
		return departments;
	}

	private MongoCollection<Department> repository() {
		return mongoClient.getDatabase(mongodbDatabase).getCollection(mongodbCollection, Department.class);
	}

}
