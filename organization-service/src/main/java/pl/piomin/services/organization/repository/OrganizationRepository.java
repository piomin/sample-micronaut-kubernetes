package pl.piomin.services.organization.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import pl.piomin.services.organization.model.Organization;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrganizationRepository {

	@Inject
	MongoClient mongoClient;

	public Organization add(Organization organization) {
		organization.setId(repository().countDocuments() + 1);
		repository().insertOne(organization);
		return organization;
	}
	
	public Organization findById(Long id) {
		return repository().find().first();
	}
	
	public List<Organization> findAll() {
		List<Organization> organizations = new ArrayList<>();
		repository().find().iterator().forEachRemaining(organization -> organizations.add(organization));
		return organizations;
	}

	private MongoCollection<Organization> repository() {
		return mongoClient.getDatabase("admin").getCollection("organization", Organization.class);
	}

}
