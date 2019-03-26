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
    

    CarParkControl(int n) {
        capacity = spaces = n;
        
    }
    
    CarParkControl(int n,NumberCanvas cont) {
        cont_ = cont;
        capacity = spaces = n;
        counter = new Counter(cont_);
        counter.show(spaces);
    }

    void arrive() throws InterruptedException {
        --spaces;
        counter.show(spaces);    
    }

    void leaves() throws InterruptedException{
        ++spaces;
        counter.show(spaces);
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