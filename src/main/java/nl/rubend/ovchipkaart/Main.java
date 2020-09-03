package nl.rubend.ovchipkaart;

import java.sql.*;
import java.util.List;

public class Main {
	public static void main(String[] args) throws SQLException {
		ReizigerDAO rdao=new ReizigerDAOPsql(SQLConnector.getConn());
		testReizigerDAO(rdao);
		testAdresDAO(new AdresDAOPsql(SQLConnector.getConn()),rdao);
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
	private static void testAdresDAO(AdresDAO adao,ReizigerDAO rdao) throws SQLException {
		//ReizigerDAO toegevoegd om de zoeken op reiziger te testen, wat hieronder valt.
		System.out.println("\n---------- Test ReizigerDAO -------------");
		//sietske toevoegen van vorige opdracht:
		rdao.save(new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf("1981-03-14")));
		// Haal alle reizigers op uit de database
		List<Adres> adressen = adao.findAll();
		System.out.println("[Test] AdresDAO.findAll() geeft de volgende adresssen:");
		for (Adres a : adressen) System.out.println(a);
		System.out.println();

		// Maak een nieuwe reiziger aan en persisteer deze in de database
		Adres sietskeAdres = new Adres(99, "1234AB", "12", "huisstraat","plaat",77);
		System.out.print("[Test] Eerst " + adressen.size() + " adressen, na ReizigerDAO.save() ");
		adao.save(sietskeAdres);
		adressen = adao.findAll();
		System.out.println(adressen.size() + " reizigers\n");

		System.out.println("[Test]Oud huisnummer:"+sietskeAdres.getHuisnummer());
		sietskeAdres.setHuisnummer("34A");
		adao.update(sietskeAdres);

		//Zou het nieuwe adres moeten geven
		System.out.println("[Test]Nieuw huisnummer:"+adao.findByReiziger(rdao.findById(77)).getHuisnummer());

		//En verwijderen.
		adao.delete(sietskeAdres);
		//Nu zou hij niet meer te vinden moeten zijn?
		System.out.println("[Test]Zou leeg moeten zijn: "+adao.findByReiziger(rdao.findById(77)));
	}
}
