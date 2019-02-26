package Assignment2;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class ConcurrencyControl implements Runnable{

    ReentrantReadWriteLock lock;

    ConcurrencyControl(ReentrantReadWriteLock theLock){
        lock = theLock;
    }



    @Override
    public void run() {
        Random rand = new Random();
        int random = rand.nextInt();
    }

    public void AcquireLock(int i){
        if(i == 1){
            lock.writeLock().lock();
        }
        else if(i == 2){
            lock.readLock().lock();
        }
    }

    public void ReleaseLock(int i){
        if(i == 1){
            lock.writeLock().unlock();
        }
        else if(i == 2){
            lock.readLock().unlock();
        }
    }

}
