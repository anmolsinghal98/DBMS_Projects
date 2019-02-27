package Assignment2;

import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Runnable{

    Database d;
    ConcurrencyControl CCM;
    int tasks;

    Transaction(Database d, ConcurrencyControl CCM,int tasks){
        this.CCM = CCM;
        this.d = d;
        this.tasks=tasks;
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
        try{
            for(int i=0;i<tasks;i++){
                int ub_flights = d.flights.size();
                int ub_passengers = d.passengers.size();
                int rflights = rand.nextInt(ub_flights);
                int rpassengers = rand.nextInt(ub_passengers);
                Flight f1 = d.flights.get(rflights);
                int chooser = rand.nextInt(5);

                if(chooser == 0){
                    CCM.AcquireLock(1);
                    Thread.sleep(10);
                    reserve(f1,d.passengers.get(rpassengers).getId());
                    System.out.println("Flight with id "+f1.getId()+" reserved for "+rpassengers);
                    CCM.ReleaseLock(1);
                }
                else if(chooser == 1){
                    CCM.AcquireLock(1);
                    Thread.sleep(10);
                    if(d.passengers.get(rpassengers).flist.size()!=0){
                        int rv = rand.nextInt(d.passengers.get(rpassengers).flist.size());
                        Flight tc = d.passengers.get(rpassengers).flist.get(rv);
                        cancel(tc,d.passengers.get(rpassengers).getId());
                        System.out.println("Flight with id "+tc.getId()+" canceled for "+rpassengers);
                    }
                    else{
                        System.out.println("No cancellation");
                    }
                    CCM.ReleaseLock(1);
                }
                else if(chooser == 2){
                    CCM.AcquireLock(2);
                    Thread.sleep(10);
                    ArrayList<Flight> f=My_Flight(rpassengers);
                    System.out.println("Flights for passenger "+rpassengers);
                    if(f!=null){
                        for(int s=0;s<f.size();s++){
                            System.out.print(f.get(s).getId()+",");
                        }
                    }
                    if(f==null || f.size()==0){
                        System.out.println("No flights booked");
                    }
                    System.out.println();
                    CCM.ReleaseLock(2);

                }
                else if(chooser == 3){
                    CCM.AcquireLock(2);
                    Thread.sleep(10);
                    int s=Total_reservations();
                    System.out.println("Total reservations- "+s);
                    CCM.ReleaseLock(2);
                }
                else if(chooser == 4){
                    CCM.AcquireLock(1);
                    Thread.sleep(10);
                    int rflight2 = rand.nextInt(ub_flights);
                    Flight f2 = d.flights.get(rflight2);
                    transfer(f1,f2,d.passengers.get(rpassengers).getId());
                    System.out.println("Transfer of "+rpassengers+" from flight"+f1.getId()+" to "+f2.getId());
                    CCM.ReleaseLock(1);
                }
            }
        }
        catch(Exception e){
            System.out.println("Timeout");
        }


    }


}
