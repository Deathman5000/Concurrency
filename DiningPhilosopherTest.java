public class DiningPhilosopherTest {
  public static void main(String[] args) {
    int rounds = Integer.parseInt(args[0]);
    int n = Integer.parseInt(args[1]);
    Fork[] fork = new Fork[n];
    for(int i = 0; i < fork.length; i++){
      fork[i] = new Fork("F: " + i);	 	 
    }
    Philosopher[] philosophers = new Philosopher[n];
    for(int i=0;i<n-1;i++){
      philosophers[i] = new Philosopher("P: "+i+" -", fork[i], fork[i+1], rounds);
    }
    philosophers[n-1] = new Philosopher("P: 4 - ",fork[0],fork[n-1],rounds);
    for(int i=0;i<philosophers.length;i++){
      log.record("Philosopher "+i+" has entered the battle");
      Thread t= new Thread( philosophers[i]);
      t.start();
      }
  }
}

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
  
class Philosopher extends Thread {
  private Fork First;
  private Fork Second;
  private String name;
  private int state;
  private int round;

  public Philosopher(String name, Fork left, Fork right, int rounds) {
    this.state = 1;
    this.name = name;
    Second = left;
    First = right;
    round = rounds; 
  }

  public void run(){
    for(int i=0; i<=round; i++){
      eat();
    }
  }

  public void think() {
    this.state = 1;
    log.record(name + " think");
    try {
      sleep((int)(Math.random() * 100));
    }
    catch(InterruptedException e){
    }
  }

  public void eat(){
    if(! Second.used){
      Second.take();
      First.take();
      log.record(name + " eat");
      try {
        sleep((int)(Math.random() * 100));
      }
      catch(InterruptedException e){
      }
      First.release();
      Second.release();
    }
    think();
  }
}
	
class log{
  public static void record(String msg){
    System.out.println(msg);
  }
}
