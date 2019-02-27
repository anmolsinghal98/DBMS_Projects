package Assignment2;


class Lock{
    volatile int value;
    Lock(){
        value=0;
    }
    synchronized void acquire_share_lock(){
        value=2;
    }
    synchronized void acquire_exclusive_lock(){
        value=1;
    }
    synchronized void unlock(){
        value=0;
    }
}


public class ConcurrencyControl {

    Lock lock;

    ConcurrencyControl(Lock theLock){
        lock = theLock;
    }

    public synchronized void AcquireLock(int i) {
        try{
            if(i == 1){
                while(lock.value!=0){
                    wait(100);
                }
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

    public synchronized void ReleaseLock(int i){
        lock.unlock();
    }

}
