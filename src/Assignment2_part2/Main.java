package Assignment2_part2;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

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

        ConcurrencyControl CCM = new ConcurrencyControl();

        int tasks=100;

        int noThreads=4;

        long startTime=System.currentTimeMillis();

        ExecutorService exec= Executors.newFixedThreadPool(noThreads);

        for(int i=0;i<noThreads;i++){
            Transaction t=new Transaction(airline,CCM,tasks/noThreads);
            exec.execute(t);
        }

        if(!exec.isTerminated()){
            exec.shutdown();
            exec.awaitTermination(5L, TimeUnit.SECONDS);
        }

        long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);


    }
}
