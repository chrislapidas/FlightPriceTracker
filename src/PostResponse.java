import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

public class PostResponse {
	
	private JsonObject myJsonResponse;
	private JsonArray tripOptionArray;
	
	//constructor
	public PostResponse(JsonObject jsonSetter){
		myJsonResponse = jsonSetter;
		tripOptionArray = myJsonResponse.getJsonObject("trips").getJsonArray("tripOption");
	}
	
	public String getSaleTotal(int indexPos){
		JsonObject tripOption = tripOptionArray.getJsonObject(indexPos);
		JsonString saleJsonString = tripOption.getJsonString("saleTotal");
		return saleJsonString.toString().substring(4, 10);
	}
	
	public String getFlightNumber(int indexPos, int legNum){
		
		JsonObject segmentObject = getSegmentObject(indexPos, legNum);
		JsonObject flightObj = segmentObject.getJsonObject("flight");
		JsonString flightNumber = flightObj.getJsonString("number");
		return flightNumber.getString();	
	}
	
	public String getFlightDepartTime(int indexPos, int legNum){
		
		JsonObject legObject = getLegObject(indexPos, legNum);
		JsonString flightTime = legObject.getJsonString("departureTime");
		return flightTime.toString().substring(12, 23);
	}
	
	public String getFlightArriveTime(int indexPos, int legNum){
		
		JsonObject legObject = getLegObject(indexPos, legNum);
		JsonString flightTime = legObject.getJsonString("arrivalTime");
		return flightTime.toString().substring(12, 23);
	}
	

	public String getFlightDepartDate(int indexPos, int legNum){
		
		JsonObject legObject = getLegObject(indexPos, legNum);
		JsonString flightTime = legObject.getJsonString("departureTime");
		return  flightTime.toString().substring(1, 11);
	}
	
	public String getFlightArriveDate(int indexPos, int legNum){
		
		JsonObject legObject = getLegObject(indexPos, legNum);
		JsonString flightTime = legObject.getJsonString("arrivalTime");
		return flightTime.toString().substring(1, 11);
	}
	
	public String getAirline(int indexPos, int legNum){

		JsonObject segmentObject = getSegmentObject(indexPos, legNum);
		JsonObject flightObj = segmentObject.getJsonObject("flight");
		JsonString flightAirline = flightObj.getJsonString("carrier");
		return flightAirline.getString();
	}
	
	public String getOriginAirport(int indexPos, int legNum){
		JsonObject segmentObject = getSegmentObject(indexPos, legNum);
		JsonArray legArray = segmentObject.getJsonArray("leg");
		JsonObject legObject = legArray.getJsonObject(0);
		JsonString airport = legObject.getJsonString("origin");
		return airport.getString();
	}
	
	public String getDestinationAirport(int indexPos, int legNum){
		JsonObject segmentObject = getSegmentObject(indexPos, legNum);
		JsonArray legArray = segmentObject.getJsonArray("leg");
		JsonObject legObject = legArray.getJsonObject(0);
		JsonString airport = legObject.getJsonString("destination");
		return airport.getString();
	}
	
	//helper method
	public JsonObject getSegmentObject(int indexPos, int legNum){
		
		JsonObject tripOption = tripOptionArray.getJsonObject(indexPos);
		JsonArray sliceArray = tripOption.getJsonArray("slice");
		JsonObject sliceObj = sliceArray.getJsonObject(legNum);
		JsonArray segmentArray = sliceObj.getJsonArray("segment");
		JsonObject segmentObject = segmentArray.getJsonObject(0);
		return segmentObject;
		
	}
	//helper method
	public JsonObject getLegObject(int indexPos, int legNum){
		JsonObject segmentObject = getSegmentObject(indexPos, legNum);
		JsonArray legArray = segmentObject.getJsonArray("leg");
		JsonObject legObject = legArray.getJsonObject(0);
		return legObject;
	}

}
