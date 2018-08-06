package com.sap.refapps.espm.config;


import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * This is the cloud database configuration class which reads
 * the database properties automatically from the application
 * environment i.e VCAP_SERVICES based on the service bound
 * to the application.
 *
 */
@Configuration
@Profile("cloud")
public class CloudDataSourceConfig /*extends AbstractCloudConfig*/  {

	/**
	 * Returns the datasource based on DB service
	 * bound to the application.
	 * 
	 * @return datasource
	 */
	/*@Bean
	public DataSource cloudDataSource() {
		return connectionFactory().dataSource();
	}*/
	 @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

}
