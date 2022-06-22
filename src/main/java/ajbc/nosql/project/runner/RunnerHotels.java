package ajbc.nosql.project.runner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
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
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.nosql.project.connection.MyConnectionString;
import ajbc.nosql.project.models.Hotel;
import ajbc.nosql.project.models.Order;
import ajbc.nosql.project.models.Room;
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

//			initHotelsCollection(hotelsCollection);
			
			getOrdersByCustomerId(new ObjectId("62aae62a42f5df19293d9c82"));
			
		}
	}

	private static void getOrdersByCustomerId(ObjectId id) {
		// TODO Auto-generated method stub
		
	}

	private static void initHotelsCollection(MongoCollection<Hotel> hotelsCollection) {
		List<Order> orders = Arrays.asList();
		
		
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
