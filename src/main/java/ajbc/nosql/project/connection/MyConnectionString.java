package ajbc.nosql.project.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;

public class MyConnectionString {

	private static String PROPERTIES_FILE = "nosql.properties";

	public static ConnectionString uri() {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		Properties props = new Properties();
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(PROPERTIES_FILE);
			props.load(inputStream);
			
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String cluster = props.getProperty("cluster");
			String server_addres = props.getProperty("server_addres");
			String param1 = props.getProperty("param1");
			String param2 = props.getProperty("param2");

			String uri = "mongodb+srv://%s:%s@%s.%s/?%s&%s".formatted(user, password, cluster, server_addres, param1, param2);
			
			return new ConnectionString(uri);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
