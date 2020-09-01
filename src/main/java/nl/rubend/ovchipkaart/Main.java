package nl.rubend.ovchipkaart;

import java.sql.*;
import java.util.List;

public class Main {
	public static void main(String[] args) throws SQLException {
		testReizigerDAO(new ReizigerDAOPsql(SQLConnector.getConn()));
	}
	private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
		System.out.println("\n---------- Test ReizigerDAO -------------");

		// Haal alle reizigers op uit de database
		List<Reiziger> reizigers = rdao.findAll();
		System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
		for (Reiziger r : reizigers) {
			System.out.println(r);
		}
		System.out.println();

		// Maak een nieuwe reiziger aan en persisteer deze in de database
		String gbdatum = "1981-03-14";
		Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
		System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
		rdao.save(sietske);
		reizigers = rdao.findAll();
		System.out.println(reizigers.size() + " reizigers\n");

		System.out.println("[Test]Oude geboortedatum:"+sietske.getGeboortedatum());
		sietske.setGeboortedatum(java.sql.Date.valueOf("1991-03-14"));
		rdao.update(sietske);

		//Zou de nieuwe geboortedatum moeten geven
		System.out.println("[Test]Nieuwe geboortedatum:"+rdao.findById(77).getGeboortedatum());

		System.out.println("[Test]zoeken op geboortedatum:" + rdao.findByGebDatum("1991-03-14"));

		//En verwijderen.
		rdao.delete(sietske);
		//Nu zou hij niet meer te vinden moeten zijn?
		System.out.println("[Test]Zou leeg moeten zijn: "+rdao.findById(77));
	}
}
