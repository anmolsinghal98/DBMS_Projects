package Assignment2;

import java.util.ArrayList;

public class Transaction {

    Database d;

    Transaction(Database d){
        this.d=d;
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
        
    }



}
