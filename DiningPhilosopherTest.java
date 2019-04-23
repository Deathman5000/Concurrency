/*
  Program: DiningPhilosopherTest.java
  Authors: James Hund, TJ Moore
  Version: 04/22/19
  Description: This program is an implementation of Algorithm 6.12 for the
               "Dining Philosophers Problem". It takes as arguments the number
               of rounds and number of philosophers in that order.
 */
public class DiningPhilosopherTest {
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
    philosophers[n-1] = new Philosopher("P: 4 - ",fork[0],fork[n-1],rounds);
    for(int i=0;i<philosophers.length;i++){
      log.record("Philosopher "+i+" has entered the battle");
      Thread t= new Thread( philosophers[i]);
      t.start();
      }
  }
}

// fork class
class Fork{
  public boolean used;
  public String name;

  public Fork(String fname){
    this.name = fname;
  }

  public synchronized void take() {
    // for output
    log.record("Fork "+name+" is being used");
    this.used = true;
  }

  public synchronized void release() {
    // for output
    log.record("Fork "+name+" is being released");
    this.used = false;
  }
}
  
class Philosopher extends Thread {
  private Fork First;
  private Fork Second;
  private boolean hasFork = false;
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

      // looks for first fork
      if(! First.used) {
        First.take();
        hasFork=true;
      }
      // looks to see if second fork is free and if first fork is already taken by this instance
      if(! Second.used && hasFork) {
        Second.take();
        eat();
        // releasing the forks
        First.release();
        Second.release();
        hasFork=false;
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
