package ajbc.nosql.project.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Room {
	private ObjectId id;
	private int number;
	private int numberOfGuests;
	private boolean hasABath;
	private List<LocalDateTime> unavailableDates;

	public Room(ObjectId id, int number, int numberOfGuests, boolean hasABath) {
		super();
		this.id = id;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.hasABath = hasABath;
		unavailableDates = new ArrayList<LocalDateTime>();
	}

	public Room(int number, int numberOfGuests, boolean hasABath) {
		super();
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.hasABath = hasABath;
		unavailableDates = new ArrayList<LocalDateTime>();
	}

	public Room() {
		unavailableDates = new ArrayList<LocalDateTime>();
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

	public List<LocalDateTime> getUnavailableDates() {
		return unavailableDates;
	}

	public void setUnavailableDates(List<LocalDateTime> unavailableDates) {
		this.unavailableDates = unavailableDates;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", numberOfGuests=" + numberOfGuests + ", hasABath=" + hasABath
				+ ", unavailableDates=" + unavailableDates + "]";
	}

}
