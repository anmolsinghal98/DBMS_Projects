package Assignment2_part2;


import java.util.ArrayList;

class Lock{
    volatile int value;
    volatile int numholders;
    ArrayList<Thread> waiters;
    Lock(){
        value=0;
        numholders = 0;
        waiters = new ArrayList<>();
    }
    synchronized void acquire_share_lock(){
        value=2;
        numholders++;
    }
    synchronized void acquire_exclusive_lock(){
        value=1;
        numholders++;
    }
    synchronized void unlock(){
        value=0;
        numholders--;
    }
}


public class ConcurrencyControl {


    public synchronized void AcquireLock(int i,Lock lock) {
        try{
            if(i == 1){
                if(lock.value != 0 || lock.numholders != 0){
                    lock.waiters.add(Thread.currentThread());
                }
                while(lock.value!=0 && lock.numholders != 0){
                    wait(100);
                }
                lock.waiters.remove(Thread.currentThread());
                lock.acquire_exclusive_lock();
            }
            else if(i == 2){
                while(lock.value==1){
                    wait(100);
                }
                lock.acquire_share_lock();
            }
        }
        catch(Exception e){
            System.out.println("Time Out. Can't execute further");
        }
    }



    public synchronized void ReleaseLock(int i,Lock lock){
        lock.unlock();
    }

}
