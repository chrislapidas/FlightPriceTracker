import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FlightTrackerMain {

	public static void main(String[] args) throws Exception {

		Timer timer = new Timer ();
		TimerTask hourlyTask = new TimerTask () {
		    @Override
		    public void run () {
		    	
				DateFormat df = new SimpleDateFormat("MM/dd/yy");
				Date dateObj = new Date();
				
				
				DateFormat tf = new SimpleDateFormat("HH:mm:ss");
				Date timeObj = new Date();
				
				
				PostRequest myPost = new PostRequest();
				PostResponse myPostResponse = null;
				try {
					myPostResponse = new PostResponse(myPost.makePostRequest());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				
				
				mysqlConnection dbCon = new mysqlConnection();
				
				for (int i = 0; i < 5; i++){
					for (int j = 0; j < 2; j++){
						dbCon.updateDBleg(myPostResponse.getAirline(i, j), myPostResponse.getFlightNumber(i, j), myPostResponse.getFlightDepartDate(i, j),
							myPostResponse.getFlightDepartTime(i, j), myPostResponse.getOriginAirport(i, j), 
							myPostResponse.getFlightArriveDate(i, j), myPostResponse.getFlightArriveTime(i, j), 
							myPostResponse.getDestinationAirport(i, j));
					}
				
				}
				
				for (int i = 0; i < 5; i++){
					
					dbCon.updateDBreq(myPostResponse.getSaleTotal(i), Integer.toString(i+1), tf.format(timeObj), df.format(dateObj),
							myPostResponse.getFlightDepartDate(i, 0), myPostResponse.getFlightNumber(i, 0), 
							myPostResponse.getFlightDepartDate(i, 1), myPostResponse.getFlightNumber(i, 1));
					
				
				}
		
		    }
		};

		// schedule the task to run starting now and then every 45 min...
		timer.schedule (hourlyTask, 0l, 1000*60*45);
		
		
	}

}
