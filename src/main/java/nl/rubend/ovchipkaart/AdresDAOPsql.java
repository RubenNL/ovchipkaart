package nl.rubend.ovchipkaart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
	private Connection conn;
	//private ReizigerDAO rdao;
	public AdresDAOPsql(Connection conn) {
		this.conn=conn;
		//rdao=new ReizigerDAOPsql(conn);
	};
	private Adres setToAdres(ResultSet set) throws SQLException {
		return new Adres(set.getInt("adres_id"), set.getString("postcode"), set.getString("huisnummer"), set.getString("straat"), set.getString("woonplaats"),set.getInt("reiziger_id"));
	}
	@Override
	public boolean save(Adres adres) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?);");
			statement.setInt(1, adres.getId());
			statement.setString(2, adres.getPostcode());
			statement.setString(3, adres.getHuisnummer());
			statement.setString(4, adres.getStraat());
			statement.setString(5,adres.getWoonplaats());
			statement.setInt(6,adres.getReiziger_id());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Adres adres) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("UPDATE adres SET postcode=?,huisnummer=?,straat=?,woonplaats=?,reiziger_id=? WHERE adres_id=?;");
			statement.setString(1, adres.getPostcode());
			statement.setString(2, adres.getHuisnummer());
			statement.setString(3, adres.getStraat());
			statement.setString(4, adres.getWoonplaats());
			statement.setInt(5, adres.getReiziger_id());
			statement.setInt(6, adres.getId());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Adres adres) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("DELETE FROM adres WHERE adres_id=?;");
			statement.setInt(1, adres.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public Adres findByReiziger(Reiziger reiziger) throws SQLException {
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM adres where reiziger_id=?;");
		statement.setInt(1,reiziger.getId());
		statement.execute();
		ResultSet set=statement.getResultSet();
		if(!set.next()) return null;
		return setToAdres(set);
	}

	@Override
	public List<Adres> findAll() throws SQLException {
		List<Adres> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM adres;");
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToAdres(set));
		return response;
	}
}
