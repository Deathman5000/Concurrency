/*
  Program: DiningPhilosopher6_12.java
  Authors: James Hund, TJ Moore
  Version: 04/22/19
  Description: This program is an implementation of Algorithm 6.12 for the
               "Dining Philosophers Problem". It takes as arguments the number
               of rounds and number of philosophers in that order.
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
public class DiningPhilosopher6_12 {
  public static void main(String[] args) {
    // initializes number of rounds and philosophers
    int rounds = Integer.parseInt(args[0]);
    int n = Integer.parseInt(args[1]);
    // Creating and initializing forks
    Fork[] fork = new Fork[n];
    for(int i = 0; i < fork.length; i++){
      fork[i] = new Fork("F: " + i);	 	 
    }
    // Creating and initializing Philosophers
    Philosopher[] philosophers = new Philosopher[n];
    for(int i=0;i<n-1;i++){
      philosophers[i] = new Philosopher("P: "+i+" -", fork[i], fork[i+1], rounds);
    }
    // nth Philosopher grabs from the opposite side first
    philosophers[n-1] = new Philosopher("P: "+(n-1)+" -",fork[0],fork[n-1],rounds);
    for(int i=0;i<philosophers.length;i++){
      log.record("Philosopher "+i+" has entered the battle");
      Thread t= new Thread( philosophers[i]);
      t.start();
      }
  }
}

// fork class
class Fork{
  public Semaphore fork = new Semaphore(1);
  public String name;

  public Fork(String fname){
    this.name = fname;
  }

  // to get fork
  public boolean take(String s) {
    try{
        fork.acquire();
        // for output
        log.record("Fork "+name+" is being used by "+s);
        return true;
      }
      catch(Exception e){return false;}
  }

  // to release fork
  public void release(String s) {
    // for output
    log.record("Fork "+name+" is being released by "+s);
    fork.release();
  }
}
  
class Philosopher extends Thread {
  private Fork First;
  private Fork Second;
  private String name;
  private int round;

  // the init of Philosopher
  public Philosopher(String name, Fork left, Fork right, int rounds) {
    this.name = name;
    Second = left;
    First = right;
    round = rounds; 
  }

  // starts the Philosopher
  public void run(){
    for(int i=0; i<=round; i++){
      think();
      if(First.take(name)){
        if(Second.take(name)){
          eat();
          First.release(name);
          Second.release(name);
        }
      }
    }
  }

  // think method
  public void think(){
    // for output
    log.record(name + " think");
    try {
      sleep((int)(Math.random() * 100));
    }
    catch(InterruptedException e){
    }
  }

  // eat method
  public void eat(){
    // for output
    log.record(name + " eat");
    try {
      sleep((int)(Math.random() * 100));
    }
    catch(InterruptedException e){
    }
  }
}
// prints output to command line
class log{
  public static void record(String msg){
    System.out.println(msg);
  }
}
