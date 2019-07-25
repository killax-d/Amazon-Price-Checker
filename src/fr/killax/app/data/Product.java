package fr.killax.app.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Product {
	
	private String name;
	private double price;

	private String url;
	private double requestedPrice;
	private User user;
	private ImageBase64 image;
	private String delivry;
	private String features;
	
	// id of element
	private static String nameThreshold = "productTitle";
	private static String priceThreshold = "priceblock_ourprice";
	private static String delivryThreshold = "ddmDeliveryMessage";
	private static String featuresThreshold = "feature-bullets";

	public Product(String url, double requestedPrice, User user) {
		this.url = url;
		this.requestedPrice = requestedPrice;
		this.name = "unknow";
		this.price = 0.00;
		this.user = user;
		try {
			update();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() throws Exception {
		org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36").get();
	    this.name = ((Element) doc.getElementById(nameThreshold)).text();
	    String rawPrice = ((Element) doc.getElementById(priceThreshold)).text().replace(",", ".");
	    this.price = Double.parseDouble(rawPrice.substring(0, rawPrice.length()-2));
	    this.image = new ImageBase64(doc.select(".imgTagWrapper").first().child(0).attr("src"));
	    this.delivry = doc.getElementById(delivryThreshold).html();
	    this.features = doc.getElementById(featuresThreshold).html();
	    
	    System.out.println(String.format("Item : %s CHECKED.", name));
	    if(price < requestedPrice)
	    	new Email(user, this).send();
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public double getPrice() {
		return price;
	}
	
	public ImageBase64 getImage() {
		return image;
	}
	
	public String getDelivry() {
		return delivry;
	}
	
	public String getFeatures() {
		return features;
	}
	
	public double getRequestedPrice() {
		return requestedPrice;
	}
}
