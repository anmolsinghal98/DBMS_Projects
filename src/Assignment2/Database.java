package Assignment2;

import java.util.ArrayList;

public class Database {

    ArrayList<Flight> flights;
    ArrayList<Passenger> passengers;

    Database(){
        flights=new ArrayList<>();
        passengers=new ArrayList<>();
    }
}

class Flight{

    private int id;
    ArrayList<Passenger> plist;

    private int capacity;

    Flight(int id,int capacity){
        this.id=id;
        this.capacity=capacity;
        plist=new ArrayList<>();
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
    Passenger(int id){
        this.id=id;
        flist=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}


