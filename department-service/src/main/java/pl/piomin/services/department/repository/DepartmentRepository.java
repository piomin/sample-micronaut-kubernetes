package pl.piomin.services.department.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import pl.piomin.services.department.model.Department;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class DepartmentRepository {

	@Inject
	MongoClient mongoClient;

	public Department add(Department department) {
		department.setId(repository().countDocuments() + 1);
		repository().insertOne(department);
		return department;
	}
	
	public Department findById(Long id) {
		return repository().find().first();
	}
	
	public List<Department> findAll() {
		List<Department> departments = new ArrayList<>();
		repository().find().iterator().forEachRemaining(department -> departments.add(department));
		return departments;
	}
	
	public List<Department> findByOrganization(Long organizationId) {
		List<Department> departments = new ArrayList<>();
		repository().find().iterator().forEachRemaining(department -> departments.add(department));
		return departments.stream().filter(a -> a.getOrganizationId().equals(organizationId)).collect(Collectors.toList());
	}

	private MongoCollection<Department> repository() {
		return mongoClient.getDatabase("admin").getCollection("department", Department.class);
	}

}
