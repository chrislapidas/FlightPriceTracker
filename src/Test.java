import java.io.FileInputStream;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;

public class Test {

	public static void main(String[] args) throws Exception {
		InputStream jsonTxtResponse = new FileInputStream("jsonOutput.txt");
		JsonObject jsonObjResponse = Json.createReader(jsonTxtResponse).readObject();
		PostResponse response = new PostResponse(jsonObjResponse);
		
		
		/*
		for (int i = 0; i < 5; i++){
			System.out.println("Flight #" + i + ": " + response.getSaleTotal(i) + " " + 
		response.getOriginAirport(i, ArriveOrDepartEnum.DEPART));
			System.out.println("Depart Flight #" + i + ": " + response.getFlightNumber(i, ArriveOrDepartEnum.DEPART));
			System.out.println("Arrive Flight #" + i + ": " + response.getFlightNumber(i, ArriveOrDepartEnum.ARRIVE));
		}
		*/

	}

}
