package ua.banan.web;

public class WordCase 
{
	public String gen;
	public String nom;
	public String plu;
	
	public WordCase(String nom, String gen, String plu) {
		super();
		this.gen = gen;
		this.nom = nom;
		this.plu = plu;
	}
	public String getGen() {
		return gen;
	}
	public void setGen(String gen) {
		this.gen = gen;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPlu() {
		return plu;
	}
	public void setPlu(String plu) {
		this.plu = plu;
	}
	
	
	
}
