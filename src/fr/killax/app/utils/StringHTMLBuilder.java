package fr.killax.app.utils;

public class StringHTMLBuilder {

	public static String format(String content, Object... obj) {
		int i = 0;
		String newContent = "";
		
		for (String line : content.split("\n")) {
			if (line != null) {
				String lineEdit = line;
				while (lineEdit.contains("%s")) {
					if(i < obj.length)
						lineEdit = lineEdit.replace("%s", obj[i++].toString());
					else
						break;
				}
				newContent += lineEdit + "\n";
			}
		}
		return newContent;
	}
}
