CREATE DEFINER=`root`@`localhost` PROCEDURE `upd_flight_req`(uPrice varchar(45), uPriceRank varchar(45), uRequestTime varchar(45), 
uRequestDate varchar(45), uFlightDepartDate varchar(45), uFlightDepartNum varchar(45), uFlightReturnDate varchar(45), uFlightReturnNum varchar(45))
BEGIN

insert into flight_request (FlightLegDepartID, FlightLegReturnID, Price, PriceRank, RequestTime, RequestDate)
value ((select FlightLegID from flight_leg where FlightNumber = uFlightDepartNum and DepartDate = uFlightDepartDate),
(select FlightLegID from flight_leg where FlightNumber = uFlightReturnNum and DepartDate = uFlightReturnDate),
uPrice, uPriceRank, uRequestTime, uRequestDate);

END