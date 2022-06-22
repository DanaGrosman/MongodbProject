package ajbc.nosql.project.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

public class Order {
	private ObjectId id;
	private ObjectId hotelId;
	private ObjectId customerId;
	private int roomNumber;
	private LocalDateTime orderDate;
	private LocalDateTime startDate;
	private int numberOfNights;
	private float totalPrice;

	public Order(ObjectId id, ObjectId hotelId, ObjectId customerId, int roomNumber, LocalDateTime startDate,
			int numberOfNights, float totalPrice) {
		super();
		this.id = id;
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.roomNumber = roomNumber;
		this.orderDate = LocalDateTime.now();
		this.startDate = startDate;
		this.numberOfNights = numberOfNights;
		this.totalPrice = totalPrice;
	}

	public Order(ObjectId hotelId, ObjectId customerId, int roomNumber, LocalDateTime startDate, int numberOfNights,
			float totalPrice) {
		super();
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.roomNumber = roomNumber;
		this.orderDate = LocalDateTime.now();
		this.startDate = startDate;
		this.numberOfNights = numberOfNights;
		this.totalPrice = totalPrice;
	}

	public Order() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getHotelId() {
		return hotelId;
	}

	public void setHotelId(ObjectId hotelId) {
		this.hotelId = hotelId;
	}

	public ObjectId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(ObjectId customerId) {
		this.customerId = customerId;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", hotelId=" + hotelId + ", customerId=" + customerId + ", roomNumber=" + roomNumber
				+ ", orderDate=" + orderDate + ", startDate=" + startDate + ", numberOfNights=" + numberOfNights
				+ ", totalPrice=" + totalPrice + "]";
	}

}
