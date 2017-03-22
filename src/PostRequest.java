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

	public JsonObject makePostRequest() throws Exception{
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
		InputStream jsonTxt = new FileInputStream("jsonIn.txt");
		JsonReader jReader = Json.createReader(jsonTxt);
		JsonObject jsonObj = jReader.readObject();
		

		//send post request
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObj.toString());
		wr.flush();
		wr.close();

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
