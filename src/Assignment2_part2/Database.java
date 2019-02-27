package Assignment2_part2;


import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    ArrayList<Flight> flights;
    ArrayList<Passenger> passengers;

    Database(){
        flights=new ArrayList<>();
        passengers=new ArrayList<>();
    }

    void print_contents(){
        System.out.println("Flight Details-");
        for(int i=0;i<flights.size();i++){
            System.out.println("Flight ID: "+flights.get(i).getId()+ " , Flight Capacity: "+flights.get(i).getCapacity()+" , Passenger Count: "+flights.get(i).plist.size());
        }
        System.out.println();
        System.out.println("Passenger Details-");
        for(int i=0;i<passengers.size();i++){
            System.out.println("Passenger ID: "+passengers.get(i).getId());
            for(int j=0;j<passengers.get(i).flist.size();j++){
                System.out.println("Reserved flight ID: "+passengers.get(i).flist.get(j).getId());
            }
        }
    }
}

class Flight{
    private int id;
    ArrayList<Passenger> plist;
    Lock lock;
    private int capacity;

    Flight(int id,int capacity){
        this.id = id;
        this.capacity = capacity;
        plist = new ArrayList<>();
        lock = new Lock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getCapacity() {
        return capacity;
    }

}

class Passenger{
    private int id;
    ArrayList<Flight> flist;
    Lock lock;
    Passenger(int id){
        this.id = id;
        flist = new ArrayList<>();
        lock = new Lock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}


