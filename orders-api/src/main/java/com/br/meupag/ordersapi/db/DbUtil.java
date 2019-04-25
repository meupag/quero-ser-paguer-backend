package com.br.meupag.ordersapi.db;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.domain.OrderItem;
import com.br.meupag.ordersapi.domain.Product;
import com.br.meupag.ordersapi.domain.abs.Domain;

public class DbUtil {

	private static SessionFactory sessionFactory;
	private static final Logger logger = Logger.getLogger(DbUtil.class);
	
	static {
		Configuration configuration = new Configuration();
		
		String jdbcUrl = String.format(
				"jdbc:mysql://%s:%s/%s",
				System.getenv("RDS_ENDPOINT"),
				System.getenv("RDS_PORT"),
				System.getenv("RDS_DB"));
		
		configuration
			.addAnnotatedClass(Order.class)
			.addAnnotatedClass(Client.class)
			.addAnnotatedClass(OrderItem.class)
			.addAnnotatedClass(Product.class)
			.addAnnotatedClass(Domain.class)
			.setProperty("hibernate.connection.url", jdbcUrl)
			.setProperty("hibernate.connection.username", System.getenv("RDS_USER"))
			.setProperty("hibernate.connection.password", System.getenv("RDS_PWD"))
			.setProperty("hibernate.cache.use_second_level_cache", "true")
			.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory")
			.configure();
		
		ServiceRegistry serviceRegistry = 
				new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		try {
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);			
		} catch(HibernateException e) {
			logger.error("Initial SessionFactory creation failed: " + e);
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
