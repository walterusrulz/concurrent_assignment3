package concurrent_assignment3.B1a;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Remember to move the 'cars_images' folder to the root directory
 * of your project,
 * or write the absolute path to the folder in lines 23,35,27
 * in CarWorld.java. 
 * 
 * Use Semaphores to create a safe bridge (only 1 car on the bridge at 
 * the same time)
 * 
 * Pablo.Bermejo@uclm.es
 *
 */

public class TrafficController {

     Semaphore traffic;
    
     TrafficController(int permission) {
         traffic = new Semaphore(permission);
    }
     
    
    public void redEnters() {
         try {
             this.traffic.acquire();
         } catch (InterruptedException ex) {
             Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public  void blueEnters() {
	 try {
             this.traffic.acquire();
         } catch (InterruptedException ex) {
             Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

     public  void blueExits() {
    	this.traffic.release();
    	 
    }

    public  void redExits() {
        this.traffic.release();
	

    }

}