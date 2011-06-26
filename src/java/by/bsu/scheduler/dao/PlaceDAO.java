package by.bsu.scheduler.dao;

import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.utils.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import by.bsu.scheduler.models.Place;

public class PlaceDAO extends BaseDAO {

	public PlaceDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	
	private static final String SQL_CREATE =
			"	Insert Into"
			+ "		places"
			+ "	("
			+ "		name,"
			+ "		image,"
			+ "		latitude,"
			+ "		longitude"
			+ "	)"
			+ "	Values"
			+ "	(?, ?, ?, ?)";
	private static final String SQL_GET_ALL =
			"	select"
			+ "		*"
			+ "	from"
			+ "		places";
	private static final String SQL_GET_ALL_BY_ID =
			"	select"
			+ "		*"
			+ "	from"
			+ "		places"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_UPDATE_BY_ID =
			"	update"
			+ "		places"
			+ "	set"
			+ "		name = ?,"
			+ "		image = ?,"
			+ "		latitude = ?,"
			+ "		longitude = ?"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_DELETE_BY_ID =
			"	delete from"
			+ "		places"
			+ "	where"
			+ "		id = ?";

	public ArrayList<Place> getAll() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Place> places = new ArrayList<Place>();

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				places.add(mapPlace(resultSet));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return places;
	}

	public Place get(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Place place = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				place = mapPlace(resultSet);
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return place;
	}

	public void save(int id, String name, Double latitude, Double longitude, String image) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, image);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setInt(5, id);
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

	public void create(String name, Double latitude, Double longitude, String image) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_CREATE);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, image);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}
	}

	public static Place mapPlace(ResultSet resultSet) throws SQLException {
		return new Place(
				resultSet.getInt("id"),
				resultSet.getString("name"),
				resultSet.getDouble("latitude"),
				resultSet.getDouble("longitude"),
				resultSet.getString("image"));
	}

	public static Place mapPlaceBySynonym(ResultSet resultSet, String synonym) throws SQLException {
		return new Place(
				resultSet.getInt(synonym + ".id"),
				resultSet.getString(synonym + ".name"),
				resultSet.getDouble(synonym + ".latitude"),
				resultSet.getDouble(synonym + ".longitude"),
				resultSet.getString(synonym + ".image"));
	}
}
