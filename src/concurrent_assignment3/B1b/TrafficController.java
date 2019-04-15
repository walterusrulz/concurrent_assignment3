package concurrent_assignment3.B1b;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Remember to move the 'cars_images' folder to the root directory
 * of your project,
 * or write the absolute path to the folder in lines 23,35,27
 * in CarWorld.java. 
 * 
 * Use signals to create a safe bridge (only 1 car on the bridge at 
 * the same time)
 * 
 * Pablo.Bermejo@uclm.es
 *
 */
public class TrafficController {
     volatile boolean flag;
     
      TrafficController(boolean cond) {
         this.flag=cond;
    }
    
    synchronized public void redEnters() {
        while(this.flag){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        flag=true;
    }

    synchronized public  void blueEnters() {
	while(this.flag){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        flag=true;
    }

    synchronized public  void blueExits() {
    	this.flag= false;
        this.notify();
    	 
    }

    synchronized public  void redExits() {
        this.flag= false;
        this.notify();
	

    }

}