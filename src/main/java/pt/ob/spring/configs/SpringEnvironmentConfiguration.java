package pt.ob.spring.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringEnvironmentConfiguration {
	
	
	@Bean( name = "mongoDBHost" )
	public String getMongoDBAddress() {
		return System.getenv( "MONGO_DB_HOST" );
	}
	
	
	@Bean( name = "mongoDBPort" )
	public int getMongoDBPort() {
		return Integer.valueOf( System.getenv( "MONGO_DB_PORT" ) );
	}
	
	
	@Bean( name = "mongoDBName" )
	public String getMongoDBName() {
		return System.getenv( "MONGO_DB_NAME" );
	}
	
	
	
	@Bean( name = "mongoDBUser" )
	public String getMongoDBUser() {
		return System.getenv( "MONGO_DB_USER" );
	}
	
	
	@Bean( name = "mongoDBPass" )
	public String getMongoDBPass() {
		return System.getenv( "MONGO_DB_PASS" );
	}

}
