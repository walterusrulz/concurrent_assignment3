/**
 * //adapted by miguel.galdon@uclm.es
 */
package concurrent_assignment3.A2;


import java.util.concurrent.Semaphore;

/**
 * You can only park if there is at least 1 free slot.
 * You can only leave if there is there is at least 1 parked car.
 * Create and use counters, and use signals as appropiate.
 * @author Pablo.Bermejo@uclm.es
 */

class CarParkControl {

    protected int spaces;
    protected int capacity;
    NumberCanvas cont_;
    Counter counter;
    boolean flag;//attribute flag
    

    CarParkControl(int n) {
        capacity = spaces = n;
        
    }
    
    CarParkControl(int n,NumberCanvas cont) {
        cont_ = cont;
        capacity = spaces = n;
        counter = new Counter(cont_);
        counter.show(spaces);
        flag=false;//added flag to constructor
    }
    //Just synching methods is enough, although threads that should not be running
    //keep trying to get processor time and are rejected till coditions are fullfilled.
    //Maybe it would be better to block a thread we know won't meet conditions.     

    synchronized void arrive() throws InterruptedException {//sync for 1 
        if(this.counter.value>0){
           --spaces;
           counter.show(spaces); 
           //below we are actively unblocking departures if we spot 0 cars
           //given we just parked one, we notify leaving, which is waiting
           //for a car to remove, signalling its intent with FLAG
           /*if(flag){
               this.notify();
           }*/
        }
    }

    synchronized void leaves() throws InterruptedException{
        if(this.counter.value<4){
           ++spaces;
           counter.show(spaces); 
           //below we block departures when NO cars are available
           //also signal our intention with flag, so that when a new car
           //arrives, the other thread will unblock us
        }/*else{
            flag=true;
            this.wait();
        }*/
     }
}


/********************LLEGADAS*******************************/
class Arriving implements Runnable {

    CarParkControl carpark;
    NumberCanvas display;

    Arriving(CarParkControl c) {
        carpark = c;
    }
    Arriving(CarParkControl c, NumberCanvas n) {
        carpark = c;
        display = n;
    }
    public void run() {
      try {
        while(true) {
          ThreadPanel.rotate(340); 
           carpark.arrive();
          ThreadPanel.rotate(20);           
        }
      } catch (InterruptedException e){}
    }
}

/********************SALIDAS*******************************/

class Leaving implements Runnable {

    CarParkControl carpark;
    NumberCanvas display;

    Leaving(CarParkControl c) {
        carpark = c;
    }
    
    public void run() {
      try {
        while(true) {
          ThreadPanel.rotate(20);
          carpark.leaves();
          ThreadPanel.rotate(340);        
        }
      } catch (InterruptedException e){}
    }
}
/********************COUNTER*******************************/

 class Counter {

    volatile int value=0;
    NumberCanvas display;

   
    Counter(NumberCanvas n) {
        display=n;
    }
    
    void show() {
       display.setvalue(value);   

    }
    
    void show(int n) {
        value = n;
        display.setvalue(value);   
    }
}