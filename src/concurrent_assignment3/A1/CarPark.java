/*
@author  j.n.magee 

//adapted by miguel.galdon@uclm.es 2015
 *            pablo.bermejo@uclm.es 2018
*/
package concurrent_assignment3.A1;


import java.awt.*;
import java.applet.*;

/**
 * 
 */


/****************************APPLET**************************/

public class CarPark extends Applet {

    final static int Places = 4; 

    ThreadPanel arrivals;
    ThreadPanel departures;
    CarParkCanvas carDisplay;
    NumberCanvas numDisplay;

    public void init() {
        super.init();
        super.resize(1000,400);
         // Set up Display
        arrivals = new ThreadPanel(" Arrivals ",Color.blue);
        departures = new ThreadPanel("Departures",Color.yellow);
        carDisplay = new CarParkCanvas("Car Park",Places,this);
        numDisplay = new NumberCanvas("  Free  ",Color.ORANGE);
        numDisplay.setSize(150,150);
        
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTH;
        
        gridbag.setConstraints(carDisplay, gc);
        gridbag.setConstraints(arrivals, gc);
        gridbag.setConstraints(departures, gc);
        gridbag.setConstraints(numDisplay, gc);
        add(arrivals);
        add(numDisplay);
        add(departures);
        
        add(carDisplay);
        
        
		setBackground(Color.lightGray);
    }

    public void start() {
        CarParkControl c = new DisplayCarPark(carDisplay,Places,numDisplay);
        arrivals.start(new Arriving(c));
        departures.start(new Leaving(c));
    }


    public void stop() {
        arrivals.stop();
        departures.stop();
    }

}





