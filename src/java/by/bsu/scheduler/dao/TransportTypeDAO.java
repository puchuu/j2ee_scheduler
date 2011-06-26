package by.bsu.scheduler.dao;

import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.utils.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import by.bsu.scheduler.models.TransportType;

public class TransportTypeDAO extends BaseDAO {

	public TransportTypeDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	private static final String SQL_CREATE =
			"	Insert Into"
			+ "		transport_types"
			+ "	("
			+ "		name,"
			+ "		image,"
			+ "		speed"
			+ "	)"
			+ "	Values"
			+ "	(?, ?, ?)";
	private static final String SQL_GET_ALL =
			"	select"
			+ "		*"
			+ "	from"
			+ "		transport_types";
	private static final String SQL_GET_ALL_BY_ID =
			"	select"
			+ "		*"
			+ "	from"
			+ "		transport_types"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_UPDATE_BY_ID =
			"	update"
			+ "		transport_types"
			+ "	set"
			+ "		name = ?,"
			+ "		image = ?,"
			+ "		speed = ?"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_DELETE_BY_ID =
			"	delete from"
			+ "		transport_types"
			+ "	where"
			+ "		id = ?";

	public ArrayList<TransportType> getAll() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<TransportType> transport_types = new ArrayList<TransportType>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				transport_types.add(mapTransportType(resultSet));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return transport_types;
	}

	public TransportType get(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		TransportType transport_type = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				transport_type = mapTransportType(resultSet);
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return transport_type;
	}

	public void save(int id, String name, String image, int speed) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, image);
			preparedStatement.setInt(3, speed);
			preparedStatement.setInt(4, id);
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

	public void create(String name, String image, int speed) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_CREATE);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, image);
			preparedStatement.setInt(3, speed);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}
	}

	public static TransportType mapTransportType(ResultSet resultSet) throws SQLException {
		return new TransportType(
				resultSet.getInt("id"),
				resultSet.getString("name"),
				resultSet.getString("image"),
				resultSet.getInt("speed"));
	}

	public static TransportType mapTransportTypeBySynonym(ResultSet resultSet, String synonym) throws SQLException {
		return new TransportType(
				resultSet.getInt(synonym + ".id"),
				resultSet.getString(synonym + ".name"),
				resultSet.getString(synonym + ".image"),
				resultSet.getInt(synonym + ".speed"));
	}
}
