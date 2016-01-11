package pt.ob.spring.configs;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


@Configuration
@EnableMongoRepositories( basePackages = { "pt.ob.data.repositories" } )
public class SpringMongoConfiguration extends AbstractMongoConfiguration {


	@Autowired
	@Qualifier( "mongoDBHost" )
	private String mongoDBHost;

	@Autowired
	@Qualifier( "mongoDBPort" )
	private int mongoDBPort;

	@Autowired
	@Qualifier( "mongoDBName" )
	private String mongoDBName;

	@Autowired
	@Qualifier( "mongoDBUser" )
	private String mongoDBUser;

	@Autowired
	@Qualifier( "mongoDBPass" )
	private String mongoDBPass;


	@Override
	protected String getDatabaseName() {
		return this.mongoDBName;
	}


	@Override
	public Mongo mongo() throws UnknownHostException {
		ServerAddress server = new ServerAddress( this.mongoDBHost, this.mongoDBPort );

		MongoCredential credentials = MongoCredential.createCredential( this.mongoDBUser, this.mongoDBName,
				this.mongoDBPass.toCharArray() );

		List<MongoCredential> credentialsList = new ArrayList<>();
		credentialsList.add( credentials );

		MongoClient mongoClient = new MongoClient( server, credentialsList );
		return mongoClient;
	}

}
