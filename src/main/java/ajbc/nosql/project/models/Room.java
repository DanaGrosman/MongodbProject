package ajbc.nosql.project.models;

import org.bson.types.ObjectId;

public class Room {
	private ObjectId id;
	private int number;
	private int numberOfGuests;
	private boolean hasABath;

	public Room(ObjectId id, int number, int numberOfGuests, boolean hasABath) {
		super();
		this.id = id;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.hasABath = hasABath;
	}

	public Room(int number, int numberOfGuests, boolean hasABath) {
		super();
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.hasABath = hasABath;
	}

	public Room() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public boolean isHasABath() {
		return hasABath;
	}

	public void setHasABath(boolean hasABath) {
		this.hasABath = hasABath;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", numberOfGuests=" + numberOfGuests + ", hasABath=" + hasABath
				+ "]";
	}

}
