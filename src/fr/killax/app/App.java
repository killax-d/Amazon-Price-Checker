package fr.killax.app;

import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.killax.app.data.Product;
import fr.killax.app.data.User;
import fr.killax.logger.Logger;

public class App {

	private static CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<Product>();

	public static Logger logger;
	
	public static void main(String[] args) {
		products.add(new Product("https://www.amazon.fr/dp/B01N5OPMJW/ref=cm_sw_r_tw_dp_U_x_WgCoDb7W2987S", 15.00, new User("M", "Colique", "Al", "contact@dylan-donne.fr")));
		runChecker();
	}
	
	private static void runChecker(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Checker(), 3600*1000*12, 3600*1000*12);
	}

	public static CopyOnWriteArrayList<Product> getProducts() {
		return products;
	}
}
