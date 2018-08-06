package com.sap.refapps.espm.config;


import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
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
	
	
	Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public DataSource postgresDataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        log.info("Initializing PostgreSQL database: {}", databaseUrl);

        URI dbUri;
        try {
            dbUri = new URI(databaseUrl);
        }
        catch (URISyntaxException e) {
            log.error(String.format("Invalid DATABASE_URL: %s", databaseUrl), e);
            return null;
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' 
                       + dbUri.getPort() + dbUri.getPath();

        org.apache.tomcat.jdbc.pool.DataSource dataSource 
            = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnReturn(true);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

}
