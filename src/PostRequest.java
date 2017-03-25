import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class PostRequest {

	/*     METHOD: makePostRequest(String jsonInTxtFile)
	 * 
	 * Submits a post request using an input txt file, turning it into a json object, sends request, takes returning contents
	 * and saves them to an output file, then reads those contens back in to a new json object which is returned.
	 * 
	 * Input: jsonInTxtFile - This is a text file in the format of the json input for QPX Express. For formatting, check online docs.
	 * 
	 * Output: JsonObject - javax.json object that stores the results from the POST request. Formatting is again detailed
		on the online docs for QPX express. 
	*/
	
	public JsonObject makePostRequest(String jsonInTxtFile) throws Exception{
		//url for the post request
		//TO MAKE POST REQUEST: Register for an api key on google's API website. You may need to search for "QPX Express" to show up
		//on the list of APIs. After registering, they will provide you with a key. Use it where it says INSERT_API_KEY_HERE.
		String url = "https://www.googleapis.com/qpxExpress/v1/trips/search?key=INSERT_API_KEY_HERE";
		URL urlObj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection)urlObj.openConnection();
		
		System.out.println("Attempting connection...");

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setDoInput(true);
		con.setDoOutput(true);
		
		//create json input from .txt file for post
		InputStream jsonTxt = new FileInputStream(jsonInTxtFile);
		JsonReader jReader = Json.createReader(jsonTxt);
		JsonObject jsonObj = jReader.readObject();
		

		//send post request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObj.toString());
		wr.flush();
		wr.close();

		//response code shows status of request
		//200 is a normal response
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		//buffered reader to receive response from POST request
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		//write response to string
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//create file to write string response to
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("jsonOutput.txt")));
		bwr.write(response.toString());
		bwr.flush();
		bwr.close();
		
		//put text file into json obj
		InputStream jsonTxtResponse = new FileInputStream("jsonOutput.txt");
		JsonObject jsonObjResponse = Json.createReader(jsonTxtResponse).readObject();
		return jsonObjResponse;
	}
}
