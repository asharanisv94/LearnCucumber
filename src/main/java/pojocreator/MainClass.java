package pojocreator;

import java.io.File;
import java.io.IOException;

public class MainClass {

	public static void main(String[] arg) throws IOException {
		CreatePojoFromJson createPojoFromJson = new CreatePojoFromJson();
		
		String outputFileDirectory = "src/main/java/";
		
		File file = new File(outputFileDirectory);
		createPojoFromJson.convertJsonToJavaClass(file,"model.createIssueRequest","CreateIssueRequest",utils.JsonReader.readJsonFile("CreateIssue.json").toString());
	}
}
