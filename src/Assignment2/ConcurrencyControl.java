package Assignment2;

class Lock{
    volatile int value;
    Lock(int value){
        this.value=value;
    }
    synchronized void shared_lock(){
        this.value=1;
    }
    synchronized void exclusive_lock(){
        this.value=2;
    }
    synchronized void unlock(){
        this.value=0;
    }

}

public class ConcurrencyControl implements Runnable{

    Lock lock;
    ConcurrencyControl(){
        lock.unlock();
    }


    @Override
    public void run() {

    }
}
