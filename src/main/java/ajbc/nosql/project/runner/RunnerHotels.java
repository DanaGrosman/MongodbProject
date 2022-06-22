package ajbc.nosql.project.runner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.nosql.project.DbManager.DBManager;
import ajbc.nosql.project.connection.MyConnectionString;
import ajbc.nosql.project.crud.GeneralDAO;
import ajbc.nosql.project.models.Customer;
import ajbc.nosql.project.models.Hotel;
import ajbc.nosql.project.models.Order;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class RunnerHotels {
	public static void main(String[] args) {
		// set logger to ERROR only
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings);) {
			MongoDatabase myDb = mongoClient.getDatabase("GoodTimesHotels");
			MongoCollection<Hotel> hotelsCollection = myDb.getCollection("hotels", Hotel.class);
			MongoCollection<Order> ordersCollection = myDb.getCollection("orders", Order.class);
			MongoCollection<Customer> customersCollection = myDb.getCollection("customers", Customer.class);
			GeneralDAO dao = new GeneralDAO(ordersCollection, customersCollection, hotelsCollection);

			// init DB
//			DBManager dbManager = new DBManager(hotelsCollection, ordersCollection, customersCollection);
//			dbManager.initHotelsCollection();
//			dbManager.initCustomersCollection();
//			dbManager.initOrdersCollection();

			// Q1
//			getOrdersByCustomerId(new ObjectId("62b2d3fa8fdb3c5587a853bc"), dao);

			// Q2
//			getHotelsByCity("Paris", dao);

			// Q3
//			System.out.println(getIfHotelHasAvailableRoom(LocalDateTime.of(2022, 8, 26, 8, 0), 2, new ObjectId("62b2fb811b5519215052216e"), dao));

			// Q4
//			LocalDateTime date = LocalDateTime.of(2022, 9, 1, 8, 0);
//			int roomNumber = getIfHotelHasAvailableRoom(date, 2, new ObjectId("62b2fb811b5519215052216d"), dao);
//			System.out.println(roomNumber);
//			if (roomNumber != -1) {
//				addOrderToDB(new Order(new ObjectId("62b2fb811b5519215052216d"),
//						new ObjectId("62b2fb811b55192150522172"), roomNumber, date, 2, 2400), dao);
//			}

			// Q5
//			cancelOrder(new ObjectId("62b3706540c4df4364500f51"), dao);

			// Q6
			sortHotelsByTotalIncomes(dao);
		}
	}

	private static void sortHotelsByTotalIncomes(GeneralDAO dao) {
		List<Hotel> sortedHotels = dao.getSortedHotelsByTotalIncome();
	}

	private static void cancelOrder(ObjectId orderId, GeneralDAO dao) {
		Order order = dao.cancelOrderById(orderId);
		System.out.println(order);
	}

	private static void addOrderToDB(Order order, GeneralDAO dao) {
		order = dao.insertOneOrder(order);
		System.out.println(order);
	}

	private static int getIfHotelHasAvailableRoom(LocalDateTime date, int numOfNights, ObjectId hotelId,
			GeneralDAO dao) {
		int roomNumber = dao.cheakIfHotelHasAvailableRoomByDate(date, numOfNights, hotelId);
		return roomNumber;
	}

	private static void getHotelsByCity(String city, GeneralDAO dao) {
		List<Hotel> hotels = dao.getHotelsByCity(city);
		hotels.forEach(System.out::println);
	}

	private static void getOrdersByCustomerId(ObjectId customerId, GeneralDAO dao) {
		List<Order> orders = dao.getOrdersByCustomerId(customerId);
		orders.forEach(System.out::println);
	}

}
