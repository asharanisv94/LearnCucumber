package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonReader {

	public static JSONObject readJsonFile(String fileName) {
		JSONObject jsonObject=null;
		
		//Create a file object by specifying file path
				File file = new File("src/test/resources/data/"+fileName);

				try {
				
					JSONTokener tokener = new JSONTokener(new FileReader(file));
					
					jsonObject = new JSONObject(tokener);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return jsonObject;
				
	}
}
