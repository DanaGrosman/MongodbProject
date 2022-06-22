package ajbc.nosql.project.DbManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;

import ajbc.nosql.project.crud.GeneralDAO;
import ajbc.nosql.project.models.Customer;
import ajbc.nosql.project.models.Hotel;
import ajbc.nosql.project.models.Order;
import ajbc.nosql.project.models.Room;

public class DBManager {
	private MongoCollection<Hotel> hotelsCollection;
	private MongoCollection<Order> ordersCollection;
	private MongoCollection<Customer> customersCollection;	
	private GeneralDAO dao;
	
	public DBManager(MongoCollection<Hotel> hotelsCollection, MongoCollection<Order> ordersCollection,
			MongoCollection<Customer> customersCollection) {
		this.hotelsCollection = hotelsCollection;
		this.ordersCollection = ordersCollection;
		this.customersCollection = customersCollection;
		this.dao = new GeneralDAO(ordersCollection, customersCollection, hotelsCollection);
	}

	public void initCustomersCollection() {
		List<ObjectId> orders = new ArrayList<ObjectId>();
		List<Customer> customers = Arrays.asList(new Customer("Dana", "Grosman", "Israel", orders),
				new Customer("Adir", "Grosman", "Israel", orders), new Customer("Talya", "Grosman", "Israel", orders),
				new Customer("Chris", "Brown", "USA", orders));

		InsertManyResult insertManyResult = customersCollection.insertMany(customers);
		System.out.println(insertManyResult.wasAcknowledged());
	}

	public void initOrdersCollection() {
		List<Order> orders = Arrays.asList(
				new Order(new ObjectId("62b2fb811b5519215052216f"), new ObjectId("62b2fb811b55192150522172"),
						100, LocalDateTime.of(2022, 8, 25, 8, 0), 3, 2490f),
				new Order(new ObjectId("62b2fb811b5519215052216f"), new ObjectId("62b2fb811b55192150522173"),
						101, LocalDateTime.of(2022, 7, 15, 8, 0), 2, 1660f),
				new Order(new ObjectId("62b2fb811b5519215052216f"), new ObjectId("62b2fb811b55192150522173"),
						101, LocalDateTime.of(2022, 10, 20, 8, 0), 4, 3320f));

		List<Order> orderDocs = dao.insertOrders(orders);
		orderDocs.forEach(System.out::println);
	}

	public void initHotelsCollection() {
		List<ObjectId> orders = new ArrayList<ObjectId>();

		List<Room> hermosoRooms = Arrays.asList(new Room(100, 2, true), new Room(101, 2, true), new Room(102, 2, false),
				new Room(103, 2, false));
		Hotel hermoso = new Hotel("Hermoso", "Yaffo", 70, "Jerusalem", "Israel", 5, hermosoRooms, 1200f, orders);

		List<Room> lindoRooms = Arrays.asList(new Room(100, 4, true), new Room(101, 4, true), new Room(102, 4, false));
		Hotel lindo = new Hotel("Lindo", "Boulevard Diderot", 12, "Paris", "France", 3, lindoRooms, 550f, orders);

		List<Room> belloRooms = Arrays.asList(new Room(100, 3, true), new Room(101, 3, false));
		Hotel bello = new Hotel("Bello", "Amstel", 144, "AMS", "Netherlands", 4, belloRooms, 830, orders);

		List<Hotel> hotels = new ArrayList<Hotel>();
		hotels.add(hermoso);
		hotels.add(lindo);
		hotels.add(bello);

		InsertManyResult insertManyResult = hotelsCollection.insertMany(hotels);
		System.out.println(insertManyResult.wasAcknowledged());
	}
}
