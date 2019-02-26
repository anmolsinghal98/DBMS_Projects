package Assignment2;

import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Runnable{

    Database d;
    ConcurrencyControl CCM;

    Transaction(Database d, ConcurrencyControl CCM){
        this.CCM = CCM;
        this.d = d;
    }

    void reserve(Flight f, int id){
        if(f.plist.size()<f.getCapacity()){
            for(int i=0;i<d.passengers.size();i++){
                if(d.passengers.get(i).getId()==id){
                    f.plist.add(d.passengers.get(i));
                    d.passengers.get(i).flist.add(f);
                }
            }
        }
    }

    //TODO check remove
    void cancel(Flight f,int id){
        for(int i=0;i<d.passengers.size();i++){
            if(d.passengers.get(i).getId()==id){
                f.plist.remove(d.passengers.get(i));
                d.passengers.get(i).flist.remove(f);
            }
        }
    }

    ArrayList<Flight> My_Flight(int id){
        for(int i=0;i<d.passengers.size();i++){
            if(d.passengers.get(i).getId()==id){
                return d.passengers.get(i).flist;
            }
        }
        return null;
    }

    int Total_reservations(){
        int total=0;
        for(int i=0;i<d.flights.size();i++){
            total+=d.flights.get(i).plist.size();
        }
        return total;
    }

    void transfer(Flight f1, Flight f2, int id){
        if(f2.plist.size()!=f2.getCapacity()){
            for(int i=0;i<f1.plist.size();i++){
                if(f1.plist.get(i).getId()==id){
                    Passenger p=f1.plist.remove(i);
                    f2.plist.add(p);
                    p.flist.remove(f1);
                    p.flist.add(f2);
                }
            }
        }
    }

    @Override
    public void run(){
        Random rand = new Random();
        int ub_flights = d.flights.size();
        int ub_passengers = d.passengers.size();
        int rflights = rand.nextInt(ub_flights);
        int rpassengers = rand.nextInt(ub_passengers);
        Flight f1 = d.flights.get(rflights);

        int chooser = rand.nextInt(5);

        if(chooser == 0){
            CCM.AcquireLock(1);
            reserve(f1,rpassengers);
            CCM.ReleaseLock(1);
        }
        else if(chooser == 1){
            CCM.AcquireLock(1);
            int rv = rand.nextInt(d.passengers.get(rpassengers).flist.size());
            Flight tc = d.passengers.get(rpassengers).flist.get(rv);
            cancel(tc,rv);
            CCM.ReleaseLock(1);
        }
        else if(chooser == 2){
            CCM.AcquireLock(2);
            My_Flight(rpassengers);
            CCM.ReleaseLock(2);

        }
        else if(chooser == 3){
            CCM.AcquireLock(2);
            Total_reservations();
            CCM.ReleaseLock(2);
        }
        else if(chooser == 4){
            CCM.AcquireLock(1);
            int rflight2 = rand.nextInt(ub_flights);
            Flight f2 = d.flights.get(rflight2);
            transfer(f1,f2,rpassengers);
            CCM.ReleaseLock(1);
        }
    }


}
