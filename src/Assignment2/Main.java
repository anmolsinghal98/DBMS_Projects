package Assignment2;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    public static void main(String[] args){

        Database airline=new Database();

        Flight f1=new Flight(1,5);
        Flight f2=new Flight(2,2);
        Flight f3=new Flight(3, 3);
        Flight f4=new Flight(4,10);
        Flight f5=new Flight(5,4);
        airline.flights.add(f1);
        airline.flights.add(f2);
        airline.flights.add(f3);
        airline.flights.add(f4);
        airline.flights.add(f5);

        for(int i=0;i<21;i++){
            Passenger p=new Passenger(i+1);
            airline.passengers.add(p);
        }
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ConcurrencyControl CCM = new ConcurrencyControl(rwl);
        Transaction t1 = new Transaction(airline,CCM);
        Transaction t2 = new Transaction(airline,CCM);
        t1.reserve(f2,1);
        t1.reserve(f2,2);
        t1.reserve(f1,3);
        t1.transfer(f1,f2,3);
        t1.reserve(f1,2);
        //System.out.println(s);
        airline.print_contents();
    }
}
