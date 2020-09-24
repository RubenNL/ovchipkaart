package nl.rubend.ovchipkaart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
	private Connection conn;
	public ProductDAOPsql(Connection conn) {
		this.conn=conn;
	};
	private Product setToProduct(ResultSet set) throws SQLException {
		return new Product(set.getInt("product_nummer"),set.getString("naam"),set.getString("beschrijving"),set.getDouble("prijs"));
	}
	@Override
	public boolean save(Product product) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("INSERT INTO product(product_nummer,naam,beschrijving,prijs) VALUES (?,?,?,?);");
			statement.setInt(1, product.getProduct_nummer());
			statement.setString(2, product.getNaam());
			statement.setString(3, product.getBeschrijving());
			statement.setDouble(4, product.getPrijs());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(Product product) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("UPDATE product SET naam=?,beschrijving=?,prijs=? WHERE product_nummer=?;");
			statement.setString(1, product.getNaam());
			statement.setString(2, product.getBeschrijving());
			statement.setDouble(3, product.getPrijs());
			statement.setInt(4, product.getProduct_nummer());
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Product product) {
		try {
			PreparedStatement statement = SQLConnector.getConn().prepareStatement("DELETE FROM product WHERE product_nummer=?;");
			statement.setInt(1, product.getProduct_nummer());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
		List<Product> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM product INNER JOIN ov_chipkaart_product ocp on product.product_nummer = ocp.product_nummer WHERE ocp.kaart_nummer=?");
		statement.setInt(1,ovChipkaart.getKaart_nummer());
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToProduct(set));
		return response;
	}

	@Override
	public List<Product> findAll() throws SQLException {
		List<Product> response=new ArrayList<>();
		PreparedStatement statement= SQLConnector.getConn().prepareStatement("SELECT * FROM product;");
		statement.execute();
		ResultSet set=statement.getResultSet();
		while(set.next()) response.add(setToProduct(set));
		return response;
	}
}
