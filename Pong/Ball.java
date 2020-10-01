import java.awt.*;
import java.util.*;

public class Ball extends Rectangle {

    static int xPosition;
    static int yPosition;
    static int radius;

    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;

    Ball(int x, int y, int width, int height) {

        super(x, y, width, height);

        xPosition = x;
        yPosition = y;
        radius = (width / 2);
        random = new Random();

        int randomXDirection = random.nextInt(2);


        if (randomXDirection == 0) {

            randomXDirection--;
        }

        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);


        if (randomYDirection == 0) {

            randomYDirection--;
        }

        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(int randomXDirection) {

        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {

        yVelocity = randomYDirection;
    }

    public void move() {

        x += xVelocity;
        y += yVelocity;
        xPosition = x;
        yPosition = y;
    }

    public void draw(Graphics g) {

        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }
}