package nl.rubend.ovchipkaart;

import java.util.List;

public class Product {
	private int product_nummer;
	private String naam;
	private String beschrijving;
	private double prijs;
	public Product(int product_nummer,String naam,String beschrijving, double prijs) {
		this.product_nummer=product_nummer;
		this.naam=naam;
		this.beschrijving=beschrijving;
		this.prijs=prijs;
	}

	public int getProduct_nummer() {
		return product_nummer;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	public double getPrijs() {
		return prijs;
	}

	public void setPrijs(double prijs) {
		this.prijs = prijs;
	}
}
