/**
 * // updated Galdón 2015
 */

package concurrent_assignment3.A2;


import java.awt.*;
import java.applet.*;



class CarParkCanvas extends Canvas {
    String title;
    int slots;
    int spaces;
    boolean occupied[];
    Applet applet;
    Image  car;

    Font f1 = new Font("Helvetica",Font.ITALIC+Font.BOLD,24);
    Font f2 = new Font("TimesRoman",Font.BOLD,36);

    CarParkCanvas(String title, int slots, Applet applet) {
        super();
        this.title=title;
        this.slots=slots;
        spaces = slots;
        this.applet = applet;
        this.occupied = new boolean[slots];
        for (int i=0; i<slots; i++) occupied[i] = false;
        setSize(20+50*slots,150);
        setBackground(Color.cyan);
        MediaTracker mt;
        mt = new MediaTracker(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();


        car = toolkit.getImage("image/car.gif");
        try {
            mt.waitForID(0);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load car image");
        }
        
  	}

    public void setvalue(int spaces, boolean occupied[]){
        this.spaces = spaces;
        this.occupied = occupied;
        repaint();
    }

    public void paint(Graphics g) {
        update(g);
    }

    Image offscreen;
    Dimension offscreensize;
    Graphics offgraphics;

    public synchronized void update(Graphics g){
        Dimension d = getSize();
	    if ((offscreen == null) || (d.width != offscreensize.width)
	                            || (d.height != offscreensize.height)) {
	        offscreen = createImage(d.width, d.height);
	        offscreensize = d;
	        offgraphics = offscreen.getGraphics();
	        offgraphics.setFont(getFont());
	    }

	    offgraphics.setColor(getBackground());
	    offgraphics.fillRect(0, 0, d.width, d.height);
            

         // Display the title
        offgraphics.setColor(Color.black);
        offgraphics.setFont(f1);
        FontMetrics fm = offgraphics.getFontMetrics();
        int w = fm.stringWidth(title);
        int h = fm.getHeight();
        int x = (getSize().width - w)/2;
        int y = h;
        offgraphics.drawString(title, x, y);
        offgraphics.drawLine(x,y+3,x+w,y+3);
        // CarPark Places
        y = h+10;
        offgraphics.setColor(Color.white);
        offgraphics.fillRect(10,y,50*slots,100);
        offgraphics.setColor(Color.black);
        for(int i=0; i<slots; i++) {
            offgraphics.drawRect(10+50*i,y,50,100);
        }
        offgraphics.setColor(Color.white);
        for(int i=1; i<slots; i++) {
            offgraphics.drawLine(10+50*i,y+60,10+50*i,y+99);
        }
        //arrival gate
        if (spaces==0)
             offgraphics.setColor(Color.red);
        else
             offgraphics.setColor(Color.white);
        offgraphics.fillRect(8,y+60,5,39);
        //departure gate
        if (spaces==slots)
             offgraphics.setColor(Color.red);
        else
             offgraphics.setColor(Color.white);
        offgraphics.fillRect(8+50*slots,y+60,5,39);
        
        //Display Cars
        offgraphics.setColor(Color.blue);
        for (int i=0; i<slots; i++) {
          if (occupied[i])
           offgraphics.drawImage(car,15+50*i,y+5,this);
        }
        g.drawImage(offscreen, 0, 0, null);
      
    }
}
