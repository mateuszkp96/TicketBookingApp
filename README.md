# TicketBookingApp
Seat reservation system for a multiplex.

### Technologies used
* Java
* Spring Boot
* H2 Database

### Assumptions
* There cannot be a single place left over in a row between two already reserved places. The seat that is on the edge can always be reserved.
* Seats can be booked at latest 15 minutes before the screening begins. 
* Reservation expiration time is set to 10 minutes before the screening begins.

### Build  
Application is written in Java JDK version 11. The application uses Gradle wrapper to build and then run app using *java -jar*.

To build and run app type command:
*bash build_and_run_script.sh PORT_NUMBER*
where *PORT_NUMBER* is number of port of localhost on which app runs. When *PORT_NUMBER* is not defined, application starts on port 8080.

If is problem to run script, check if file *gradlew* has a execution permision.

### Run use case
There are two scripts which run use case.

First is *run_use_cases.sh* which uses **jq** for pretty JSON formatting.

Second is *run_use_cases_without_jq.sh* which print JSON in normal way (as String).

To run use case type command:  
*bash SCRIPT_NAME PORT_NUMBER*  
where *PORT_NUMBER* is number of port of localhost on which app runs. When *PORT_NUMBER* is not defined, application make calls on port 8080.

