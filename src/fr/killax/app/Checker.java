package fr.killax.app;

import java.util.TimerTask;

import fr.killax.app.data.Product;

public class Checker extends TimerTask {

	public void run() {
		for (Product p : App.getProducts()) {
			if(p != null) {
				try {
					System.out.println("Checking product : " + p.getName());
					p.update();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
