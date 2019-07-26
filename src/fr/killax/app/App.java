package fr.killax.app;

import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.killax.app.data.MySQLClient;
import fr.killax.app.data.Product;
import fr.killax.app.data.User;
import fr.killax.app.data.mysql.Column;
import fr.killax.app.data.mysql.Tuple;

public class App {

	private static MySQLClient mysql;
	private static CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<Product>();
	
	public static void main(String[] args) {
		MySQL();
		User user = new User("M", "Colique", "Al", "karyumgames@gmail.com");
		String link = "https://www.amazon.fr/Fossil-FTW4017-Montre-Connect%C3%A9e/dp/B07H57VJ5W/ref=sr_1_11?__mk_fr_FR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&keywords=Fossil&qid=1564149880&s=gateway&sr=8-11";
		
		products.add(new Product(link, 99999.00, user));
		runChecker();
	}
	
	private static void runChecker(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Checker(), 3600*1000*12, 3600*1000*12);
	}

	public static CopyOnWriteArrayList<Product> getProducts() {
		return products;
	}
	
	private static void MySQL() {
		try {
			mysql = new MySQLClient("localhost", "root", "", 3306);
			mysql.connect();
			mysql.setDatabase("test");
			mysql.createTable("test", 
				new Column("id", "INT AUTO_INCREMENT PRIMARY KEY"),
				new Column("name", "VARCHAR(255)")
			);
			mysql.insert("test", new Column[] {
					new Column("name", "VARCHAR(255)")
				}
				,new Tuple[] {
					new Tuple("Killax"),
					new Tuple("Rokem")
				}
			);
			System.out.println(String.format("Result of Selection : %s", mysql.selectAllToMap("test", "id").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
