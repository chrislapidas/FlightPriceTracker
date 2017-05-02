CREATE DEFINER=`root`@`localhost` PROCEDURE `upd_flight_leg`(flightNum varchar(45), airlineCode varchar(45), 
departDate varchar(45), departTime varchar(45), departAirport varchar(45), arriveDate varchar(45), arriveTime varchar(45), arriveAirport varchar(45))
BEGIN

if not exists (select * from flight_leg where FlightNumber = flightNum and DepartDate = departDate and Airline = airlineCode) 
then insert into flight_leg(Airline, FlightNumber, DepartDate, DepartureTime, DepartureAirport, ArrivalDate, ArrivalTime, ArrivalAirport) 
value (airlineCode, flightNum, departDate, departTime, departAirport, arriveDate, arriveTime, arriveAirport);

END if;

end