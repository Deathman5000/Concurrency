/*
  Program: DiningPhilosopher6_20.java
  Authors: James Hund, TJ Moore
  Version: 04/22/19
  Description: This program is an implementation of Algorithm 6.10 for the
               "Dining Philosophers Problem". It takes as arguments the number
               of rounds and number of philosophers in that order.
 */
public class DiningPhilosopher6_10{
  //This takes in the arguments.
  public static void main(String[] args) {
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
    for(int i=0;i<philosophers.length;i++){
      log.record("Philosopher "+i+" has entered the battle");
      Thread t= new Thread( philosophers[i]);
      t.start();
      }
  }
}

//This class defines what a fork is.
class Fork{
  public boolean used;
  public String name;

  public Fork(){
  }
  public Fork(String fname){
    this.name = fname;
  }

  public synchronized void take() {
    log.record("Fork "+name+" is being used");
    this.used = true;
  }

  public synchronized void release() {
    log.record("Fork "+name+" is being released");
    this.used = false;
  }
}
//Defines what a philosopher is and gives each Philosopher a different thread.
class Philosopher extends Thread {
  private Fork First;
  private Fork Second;
  private boolean hasFork = false;
  private String name;
  private int round;
 // This defines the philosopher and gives them arguments of name, forks, and rounds
  public Philosopher(String name, Fork left, Fork right, int rounds) {
    this.name = name;
    Second = left;
    First = right;
    round = rounds;
  }
  //Defines the think method
  public void think() {
    // sends the think command to a log for later output
    log.record(name + " think");
    // tries to a philosopher to think for a random amount of time.
    try {
      sleep((int)(Math.random() * 100));
    }
    catch(InterruptedException e){
    }
  }
 //Starts the process for each philosopher
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
  // defines the eat method.
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
//prints out the log of all that is done in the program.
class log{
  public static void record(String msg){
    System.out.println(msg);
  }
}
