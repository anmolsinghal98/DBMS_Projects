package Assignment2_part2;


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
        CCM.AcquireLock(1,f.lock);
        if(f.plist.size()<f.getCapacity()){
            for(int i=0;i<d.passengers.size();i++){
                if(d.passengers.get(i).getId()==id){
                    CCM.AcquireLock(1,d.passengers.get(i).lock);
                    f.plist.add(d.passengers.get(i));
                    d.passengers.get(i).flist.add(f);
                    CCM.ReleaseLock(1,d.passengers.get(i).lock);
                }
            }
        }
        CCM.ReleaseLock(1,f.lock);
    }

    void cancel(Flight f,int id){
        CCM.AcquireLock(1,f.lock);
        for(int i=0;i<d.passengers.size();i++){
            if(d.passengers.get(i).getId()==id){
                CCM.AcquireLock(1,d.passengers.get(i).lock);
                f.plist.remove(d.passengers.get(i));
                d.passengers.get(i).flist.remove(f);
                CCM.ReleaseLock(1,d.passengers.get(i).lock);
            }
        }
        CCM.ReleaseLock(1,f.lock);
    }

    ArrayList<Flight> My_Flight(int id){
        for(int i=0;i<d.passengers.size();i++){
            CCM.AcquireLock(2,d.passengers.get(i).lock);
            if(d.passengers.get(i).getId()==id){
                return d.passengers.get(i).flist;
            }
            CCM.ReleaseLock(2,d.passengers.get(i).lock);
        }
        return null;
    }

    int Total_reservations(){
        int total=0;
        for(int i=0;i<d.flights.size();i++){
            CCM.AcquireLock(2,d.flights.get(i).lock);
            total+=d.flights.get(i).plist.size();
            CCM.ReleaseLock(2,d.flights.get(i).lock);
        }
        return total;
    }

    void transfer(Flight f1, Flight f2, int id){
        CCM.AcquireLock(1,f1.lock);
        CCM.AcquireLock(1,f2.lock);
        if(f2.plist.size()!=f2.getCapacity()){
            for(int i=0;i<f1.plist.size();i++){
                if(f1.plist.get(i).getId()==id){
                    Passenger p=f1.plist.remove(i);
                    CCM.AcquireLock(1,p.lock);
                    f2.plist.add(p);
                    p.flist.remove(f1);
                    p.flist.add(f2);
                    CCM.ReleaseLock(1,p.lock);
                }
            }
        }
        CCM.ReleaseLock(1,f1.lock);
        CCM.ReleaseLock(1,f2.lock);
    }

    @Override
    public void run(){
        Random rand = new Random();
        for(int i=0;i<tasks;i++){
            int ub_flights = d.flights.size();
            int ub_passengers = d.passengers.size();
            int rflights = rand.nextInt(ub_flights);
            int rpassengers = rand.nextInt(ub_passengers);
            Flight f1 = d.flights.get(rflights);
            int chooser = rand.nextInt(5);

            if(chooser == 0){
                try{
                    Thread.sleep(10);
                }
                catch (Exception e){ }
                reserve(f1,rpassengers);
                System.out.println("Flight with id "+f1.getId()+" reserved for "+rpassengers);
            }
            else if(chooser == 1){
                try{
                    Thread.sleep(10);
                }
                catch (Exception e){ }
                if(d.passengers.get(rpassengers).flist.size()!=0){
                    int rv = rand.nextInt(d.passengers.get(rpassengers).flist.size());
                    Flight tc = d.passengers.get(rpassengers).flist.get(rv);
                    cancel(tc,rpassengers);
                    System.out.println("Flight with id "+tc.getId()+" canceled for "+rpassengers);
                }
                else{
                    System.out.println("No cancellation");
                }
            }
            else if(chooser == 2){
                try{
                    Thread.sleep(10);
                }
                catch (Exception e){ }
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
            }
            else if(chooser == 3){
                try{
                    Thread.sleep(10);
                }
                catch (Exception e){ }
                int s=Total_reservations();
                System.out.println("Total reservations- "+s);
            }
            else if(chooser == 4){
                int rflight2 = rand.nextInt(ub_flights);
                Flight f2 = d.flights.get(rflight2);
                try{
                    Thread.sleep(10);
                }
                catch (Exception e){ }
                transfer(f1,f2,rpassengers);
                System.out.println("Transfer of "+rpassengers+" from flight"+f1.getId()+" to "+f2.getId());
            }
        }

    }


}
