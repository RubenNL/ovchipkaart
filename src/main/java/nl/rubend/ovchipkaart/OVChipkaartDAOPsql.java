package nl.rubend.ovchipkaart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
	private Connection conn;
	private ProductDAO pdao;
	public OVChipkaartDAOPsql(Connection conn,ProductDAO pdao) {
		this.conn=conn;
		this.pdao=pdao;
	};
	private OVChipkaart setToOVChipkaart(ResultSet set) throws SQLException {
		OVChipkaart ovchipkaart=new OVChipkaart(set.getInt("kaart_nummer"), set.getDate("geldig_tot"), set.getInt("klasse"), set.getDouble("saldo"), set.getInt("reiziger_id"));
		ovchipkaart.setProducten(pdao.findByOVChipkaart(ovchipkaart));
		return ovchipkaart;
	}
	@Override
	public List<OVChipkaart> findByReizigerId(int reiziger_id) throws SQLException {
		List<OVChipkaart> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM adres WHERE reiziger_id=?;");
		statement.setInt(1,reiziger_id);
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToOVChipkaart(set));
		return response;
	}
	@Override
	public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
		return findByReizigerId(reiziger.getId());
	}

	@Override
	public boolean save(OVChipkaart ovChipkaart) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("INSERT INTO ov_chipkaart(kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES (?,?,?,?,?);");
			statement.setInt(1, ovChipkaart.getKaart_nummer());
			statement.setDate(2, (Date) ovChipkaart.getGeldig_tot());
			statement.setInt(3, ovChipkaart.getKlasse());
			statement.setDouble(4, ovChipkaart.getSaldo());
			statement.setInt(5,ovChipkaart.getReiziger_id());
			statement.executeUpdate();
			return update(ovChipkaart);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(OVChipkaart ovChipkaart) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("UPDATE ov_chipkaart SET geldig_tot=?,klasse=?,saldo=?,reiziger_id=? WHERE kaart_nummer=?;");
			statement.setDate(1, (Date) ovChipkaart.getGeldig_tot());
			statement.setInt(2, ovChipkaart.getKlasse());
			statement.setDouble(3, ovChipkaart.getSaldo());
			statement.setInt(4, ovChipkaart.getReiziger_id());
			statement.setInt(5, ovChipkaart.getKaart_nummer());
			statement.executeUpdate();
			statement = SQLConnector.getConn().prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer=?;");
			statement.setInt(1, ovChipkaart.getKaart_nummer());
			statement.executeUpdate();
			statement = SQLConnector.getConn().prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES(?,?)");
			statement.setInt(1,ovChipkaart.getKaart_nummer());
			for(Product product:ovChipkaart.getProducten()) {
				statement.setInt(2, product.getProduct_nummer());
				statement.executeUpdate();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(OVChipkaart ovChipkaart) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?;");
			statement.setInt(1, ovChipkaart.getKaart_nummer());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public List<OVChipkaart> findAll() throws SQLException {
		List<OVChipkaart> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM ov_chipkaart;");
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToOVChipkaart(set));
		return response;
	}
}
