package fr.killax.app.data;

public class ImageBase64 {

	private String rawContent;
	private String header;
	private String content;
	
	public ImageBase64(String data) {
		rawContent = data;
		header = data.split(",")[0];
		content = data.split(",")[1];
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getRawContent() {
		return rawContent;
	}
	
	public String getContent() {
		return content;
	}
	
	public String toString() {
		return rawContent;
	}
}
