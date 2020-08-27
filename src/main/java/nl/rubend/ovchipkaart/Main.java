package nl.rubend.ovchipkaart;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		try {
			PreparedStatement statement=Connector.getConn().prepareStatement("select reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum from reiziger;");
			statement.execute();
			ResultSet set=statement.getResultSet();
			System.out.println("Alle reizigers:");
			while(set.next()) {
				String tussenvoegsel=set.getString(3);
				System.out.println("\t#"+set.getInt(1)+": "+set.getString(2)+". "+(tussenvoegsel==null?"":tussenvoegsel+" ")+set.getString(4)+" ("+set.getDate(5)+")");
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

	}
}
