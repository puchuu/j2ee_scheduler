package by.bsu.scheduler.dao;

import by.bsu.scheduler.utils.DAOFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDAO {

	protected DAOFactory daoFactory;

	public BaseDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public static void closeAll(Statement statement, Connection connection) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception ex) {
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	public static void closeAll(Statement statement, Connection connection, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception ex) {
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception ex) {
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception ex) {
				}
			}
		}
	}
}
