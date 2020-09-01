package nl.rubend.ovchipkaart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
	private Connection conn;
	public ReizigerDAOPsql(Connection conn) {this.conn=conn;};
	private Reiziger setToReiziger(ResultSet set) throws SQLException {
		return new Reiziger(set.getInt("reiziger_id"), set.getString("voorletters"), set.getString("tussenvoegsel"), set.getString("achternaam"), set.getDate("geboortedatum"));
	}

	@Override
	public List<Reiziger> findAll() throws SQLException {
		List<Reiziger> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM reiziger;");
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToReiziger(set));
		return response;
	}

	@Override
	public boolean save(Reiziger reiziger) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("INSERT INTO reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?);");
			statement.setInt(1, reiziger.getId());
			statement.setString(2, reiziger.getVoorletters());
			statement.setString(3, reiziger.getTussenvoegsel());
			statement.setString(4, reiziger.getAchternaam());
			statement.setDate(5, reiziger.getGeboortedatum());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Reiziger reiziger) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("UPDATE reiziger SET voorletters=?,tussenvoegsel=?,achternaam=?,geboortedatum=? WHERE reiziger_id=?;");
			statement.setString(1, reiziger.getVoorletters());
			statement.setString(2, reiziger.getTussenvoegsel());
			statement.setString(3, reiziger.getAchternaam());
			statement.setDate(4, reiziger.getGeboortedatum());
			statement.setInt(5, reiziger.getId());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Reiziger reiziger) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?;");
			statement.setInt(1, reiziger.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Reiziger findById(int id) throws SQLException {
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM reiziger where reiziger_id=?;");
		statement.setInt(1,id);
		statement.execute();
		ResultSet set=statement.getResultSet();
		if(!set.next()) return null;
		return setToReiziger(set);
	}

	@Override
	public List<Reiziger> findByGebDatum(String datum) throws SQLException {
		List<Reiziger> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM reiziger where geboortedatum=?;");
		statement.setDate(1,java.sql.Date.valueOf(datum));
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToReiziger(set));
		return response;
	}
}
