import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.*;
import java.util.ArrayList;


class TrajectorySolution extends JComponent implements ActionListener {
        /*public void paint(Graphics g){
            Dimension d = getSize();
            int dx = d.width / 2 , dy = d.height / 2 ;
    //        int dx = 0, dy = 0;
            g.TrajectorySolution(0,dy,d.width,dy); g.TrajectorySolution(dx,0,dx,d.height);

            Rku rku=new Rku(0f,1f, 0.001f, 0.5f);
            int i=0;
            int m=50;
            while (i<100000){

                System.out.println(rku.getParameters(0) + " " + rku.getParameters(1)+ ' ' + (Math.round(rku.getParameters(0) * m)+dx)+' '+(-Math.round(rku.getParameters(1) * m)+dy));

                g.drawOval(Math.round(rku.getParameters(0) * m)+dx, -Math.round(rku.getParameters(1) * m)+dy,1,1);

    //            g.TrajectorySolution(Math.round(f1 * m)+dx, -Math.round(f2 * m) +dy, Math.round(f3 * m)+dx, -Math.round(f4 * m)+dy);
                rku.toStep();
                i++;
            }
        }*/

    private double scale = 10;
    private Color color;
    private Timer timer;
    private int width = 500;
    private int height = 500;
    private int dx = width / 2;
    private int dy = height / 2;
    private double x = dx;
    private double y = dy;
    private double maxX, maxY;
    private double minX, minY;
    private double pdx, pdy;
    private Rku rku;
    private ArrayList<Point2D.Double> list = new ArrayList<Point2D.Double>();
    private boolean joinLine;

    public TrajectorySolution(Color color, int delay, Rku a, boolean joinLine) {
        timer = new Timer(delay, this);
        this.color = color;
        rku = a;
        this.joinLine = joinLine;

        maxX = rku.getParameters(0);
        minX = rku.getParameters(0);
        maxY = rku.getParameters(1);
        minY = rku.getParameters(1);
        pdx = (maxX + minX) / 2;
        pdy = (maxY + minY) / 2;

        setPreferredSize(new Dimension(width, height));
    }

    void clear() {
        list.clear();
        maxX = rku.getParameters(0);
        minX = rku.getParameters(0);
        maxY = rku.getParameters(1);
        minY = rku.getParameters(1);
        pdx = (maxX + minX) / 2;
        pdy = (maxY + minY) / 2;
    }

    private Point2D.Double toSystem(Point2D.Double p) {
//        rku.getParameters(0) * scale + dx, -rku.getParameters(1) * scale + dy
        return new Point2D.Double((p.getX() - pdx) * scale + dx, -(p.getY() - pdy) * scale + dy);
    }

   /* private void dontknow(){
        if (rku.getParameters(0) < minX)
            minX = rku.getParameters(0);
        if (rku.getParameters(0) > maxX)
            maxX = rku.getParameters(0);
        if (rku.getParameters(1) < minY)
            minX = rku.getParameters(0);
        if (rku.getParameters(0) > maxY)
            maxY = rku.getParameters(0);
        pdx = (maxX + minX)/2;
        pdy = (maxY + minY)/2;
    }*/

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.TrajectorySolution(0, dy, width, dx);
//        g2d.TrajectorySolution(dx, 0, dy, height);
//        g2d.TrajectorySolution(0,0,250,250);
//        g2d.scale(scale, scale);
//        Ellipse2D.Double el = new Ellipse2D.Double (0, 0, 1, 1);
//        g2d.fill(el);

        Line2D.Double line;

        g2d.setColor(Color.green);
        Point2D.Double p = toSystem(new Point2D.Double(0,0));
        if (p.getX()<0 || p.getX()>width-1)
            p.setLocation(p.getX()<0?0:width-1,p.getY());
        if (p.getY()<0 || p.getY()>height-1)
            p.setLocation(p.getX(),p.getY()<0?0:height-1);
        line = new Line2D.Double(0, p.getY(),width-1, p.getY());
        g2d.draw(line);
        line = new Line2D.Double(p.getX(), 0,p.getX(), height-1);
        g2d.draw(line);

        g2d.setColor(color);
        for (int i = 0; i < list.size()-1; i++) {
            if (joinLine)
                line = new Line2D.Double(toSystem(list.get(i)), toSystem(list.get(i+1)));
            else
                line = new Line2D.Double(toSystem(list.get(i)), toSystem(list.get(i)));
            g2d.draw(line);
        }
        if (list.size()>0) {
            line = new Line2D.Double(toSystem(list.get(list.size()-1)), toSystem(list.get(list.size()-1)));
            g2d.draw(line);
            line = null;
        }

        if (timer.isRunning()) {
            if (rku.getParameters(0) < minX) {
                minX = rku.getParameters(0);
            }
            if (rku.getParameters(0) > maxX) {
                maxX = rku.getParameters(0);
            }
            if (rku.getParameters(1) < minY) {
                minY = rku.getParameters(1);
            }
            if (rku.getParameters(1) > maxY) {
                maxY = rku.getParameters(1);
            }
            pdx = (maxX + minX) / 2;
            pdy = (maxY + minY) / 2;

            if (Math.abs(maxX - minX) > Math.abs(maxY - minY)) {
                scale = 480 / Math.abs(maxX - minX);
            } else {
                scale = 480 / Math.abs(maxY - minY);
            }

            list.add(new Point2D.Double(rku.getParameters(0), rku.getParameters(1)));
            rku.toStep();

        }

//        Rectangle2D.Double rec = new Rectangle2D.Double((minX-pdx)*scale+dx-2, -(maxY-pdy)*scale+dy-2, Math.abs(maxX-minX)*scale+4, Math.abs(maxY-minY)*scale+4);
//        g2d.draw(rec);
//        el.y=rku.getParameters(1);

        /*if (timer.isRunning())
        {
            int i = 0;
            while (i < 10000)
            {
                Line2D.Double p = new Line2D.Double(rku.getParameters(0) * scale + dx, -rku.getParameters(1) * scale + dy, rku.getParameters(0) * scale + dx, -rku.getParameters(1) * scale + dy);
                g2d.draw(p);
                rku.toStep();
                i++;
            }
            //g2d.drawOval(Math.round(rku.getParameters(0))+dx, -Math.round(rku.getParameters(1))+dy,1,1);
            timer.stop();
        }*/
    }
}
