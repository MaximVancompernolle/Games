import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class Paddle extends Rectangle implements Runnable {

    int id;
    int yVelocity;
    int speed = 10;

    Thread gameThread;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {

        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;

        // if (id == 1) {

            gameThread = new Thread(this);
            gameThread.start();
        // }
    }

    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 120.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (true) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {

                if (this.id == 1) {

                    if (((y + (GamePanel.PADDLE_HEIGHT / 2)) > ((Ball.yPosition + Ball.radius) + 20)) && (Ball.xPosition < GamePanel.GAME_WIDTH / 2)) {

                        setYDirection(-speed / 2);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }

                     else if (((y + (GamePanel.PADDLE_HEIGHT / 2)) < ((Ball.yPosition + Ball.radius) - 20)) && (Ball.xPosition < GamePanel.GAME_WIDTH / 2)) {

                        setYDirection(speed / 2);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }

                    else {

                        setYDirection(0);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }
                }

                if (this.id == 2) {

                    if (((y + (GamePanel.PADDLE_HEIGHT / 2)) > ((Ball.yPosition + Ball.radius) + 20)) && (Ball.xPosition > GamePanel.GAME_WIDTH / 2)) {

                        setYDirection(-speed / 2);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }

                    else if (((y + (GamePanel.PADDLE_HEIGHT / 2)) < ((Ball.yPosition + Ball.radius) - 20)) && (Ball.xPosition > GamePanel.GAME_WIDTH / 2)) {

                        setYDirection(speed / 2);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }

                    else {

                        setYDirection(0);
                        move();
                        Toolkit.getDefaultToolkit().sync();
                    }
                }

                delta--;
            }
        }
    }

    public void keyPressed(KeyEvent e) {

        // switch (id) {

        //     case 1:

        //         if (e.getKeyCode() == KeyEvent.VK_W) {

        //             setYDirection(-speed);
        //         }

        //         if (e.getKeyCode() == KeyEvent.VK_S) {

        //             setYDirection(speed);
        //         }

        //         break;

        //     case 2:

        //         if (e.getKeyCode() == KeyEvent.VK_UP) {

        //             setYDirection(-speed);
        //         }

        //         if (e.getKeyCode() == KeyEvent.VK_DOWN) {

        //             setYDirection(speed);
        //         }

        //         break;
        // }
    }

    public void keyReleased(KeyEvent e) {

        // switch (id) {

        //     case 1:

        //         if (e.getKeyCode() == KeyEvent.VK_W) {

        //             setYDirection(0);
        //         }

        //         if (e.getKeyCode() == KeyEvent.VK_S) {

        //             setYDirection(0);
        //         }

        //         break;

        //     case 2:

        //         if (e.getKeyCode() == KeyEvent.VK_UP) {

        //             setYDirection(0);
        //         }

        //         if (e.getKeyCode() == KeyEvent.VK_DOWN) {

        //             setYDirection(0);
        //         }

        //         break;
        // }
    }

    public void setYDirection(int yDirection) {

        yVelocity = yDirection;
    }

    public void move() {

        y = y + yVelocity;
    }

    public void draw(Graphics g) {

        if (id == 1) {

            g.setColor(Color.blue);
        } else {

            g.setColor(Color.red);
        }

        g.fillRect(x, y, width, height);
    }
}