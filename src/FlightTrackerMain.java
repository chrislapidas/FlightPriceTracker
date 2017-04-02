import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FlightTrackerMain {

	public static void main(String[] args) throws Exception {

		//hourlyTask is used to schedule how often to run the following code under run()
		Timer timer = new Timer ();
		TimerTask hourlyTask = new TimerTask () {
		    
			//runs flight price request and stores results in database
		    public void run () {
		    	
		    	//time and date objects used to record time/date of flight price request
				DateFormat df = new SimpleDateFormat("MM/dd/yy");
				Date dateObj = new Date();
				DateFormat tf = new SimpleDateFormat("HH:mm:ss");
				Date timeObj = new Date();
				
				
				PostRequest myPost = new PostRequest();
				PostResponse myPostResponse = null;
				try {
					//send the data from jsonIn.txt for the post request. this file contains the details of which flights are
					//to be returned (departure and destination locations, passengers, times, ect.) Details on what types you can
					//specify are found here:
					//   https://developers.google.com/qpx-express/v1/trips/search
					myPostResponse = new PostResponse(myPost.makePostRequest("jsonIn.txt"));
				} catch (Exception e) {
					e.printStackTrace();
				}
		
				//initiate connection to MySQL database
				mysqlConnection dbCon = new mysqlConnection();
				
				//loop to update flight_leg table in database. This table stores the data concerning each leg of every flight, 
				//meaning each individual flight of a round trip flight in is it's own flight leg.
				for (int i = 0; i < 5; i++){
					for (int j = 0; j < 2; j++){
						dbCon.updateDBleg(myPostResponse.getAirline(i, j), myPostResponse.getFlightNumber(i, j), myPostResponse.getFlightDepartDate(i, j),
							myPostResponse.getFlightDepartTime(i, j), myPostResponse.getOriginAirport(i, j), 
							myPostResponse.getFlightArriveDate(i, j), myPostResponse.getFlightArriveTime(i, j), 
							myPostResponse.getDestinationAirport(i, j));
					}
				}
				
				//loop to update flight_request table. Each flight request may consist of multiple legs depending on whether the 
				//flight is one way or round trip. 
				for (int i = 0; i < 5; i++){
					dbCon.updateDBreq(myPostResponse.getSaleTotal(i), Integer.toString(i+1), tf.format(timeObj), df.format(dateObj),
							myPostResponse.getFlightDepartDate(i, 0), myPostResponse.getFlightNumber(i, 0), 
							myPostResponse.getFlightDepartDate(i, 1), myPostResponse.getFlightNumber(i, 1));
				}
		    }
		};

		// schedule the task to run starting now and then every 60 min...
		timer.schedule (hourlyTask, 0l, 1000*60*60);
		
	}

}
