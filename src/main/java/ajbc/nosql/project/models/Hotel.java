package ajbc.nosql.project.models;

import java.util.List;

import org.bson.types.ObjectId;

public class Hotel {
	private ObjectId id;
	private String name;
	private String street;
	private int number;
	private String city;
	private String country;
	private int rank;
	private List<Room> rooms;
	private float priceFerNight;
	private List<ObjectId> orders;
	private float totalIncome;

	public Hotel(ObjectId id, String name, String street, int number, String city, String country, int rank,
			List<Room> rooms, float priceFerNight, List<ObjectId> orders) {
		super();
		this.id = id;
		this.name = name;
		this.street = street;
		this.number = number;
		this.city = city;
		this.country = country;
		this.rank = rank;
		this.rooms = rooms;
		this.priceFerNight = priceFerNight;
		this.orders = orders;
		totalIncome = 0f;
	}

	public Hotel(String name, String street, int number, String city, String country, int rank, List<Room> rooms,
			float priceFerNight, List<ObjectId> orders) {
		super();
		this.name = name;
		this.street = street;
		this.number = number;
		this.city = city;
		this.country = country;
		this.rank = rank;
		this.rooms = rooms;
		this.priceFerNight = priceFerNight;
		this.orders = orders;
		totalIncome = 0f;
	}

	public Hotel() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public float getPriceFerNight() {
		return priceFerNight;
	}

	public void setPriceFerNight(float priceFerNight) {
		this.priceFerNight = priceFerNight;
	}

	public List<ObjectId> getOrders() {
		return orders;
	}

	public void setOrders(List<ObjectId> orders) {
		this.orders = orders;
	}

	public float getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(float totalIncome) {
		this.totalIncome = totalIncome;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", street=" + street + ", number=" + number + ", city=" + city
				+ ", country=" + country + ", rank=" + rank + ", rooms=" + rooms + ", priceFerNight=" + priceFerNight
				+ ", orders=" + orders + ", totalIncome=" + totalIncome + "]";
	}

}
