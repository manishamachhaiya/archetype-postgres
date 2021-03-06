#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.test.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.gdn.common.base.entity", "${package}" })
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.gdn.common.base.entity", "${package}" })
public class TestConfig {

	@Bean
	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(this.dataSource());
		entityManagerFactory.setJpaVendorAdapter(this.jpaVendorAdapter());
		return entityManagerFactory;
	}

	@Bean
	HibernateJpaDialect jpaDialect() {
		HibernateJpaDialect jpaDialect = new HibernateJpaDialect();
		return jpaDialect;
	}

	@Bean
	HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;
	}

	@Bean
	JpaTransactionManager transactionManager() throws Exception {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(this.dataSource());
		jpaTransactionManager.setJpaDialect(this.jpaDialect());
		return jpaTransactionManager;
	}
}

}
