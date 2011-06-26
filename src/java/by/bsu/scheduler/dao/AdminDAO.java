package by.bsu.scheduler.dao;

import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.utils.DAOFactory;
import by.bsu.scheduler.utils.DAOUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import by.bsu.scheduler.models.Admin;

public class AdminDAO extends BaseDAO {

	public AdminDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	private static final int SALT_GEN = 100;
	private static final String SQL_CREATE =
			"	Insert Into"
			+ "		admins"
			+ "	("
			+ "		email,"
			+ "		name,"
			+ "		login,"
			+ "		password,"
			+ "		salt"
			+ "	)"
			+ "	Values"
			+ "	(?, ?, ?, MD5(?), ?)";
	private static final String SQL_GET_ALL =
			"	select"
			+ "		*"
			+ "	from"
			+ "		admins";
	private static final String SQL_GET_ALL_BY_LOGIN_UNCRYPTED_PASSWORD =
			"	select"
			+ "		*"
			+ "	from"
			+ "		admins"
			+ "	where"
			+ "		login = ? &&"
			+ "		password = MD5(CONCAT(MD5(?), salt))";
	private static final String SQL_GET_ID_BY_ID_PASSWORD =
			"	select"
			+ "		id"
			+ "	from"
			+ "		admins"
			+ "	where"
			+ "		id = ? &&"
			+ "		password = ?";
	private static final String SQL_GET_ALL_BY_ID =
			"	select"
			+ "		*"
			+ "	from"
			+ "		admins"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_UPDATE_BY_ID =
			"	update"
			+ "		admins"
			+ "	set"
			+ "		email = ?,"
			+ "		name = ?,"
			+ "		login = ?,"
			+ "		password = MD5(CONCAT(MD5(?), ?)),"
			+ "		salt = ?"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_DELETE_BY_ID =
			"	delete from"
			+ "		admins"
			+ "	where"
			+ "		id = ?";

	public ArrayList<Admin> getAll() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Admin> admins = new ArrayList<Admin>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				admins.add(mapAdmin(resultSet));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return admins;
	}

	public Admin get(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Admin admin = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				admin = mapAdmin(resultSet);
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return admin;
	}

	public void save(int id, String email, String name, String login, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, login);
			preparedStatement.setString(4, password);
			String salt = DAOUtils.salt(SALT_GEN);
			preparedStatement.setString(5, salt);
			preparedStatement.setString(6, salt);
			preparedStatement.setInt(7, id);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection);
		}
	}

	public void delete(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection);
		}
	}

	public Admin check(String login, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Admin admin = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_LOGIN_UNCRYPTED_PASSWORD);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				admin = mapAdmin(resultSet);
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return admin;
	}

	public boolean checkCookies(String adminid, String adminpass) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		boolean res = false;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ID_BY_ID_PASSWORD);
			preparedStatement.setString(1, adminid);
			preparedStatement.setString(2, adminpass);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				res = true;
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return res;
	}

	public void create(String email, String name, String login, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_CREATE);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, login);
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, DAOUtils.salt(SALT_GEN));
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}
	}

	public static Admin mapAdmin(ResultSet resultSet) throws SQLException {
		return new Admin(
				resultSet.getInt("id"),
				resultSet.getString("email"),
				resultSet.getString("name"),
				resultSet.getString("login"),
				resultSet.getString("password"),
				resultSet.getString("salt"));
	}
}
