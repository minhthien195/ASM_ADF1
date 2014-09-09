ASM_ADF1
========
package EX;

/**
 *
 * @author MrT
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import static java.lang.System.exit;
import static java.lang.Thread.State.TERMINATED;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Chim extends Frame implements Runnable, MouseListener {

    public static final int width_panel = 320;
    public static final int height_panel = 480;

    private final JPanel panel;
    private JFrame f;

    public boolean check = true;
    public boolean running = false;
    // width and height pipes , bird
    protected int width = 54, height = 400;
    protected int d = 24;
    protected int x1 = -30, y1 = -100;
    protected int x2 = 130, y2 = 440;
    protected int soccer = 0;
    protected int fg = 0;
    // x , y of pipes and bird
    protected int x_ = 30, y_ = 100;// bird
    protected int x_p1 = 600, y_pd1 = 300; // 
    protected int y_pu1 = y_pd1 - 400 - 100;

    protected int x_p2 = 800, y_pd2 = 300; // pipes2
    protected int y_pu2 = y_pd1 - 400 - 100;

    private final AffineTransform transform = new AffineTransform();
    private double direction;
    protected Image[] imgFrames;
    protected String[] imgFileNames = {
        "E:\\SEM2\\ADF1\\Image\\bird1.png", "E:\\SEM2\\ADF1\\Image\\bird2.png", "E:\\SEM2\\ADF1\\Image\\bird3.png"};
    protected int frameRate = 3;    // frame rate in frames per second
    protected int imgWidth, imgHeight;
    protected int currentFrame = 0;

    public Chim() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        // application listens to its own mouse events
        //frame.addMouseListener(this);
        frame.setSize(width_panel, height_panel);

        panel = new MyRectangleJPanel(); // changed this line
        panel.setSize(width_panel, height_panel);
        panel.addMouseListener(this);
        frame.add(panel);
        panel.validate();
        frame.setResizable(false);// because you added panel after setVisible was called
        // because you added panel after setVisible was called

    }

    private synchronized void start() {
        new Thread(this).start();

    }

    private synchronized void stop() {

    }

    @Override
    public void run() {
        update();

    }

    public void update() {
        while (check) {
            this.x_p2 -= 2;

            if (this.x_p1 == -54) {
                this.x_p1 = this.x_p2 + 200;
                this.y_pd1 = (int) (200 * Math.random() + 150);
                this.y_pu1 = this.y_pd1 - 100 - 400;
            }

            this.x_p1 -= 2;

            if (x_p2 == -54) {
                this.x_p2 = this.x_p1 + 200;
                this.y_pd2 = (int) (200 * Math.random() + 150);
                this.y_pu2 = this.y_pd2 - 100 - 400;
            }

            updateBird();

            if (this.x_p1 == -36 || this.x_p2 == -36) {
                this.soccer += 1;
            }
            
            check();

            if (check == true) {
                fg = (fg - 2) % 14;
            }

            if (check == false) {
                break;
            }

            panel.repaint();
            wait(20);
        }
    }

    public void check() {
        // ong 1
        Rectangle c = new Rectangle(this.x_, this.y_, this.d, this.d);
        Rectangle pd1 = new Rectangle(this.x_p1, this.y_pd1, this.width, this.height);
        Rectangle pu1 = new Rectangle(this.x_p1, this.y_pu1, this.width, this.height);

        //ong 2
        Rectangle pd2 = new Rectangle(this.x_p2, this.y_pd2, this.width, this.height);
        Rectangle pu2 = new Rectangle(this.x_p2, this.y_pu2, this.width, this.height);

        if (c.intersects(pd1)) {
            this.y_ = 366;
            this.x1 = 130;
            this.y1 = 250;
            this.check = false;
        } else if (c.intersects(pu1)) {
            this.y_ = 366;
            this.x1 = 130;
            this.y1 = 250;
            this.check = false;
        } else if (c.intersects(pd2)) {
            this.y_ = 366;
            this.x1 = 130;
            this.y1 = 250;
            this.check = false;
        } else if (c.intersects(pu2)) {
            this.y_ = 366;
            this.x1 = 130;
            this.y1 = 250;
            this.check = false;
        }
    }

    public void updateBird() {
        if (this.y_ >= 356) {
            this.check = false;
        }
        this.y_ += 1;
    }

    public void wait(int m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
        }

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (check == true) {
            this.y_ -= 40;
            panel.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    class MyRectangleJPanel extends JPanel {

        public void drawBird(Graphics g) {
            Image img[] = {Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\bird1.png"),
                Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\bird2.png"),
                Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\bird3.png")};
            imgWidth = img[0].getWidth(null);
            imgHeight = img[0].getHeight(null);
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            transform.setToIdentity();
            // The origin is initially set at the top-left corner of the image.
            // Move the center of the image to (x, y).
            transform.translate(x_, y_);
            // Rotate about the center of the image

            g2d.drawImage(img[currentFrame], transform, null);

        }

        public void drawPipeD1(Graphics g) {
            Image img1;
            img1 = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\pipedown.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img1, x_p1, y_pd1, this);
        }

        public void drawPipeU1(Graphics g) {
            Image img2;
            img2 = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\pipeup.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img2, x_p1, y_pu1, this);
        }

        public void drawPipeD2(Graphics g) {
            Image img3;
            img3 = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\pipedown.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img3, x_p2, y_pd2, this);
        }

        public void drawPipeU2(Graphics g) {
            Image img4;
            img4 = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\pipeup.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img4, x_p2, y_pu2, this);
        }

        public void drawFg1(Graphics g) {
            Image img;
            img = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\dat.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img, fg, 400, this);
        }

        public void drawFg2(Graphics g) {
            Image img;
            img = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\dat.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img, fg + 224, 400, this);
        }

        public void drawbg(Graphics g) {
            Image img;
            img = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\backgroud.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img, 0, 400 - 184, this);
        }

        public void drawbg1(Graphics g) {
            Image img;
            img = Toolkit.getDefaultToolkit().getImage("E:\\SEM2\\ADF1\\Image\\backgroud.png");
            Graphics2D g2d = (Graphics2D) g;
            //g2d.setColor(Color.green);
            g2d.drawImage(img, 276, 400 - 184, this);
        }

        @Override
        public void paint(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, width_panel, height_panel);

            drawbg(g2d);
            drawbg1(g2d);

            drawPipeD1(g2d);

            drawPipeU1(g2d);

            drawPipeD2(g2d);

            drawPipeU2(g2d);

            drawBird(g2d);

            drawFg1(g2d);

            drawFg2(g2d);

            int fontSize = 20;

            g2d.setFont(
                    new Font("TimesRoman", Font.PLAIN, fontSize));
            g2d.setColor(Color.red);

            g2d.drawString(
                    "Soccer : " + soccer, x2, y2);

            g2d.setFont(
                    new Font("TimesRoman", Font.PLAIN, 40));
            g2d.drawString(
                    "You Die =)) ! ", x1, y1);

        }
    }

    public static void main(String[] args) {
        new Chim().start();
    }
}
