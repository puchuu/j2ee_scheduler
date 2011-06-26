package by.bsu.scheduler.utils;

import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.dao.PlaceDAO;
import by.bsu.scheduler.dao.TransportDAO;
import by.bsu.scheduler.dao.TransportTypeDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DAOFactory {

	private static final String URL = "jdbc/scheduler";

	public static DAOFactory getInstance() throws DAOConfigurationException {

		DAOFactory instance;
		DataSource dataSource = null;
		try {
			dataSource = (DataSource) new InitialContext().lookup(URL);
		} catch (NamingException e) {
			throw new DAOConfigurationException("DataSource '" + URL + "' is missing", e);
		}
		instance = new DAOFactoryDataSource(dataSource);

		return instance;
	}

	public abstract Connection getConnection() throws SQLException;

	public AdminDAO getAdminDAO() {
		return new AdminDAO(this);
	}

	public PlaceDAO getPlaceDAO() {
		return new PlaceDAO(this);
	}

	public TransportTypeDAO getTransportTypeDAO() {
		return new TransportTypeDAO(this);
	}

	public TransportDAO getTransportDAO() {
		return new TransportDAO(this);
	}
}
