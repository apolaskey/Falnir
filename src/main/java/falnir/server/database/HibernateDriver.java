package falnir.server.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (Static Class) Used to open and close hibernate sessions.
 * @author Andrew
 */
public class HibernateDriver {
	private static final Logger logger = LoggerFactory.getLogger(HibernateDriver.class);
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;
	
	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static SessionFactory GetSessionFactory() {
		return sessionFactory;
	}
	
	public static Session OpenSession() {
		return sessionFactory.openSession();
	}
	
	public static void Shutdown() {
		sessionFactory.close();
	}
	
	
	/**
	 * EOF
	 */
}
