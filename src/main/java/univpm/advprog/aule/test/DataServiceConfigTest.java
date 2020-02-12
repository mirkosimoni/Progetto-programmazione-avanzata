package univpm.advprog.aule.test;


import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import univpm.advprog.aule.app.DataServiceConfig;
import univpm.advprog.aule.app.WebConfig;
import univpm.advprog.aule.app.WebInit;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
//@ComponentScan(basePackages =
//     "it.univpm.advprog.singers")
//@ComponentScan(basePackages = { "it.univpm.advprog.singers.controller", "it.univpm.advprog.singers.model",
//		"it.univpm.advprog.singers.services" })
//@ComponentScan(basePackages = { "it.univpm.advprog.singers" },
//excludeFilters  = {@ComponentScan.Filter(
//		type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, WebInit.class, DataServiceConfig.class})})
@ComponentScan(basePackages = { "it.univpm.advprog.singers.model" })
@EnableTransactionManagement
public class DataServiceConfigTest extends DataServiceConfig {

	@Override
	protected Properties hibernateProperties() {
		Properties hibernateProp = super.hibernateProperties();
		hibernateProp.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		return hibernateProp;
	}
}
