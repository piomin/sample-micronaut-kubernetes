package pl.piomin.services.organization.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import io.micronaut.context.annotation.Property;
import pl.piomin.services.organization.model.Organization;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrganizationRepository {

	@Property(name = "mongodb.database")
	private String mongodbDatabase;
	@Property(name = "mongodb.collection")
	private String mongodbCollection;

	private MongoClient mongoClient;

	OrganizationRepository(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public Organization add(Organization organization) {
		organization.setId(repository().countDocuments() + 1);
		repository().insertOne(organization);
		return organization;
	}
	
	public Organization findById(Long id) {
		return repository().find().first();
	}
	
	public List<Organization> findAll() {
		final List<Organization> organizations = new ArrayList<>();
		repository()
				.find()
				.iterator()
				.forEachRemaining(organizations::add);
		return organizations;
	}

	private MongoCollection<Organization> repository() {
		return mongoClient.getDatabase(mongodbDatabase).getCollection(mongodbCollection, Organization.class);
	}

}
