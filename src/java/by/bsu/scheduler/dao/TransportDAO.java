package by.bsu.scheduler.dao;

import by.bsu.scheduler.models.Place;
import by.bsu.scheduler.models.Transport;
import by.bsu.scheduler.models.TransportType;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.utils.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class TransportDAO extends BaseDAO {

	public TransportDAO(DAOFactory daoFactory) {
		super(daoFactory);
	}
	private static final String SQL_CREATE =
			"	Insert Into"
			+ "		transports"
			+ "	("
			+ "		from_place_id,"
			+ "		to_place_id,"
			+ "		start,"
			+ "		end,"
			+ "		spend,"
			+ "		period,"
			+ "		image,"
			+ "		transport_type_id,"
			+ "		number"
			+ "	)"
			+ "	Values"
			+ "	(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_GET_ALL =
			"	select"
			+ "		*"
			+ "	from"
			+ "		transports"
			+ "	left join"
			+ "		transport_types as transport_type"
			+ "	on"
			+ "		transports.transport_type_id = transport_type.id"
			+ "	left join"
			+ "		places as place_from"
			+ "	on"
			+ "		transports.from_place_id = place_from.id"
			+ "	left join"
			+ "		places as place_to"
			+ "	on"
			+ "		transports.to_place_id = place_to.id";
	private static final String SQL_GET_ALL_BY_ID =
			"	select"
			+ "		*"
			+ "	from"
			+ "		transports"
			+ "	left join"
			+ "		transport_types as transport_type"
			+ "	on"
			+ "		transports.transport_type_id = transport_type.id"
			+ "	left join"
			+ "		places as place_from"
			+ "	on"
			+ "		transports.from_place_id = place_from.id"
			+ "	left join"
			+ "		places as place_to"
			+ "	on"
			+ "		transports.to_place_id = place_to.id"
			+ "	where "
			+ "		transports.id = ?";
	private static final String SQL_UPDATE_BY_ID =
			"	update"
			+ "		transports"
			+ "	set"
			+ "		from_place_id = ?,"
			+ "		to_place_id = ?,"
			+ "		start = ?,"
			+ "		end = ?,"
			+ "		spend = ?,"
			+ "		period = ?,"
			+ "		image = ?,"
			+ "		transport_type_id = ?,"
			+ "		number = ?"
			+ "	where"
			+ "		id = ?";
	private static final String SQL_DELETE_BY_ID =
			"	delete from"
			+ "		transports"
			+ "	where"
			+ "		id = ?";

	public ArrayList<Transport> getAll() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Transport> transports = new ArrayList<Transport>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				transports.add(mapTransportLinked(resultSet, "place_from", "place_to", "transport_type"));
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return transports;
	}

	public Transport get(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Transport transport = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_ID);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				transport = mapTransportLinked(resultSet, "place_from", "place_to", "transport_type");
			}
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection, resultSet);
		}

		return transport;
	}

	public void save(int id, int from_place_id, int to_place_id, Time start, Time end, Time spend, Time period, String image, int transport_type_id, int number) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID);
			preparedStatement.setInt(1, from_place_id);
			preparedStatement.setInt(2, to_place_id);
			preparedStatement.setTime(3, start);
			preparedStatement.setTime(4, end);
			preparedStatement.setTime(5, spend);
			preparedStatement.setTime(6, period);
			preparedStatement.setString(7, image);
			preparedStatement.setInt(8, transport_type_id);
			preparedStatement.setInt(9, number);
			preparedStatement.setInt(10, id);
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
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection);
		}
	}
	
	public void create(int from_place_id, int to_place_id, Time start, Time end, Time spend, Time period, String image, int transport_type_id, int number) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_CREATE);
			preparedStatement.setInt(1, from_place_id);
			preparedStatement.setInt(2, to_place_id);
			preparedStatement.setTime(3, start);
			preparedStatement.setTime(4, end);
			preparedStatement.setTime(5, spend);
			preparedStatement.setTime(6, period);
			preparedStatement.setString(7, image);
			preparedStatement.setInt(8, transport_type_id);
			preparedStatement.setInt(9, number);
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex);
		} finally {
			closeAll(preparedStatement, connection);
		}
	}

	private static Transport mapTransportLinked(ResultSet resultSet, String fromPlaceSynonym, String toPlaceSynonym, String transportTypeSynonym) throws SQLException {
		Place from_place = PlaceDAO.mapPlaceBySynonym(resultSet, fromPlaceSynonym);
		Place to_place = PlaceDAO.mapPlaceBySynonym(resultSet, toPlaceSynonym);
		TransportType transport_type = TransportTypeDAO.mapTransportTypeBySynonym(resultSet, transportTypeSynonym);
		return new Transport(
				resultSet.getInt("id"),
				resultSet.getInt("number"),
				from_place,
				to_place,
				resultSet.getTime("start"),
				resultSet.getTime("end"),
				resultSet.getTime("spend"),
				resultSet.getTime("period"),
				transport_type,
				resultSet.getString("image"));
	}
}
