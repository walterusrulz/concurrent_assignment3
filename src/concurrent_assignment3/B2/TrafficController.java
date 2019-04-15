package concurrent_assignment3.B2;

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
     boolean flag;
     int color;
     public static final int WHITE=0;
     public static final int RED=1;
     public static final int BLUE=2;
     
      TrafficController(boolean cond) {
         this.flag=cond;
         color=WHITE;
    }
    
    synchronized public void redEnters() {
        if(!this.flag||this.color==RED){
             flag=true;
             color=RED;
        }else{
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    synchronized public  void blueEnters() {
	if(!this.flag||this.color==BLUE){
            flag=true;
            color=BLUE;
        }else{
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TrafficController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    synchronized public  void blueExits() {
    	this.color=WHITE;
        /*if(this.color!=BLUE){
           this.flag= false; 
        }*/
        this.flag= false;
        this.notifyAll();
        
    }

    synchronized public  void redExits() {
        this.color=WHITE;
        /*if(this.color!=RED){
           this.flag= false; 
        }*/
        this.flag= false;
        this.notifyAll();
    }

}