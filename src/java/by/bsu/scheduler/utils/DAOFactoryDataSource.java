package by.bsu.scheduler.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DAOFactoryDataSource extends DAOFactory {

	private DataSource dataSource;

	DAOFactoryDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
