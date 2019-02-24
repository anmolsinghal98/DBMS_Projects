package Assignment2;


public class Main {

    public static void main(String[] args){

        Database airline=new Database();

        Flight f1=new Flight(1,10);
        Flight f2=new Flight(2,3);
        Flight f3=new Flight(3, 5);
        airline.flights.add(f1);
        airline.flights.add(f2);
        airline.flights.add(f3);

        Passenger p1=new Passenger(1);
        Passenger p2=new Passenger(2);
        Passenger p3=new Passenger(3);
        Passenger p4=new Passenger(4);
        Passenger p5=new Passenger(5);
        airline.passengers.add(p1);
        airline.passengers.add(p2);
        airline.passengers.add(p3);
        airline.passengers.add(p4);
        airline.passengers.add(p5);
    }
}
