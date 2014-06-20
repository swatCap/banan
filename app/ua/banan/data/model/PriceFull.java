package ua.banan.data.model;

public class PriceFull 
{
	private String name;
	private int toursCount;
	private int id;
	public PriceFull(String name, int toursCount, int id) {
		super();
		this.name = name;
		this.toursCount = toursCount;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getToursCount() {
		return toursCount;
	}
	public void setToursCount(int toursCount) {
		this.toursCount = toursCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
