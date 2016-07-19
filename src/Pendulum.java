
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

class Pendulum extends JComponent implements ActionListener
{
    private double scale=125;
    private double angle;
    private Color color;
    private Timer timer;
    private int width = 500;
    private int height = 500;
    private int dx = width / 2;
    private int dy = height / 2;
    private double m = 0;
    private double l = 0;
    Rku rku;

    public Pendulum(Color color, int delay, Rku a)
    {
        angle = Math.PI / 2;
        timer = new Timer(delay, this);
        this.color = color;
        rku=a;

        setPreferredSize(new Dimension(width, height));
    }

    public void start()
    {
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawLine(0, dy, width, dx);
        g2d.drawLine(dx, 0, dy, height);
        g2d.setColor(color);
//        g2d.drawOval(125, 125, 250, 250);
        angle = rku.getParameters(0);
        if (timer.isRunning())
        {
            rku.toStep();
            angle = rku.getParameters(0);
        }
        double dobx = Math.cos(angle-Math.PI/2) * scale*rku.getParameters(4) + dx;
        double doby = -Math.sin(angle-Math.PI/2) * scale*rku.getParameters(4) + dy;
//        System.out.println(scale+" "+dobx + " " + doby);
        Line2D.Double line = new Line2D.Double(dx + scale*rku.suspensionX(), dy + scale*rku.suspensionY(), dobx + scale*rku.suspensionX(), doby + scale*rku.suspensionY());
        Ellipse2D.Double el = new Ellipse2D.Double(dobx + scale*rku.suspensionX() -scale/20*rku.getParameters(3), doby + scale*rku.suspensionY() -scale/20*rku.getParameters(3), scale/10*rku.getParameters(3), scale/10*rku.getParameters(3));
        g2d.draw(line);
        g2d.fill(el);


    }
}
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Pendulum");
                JPanel panel = new JPanel();
                final Pendulum maytnik = new Pendulum(Color.magenta, 1);
                panel.add(maytnik);
                frame.getContentPane().add(panel);
                final JButton buttonStartStop = new JButton("Start");
                buttonStartStop.setPreferredSize(new Dimension(70,26));
                buttonStartStop.addActionListener(new ActionListener() {
                    private boolean pulsing = false;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (pulsing) {
                            pulsing = false;
                            maytnik.stop();
                            buttonStartStop.setText("Start");
                        }
                        else
                        {
                            pulsing = true;
                            maytnik.start();
                            buttonStartStop.setText("Stop");
                        }
                    }
                });
                panel.add(buttonStartStop);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(550,600);
                frame.setVisible(true);
            }
        });
    }
*/


