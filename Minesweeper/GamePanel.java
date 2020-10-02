import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (GAME_WIDTH / 2);
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    static final int CELL_SIZE = 50;
    static final int MINES = 40;

    static final int[][] FIELD = new int[GAME_WIDTH / CELL_SIZE][GAME_HEIGHT / CELL_SIZE];

    static final int EMPTY_CELL = 0;
    static final int MINE_CELL = 9;
    static final int FLAG_CELL = 10;

    static int[] MINE_FIELD = new int[MINES];

    Set<Integer>mineLocation = new LinkedHashSet<Integer>();

    boolean running = false;

    Thread gameThread;

    Image image;

    Graphics graphics;

    Random random;

    GamePanel() {

        this.setPreferredSize(SCREEN_SIZE);
        this.setFocusable(true);
        this.addMouseListener(new ML());

        gameThread = new Thread(this);
        gameThread.start();

        generateMines();
        fillField();
    }

    public void paint(Graphics g) {

        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);

        for (int i = 0; i < FIELD.length; i++) {

            g.setColor(Color.gray);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GAME_HEIGHT);

            for (int j = 0; j < FIELD[i].length; j++) {

                g.drawLine(0, j * CELL_SIZE, GAME_WIDTH, j * CELL_SIZE);
            }
        }
    }

    public void draw(Graphics g) {

        g.setColor(Color.black);
        g.setFont(new Font("Consolas", Font.BOLD, 30));

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                switch (FIELD[i][j]) {

                    case 0:

                        g.setColor(new Color(255, 255, 255));
                        g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        break;

                    case 1:

                        g.setColor(new Color(0, 0, 255));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 2:

                        g.setColor(new Color(0, 127, 0));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 3:

                        g.setColor(new Color(255, 0, 0));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 4:

                        g.setColor(new Color(0, 0, 127));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 5:

                        g.setColor(new Color(127, 0, 0));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 6:

                        g.setColor(new Color(0, 127, 127));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 7:

                        g.setColor(new Color(0, 0, 0));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 8:

                        g.setColor(new Color(127, 127, 127));
                        g.drawString(String.valueOf(FIELD[i][j]), (i * CELL_SIZE), ((j + 1) * CELL_SIZE));
                        break;

                    case 9:

                        g.setColor(new Color(0, 0, 0));
                        g.fillOval(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void fillField() {

        random = new Random();

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                FIELD[i][j] = (random.nextInt(8) + 1);
            }
        }
    }

    public void generateMines() {

        random = new Random();

        while (mineLocation.size() < MINES) {

            mineLocation.add(random.nextInt(200));
        }
    }

    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (true) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {

                delta--;
            }
        }
    }

    public class ML extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            int cursorX = e.getX();
            int cursorY = e.getY();
            int cursorColumn = cursorX / CELL_SIZE;
            int cursorRow = cursorY / CELL_SIZE;

            System.out.println(FIELD[cursorColumn][cursorRow]);

            if (!running) {

                System.out.println("Out of game");
            }
        }
    }
}