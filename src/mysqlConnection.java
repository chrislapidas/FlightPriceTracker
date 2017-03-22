import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlConnection {
	
	Connection myConn;
	
	public mysqlConnection(){
		try {

			myConn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/flight_tracker", "root", "cheapflights");

			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	

	}
	
	public void updateDBleg(String flightNum, String airline, String departDate, String departTime, 
			String departAirport, String arriveDate, String arriveTime, String arriveAirport){
		try{
			CallableStatement csLeg = myConn.prepareCall("{call upd_flight_leg(?,?,?,?,?,?,?,?)}");
			csLeg.setString(1, airline);
			csLeg.setString(2, flightNum);
			csLeg.setString(3, departDate);
			csLeg.setString(4, departTime);
			csLeg.setString(5, departAirport);
			csLeg.setString(6, arriveDate);
			csLeg.setString(7, arriveTime);
			csLeg.setString(8, arriveAirport);
			csLeg.executeUpdate();
			
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
	}


	public void updateDBreq(String price, String priceRank, String requestTime, String requestDate,
			String departDate, String departFlightNum, String returnFlightDate, String returnFlightnum){
		try{
			CallableStatement csReq = myConn.prepareCall("{call upd_flight_req(?,?,?,?,?,?,?,?)}");
			csReq.setString(1, price);
			csReq.setString(2, priceRank);
			csReq.setString(3, requestTime);
			csReq.setString(4, requestDate);
			csReq.setString(5, departDate);
			csReq.setString(6, departFlightNum);
			csReq.setString(7, returnFlightDate);
			csReq.setString(8, returnFlightnum);
			csReq.executeUpdate();
		
	
			
		
		} catch (SQLException e) {
			e.printStackTrace();
	
		}
	
	}
}
