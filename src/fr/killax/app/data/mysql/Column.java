package fr.killax.app.data.mysql;

public class Column {

	private String label;
	private String parameters;
	
	public Column(String label, String parameters) {
		this.label = label;
		this.parameters = parameters;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getParameters() {
		return parameters;
	}
	
	public String toString() {
		return String.format("%s %s", label, parameters);
	}
}
