package ajbc.nosql.project.crud;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.nosql.project.DbManager.DBManager;
import ajbc.nosql.project.models.Customer;
import ajbc.nosql.project.models.Hotel;
import ajbc.nosql.project.models.Order;
import ajbc.nosql.project.models.Room;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class GeneralDAO {
	private MongoCollection<Order> ordersCollection;
	private MongoCollection<Customer> customersCollection;
	private MongoCollection<Hotel> hotelsCollection;

	public GeneralDAO(MongoCollection<Order> ordersCollection, MongoCollection<Customer> customersCollection,
			MongoCollection<Hotel> hotelsCollection) {
		this.ordersCollection = ordersCollection;
		this.customersCollection = customersCollection;
		this.hotelsCollection = hotelsCollection;
	}

	public Order insertOneOrder(Order order) {
		InsertOneResult insertOneResult = ordersCollection.insertOne(order);
		System.out.println(insertOneResult.wasAcknowledged());

		Bson limit = limit(1);
		Bson sort = sort(Sorts.descending("orderDate"));

		List<Order> results = ordersCollection.aggregate(Arrays.asList(sort, limit)).into(new ArrayList<>());

		insetOrdersByCustomerId(order.getCustomerId(), order.getId());
		insertOrdersByHotelId(order.getHotelId(), order.getId());
		updateRoomsByHotelId(order.getHotelId(), order.getRoomNumber(), order.getStartDate(),
				order.getNumberOfNights());

		return results.get(0);
	}

	public List<Order> insertOrders(List<Order> orders) {
		InsertManyResult insertManyResult = ordersCollection.insertMany(orders);
		System.out.println(insertManyResult.wasAcknowledged());

		Bson limit = limit(orders.size());
		Bson sort = sort(Sorts.descending("orderDate"));

		List<Order> results = ordersCollection.aggregate(Arrays.asList(sort, limit)).into(new ArrayList<>());

		for (Order order : results) {
			insetOrdersByCustomerId(order.getCustomerId(), order.getId());
			insertOrdersByHotelId(order.getHotelId(), order.getId());
			updateRoomsByHotelId(order.getHotelId(), order.getRoomNumber(), order.getStartDate(),
					order.getNumberOfNights());
		}

		return results;
	}

	private Hotel updateRoomsByHotelId(ObjectId hotelId, int roomNumber, LocalDateTime startDate, int numberOfNights) {
		Hotel hotel = getHotelById(hotelId);
		List<Room> rooms = hotel.getRooms();

		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getNumber() == roomNumber) {
				for (int j = 0; j < numberOfNights; j++)
					rooms.get(i).getUnavailableDates().add(startDate.plusDays(j));
			}
		}

		Bson updateCity = set("city", hotel.getCity());
		Bson updateCountry = set("country", hotel.getCountry());
		Bson updateName = set("name", hotel.getName());
		Bson updateNumber = set("number", hotel.getNumber());
		Bson updatePriceFerNight = set("priceFerNight", hotel.getPriceFerNight());
		Bson updateRank = set("rank", hotel.getRank());
		Bson updateRooms = set("rooms", rooms);
		Bson updateStreet = set("street", hotel.getStreet());
		Bson updateOrders = set("orders", hotel.getOrders());

		Bson update = combine(updateCity, updateCountry, updateName, updateNumber, updatePriceFerNight, updateRank,
				updateRooms, updateStreet, updateOrders);

		FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
		hotel = hotelsCollection.findOneAndUpdate(eq("_id", hotelId), update, optionAfter);

		return hotel;
	}

	private Hotel insertOrdersByHotelId(ObjectId hotelId, ObjectId orderId) {
		Hotel hotel = getHotelById(hotelId);
		List<ObjectId> orders = hotel.getOrders();
		orders.add(orderId);

		Bson updateCity = set("city", hotel.getCity());
		Bson updateCountry = set("country", hotel.getCountry());
		Bson updateName = set("name", hotel.getName());
		Bson updateNumber = set("number", hotel.getNumber());
		Bson updatePriceFerNight = set("priceFerNight", hotel.getPriceFerNight());
		Bson updateRank = set("rank", hotel.getRank());
		Bson updateRooms = set("rooms", hotel.getRooms());
		Bson updateStreet = set("street", hotel.getStreet());
		Bson updateOrders = set("orders", orders);

		Bson update = combine(updateCity, updateCountry, updateName, updateNumber, updatePriceFerNight, updateRank,
				updateRooms, updateStreet, updateOrders);

		FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
		hotel = hotelsCollection.findOneAndUpdate(eq("_id", hotelId), update, optionAfter);
		return hotel;
	}

	private Hotel getHotelById(ObjectId hotelId) {
		Hotel hotel = hotelsCollection.find(eq("_id", hotelId)).first();
		return hotel;
	}

	public List<Order> getOrdersByCustomerId(ObjectId customerId) {
		List<Order> orders = ordersCollection.find(eq("customerId", customerId)).into(new ArrayList<>());
		return orders;
	}

	public Customer insetOrdersByCustomerId(ObjectId customerId, ObjectId orderId) {
		Customer customer = getCustomerById(customerId);
		List<ObjectId> orders = customer.getOrders();
		orders.add(orderId);

		Bson updateCountry = set("country", customer.getCountry());
		Bson updatefirstName = set("firstName", customer.getFirstName());
		Bson updateLastName = set("lastName", customer.getLastName());
		Bson updateOrders = set("orders", orders);

		Bson update = combine(updatefirstName, updateLastName, updateCountry, updateOrders);

		FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
		customer = customersCollection.findOneAndUpdate(eq("_id", customerId), update, optionAfter);

		return customer;
	}

	public Customer getCustomerById(ObjectId customerId) {
		Customer customer = customersCollection.find(eq("_id", customerId)).first();
		return customer;
	}

	public List<Hotel> getHotelsByCity(String city) {
		Bson match = match(gte("city", city));
		List<Hotel> hotels = hotelsCollection.aggregate(Arrays.asList(match)).into(new ArrayList<>());

		return hotels;
	}

	public int cheakIfHotelHasAvailableRoomByDate(LocalDateTime date, int numOfNights, ObjectId hotelId) {
		int roomNumber = -1;
		boolean isAvailable = true;

		Bson match = match(eq("_id", hotelId));
		Bson project = project(include("rooms"));
		List<Room> rooms = hotelsCollection.aggregate(Arrays.asList(match, project)).into(new ArrayList<>()).get(0)
				.getRooms();

		for (Room room : rooms) {
			List<LocalDateTime> dateTimes = room.getUnavailableDates();

			if (dateTimes.isEmpty()) {
				isAvailable = true;
				roomNumber = room.getNumber();
				break;
			}

			for (int i = 0; i < numOfNights; i++) {
				date = date.plusDays(i);
				for (LocalDateTime dateTime : dateTimes) {
					if (dateTime.equals(date)) {
						isAvailable = false;
						break;
					}
				}
			}

			if (isAvailable) {
				roomNumber = room.getNumber();
				break;
			}
		}

		return roomNumber;
	}

	public Order cancelOrderById(ObjectId orderId) {
		Order order = ordersCollection.find(eq("_id", orderId)).first();
		
		Bson match = match(eq("_id", orderId));
		Bson project = project(include("customerId", "hotelId"));
		ObjectId customerId = ordersCollection.aggregate(Arrays.asList(match, project)).into(new ArrayList<>()).get(0)
				.getCustomerId();
		ObjectId hotelId = ordersCollection.aggregate(Arrays.asList(match, project)).into(new ArrayList<>()).get(0)
				.getHotelId();

		// delete from ordersCollection
		ordersCollection.findOneAndDelete(eq("_id", orderId));

		// delete from customer
		Bson pull = pull("orders", orderId);
		customersCollection.findOneAndUpdate(eq("_id", customerId), pull);
		
		// delete from hotel
		hotelsCollection.findOneAndUpdate(eq("_id", hotelId), pull);
		
		return order;
	}

}
