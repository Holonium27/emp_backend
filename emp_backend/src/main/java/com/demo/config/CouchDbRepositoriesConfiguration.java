package com.demo.config;


import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchDbRepositoriesConfiguration {


@Bean
public CouchDbConnector getCouchDbConnector() {
	org.ektorp.http.HttpClient httpClient = new StdHttpClient.Builder()
            .host("localhost")
            .port(5984)
            .username("tanmay")
            .password("couch@tanmaybholane")
            .build();
	
CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
//- - - - - - - - Creating database - - - - - - - - - - - - - - //
CouchDbConnector db = new StdCouchDbConnector("employeedb", dbInstance);

return db;
};
}