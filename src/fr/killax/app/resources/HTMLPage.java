package fr.killax.app.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HTMLPage {

	private static Map<String, String> htmls = new HashMap<String, String>();
	
	public static String getPage(String path) {
		if (htmls.containsKey(path)) {
			return htmls.get(path);
		}
		try (InputStream is = HTMLPage.class.getResourceAsStream(path)) {
			if (is != null) {
				String content = "";
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = null;
				while((line = br.readLine()) != null) {
					content += line+"\n";
				}
				htmls.putIfAbsent(path, content);
				return content;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "<h1>Error 404: Template page not found</h1>";
	}

}
