/**
 * //adapted by miguel.galdon@uclm.es
 */
package concurrent_assignment3.A1;

/**
 * You can only park if there is at least 1 free slot.
 * You can only leave if there is there is at least 1 parked car.
 * Create and use semaphores as appropriate.
 * @author Pablo.Bermejo@uclm.es
 */
import java.util.concurrent.Semaphore;


class CarParkControl {

    protected int spaces;
    protected int capacity;
    NumberCanvas cont_;
    Counter counter;
    Semaphore dispatch;//Semaphore declaration, class attribute
    

    CarParkControl(int n) {
        capacity = spaces = n;
        
    }
    
    CarParkControl(int n,NumberCanvas cont) {
        cont_ = cont;
        capacity = spaces = n;
        counter = new Counter(cont_);
        counter.show(spaces);
        dispatch = new Semaphore(n);//initialize within constructor
    }

    void arrive() throws InterruptedException {
        System.out.print("Available permits "+dispatch.availablePermits());
        --spaces;
        counter.show(spaces);    
    }

    void leaves() throws InterruptedException{
        System.out.println("Available permits "+dispatch.availablePermits());
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
          this.carpark.dispatch.acquire();//if permits are available, then enters and decrements
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
          if(this.carpark.counter.value<4){//do this only when there is a car inside
            ThreadPanel.rotate(340);//I mean, free space is less than capacity, which is 4
            this.carpark.dispatch.release();//releases 1 permit, 1 vacancy more available in parking
            carpark.leaves();
            ThreadPanel.rotate(20);  
          }
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