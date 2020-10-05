import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (GAME_WIDTH / 2);
    static final int CELL_SIZE = 50;
    static final int MINES = 40;

    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    static final Cell[][] FIELD = new Cell[GAME_WIDTH / CELL_SIZE][GAME_HEIGHT / CELL_SIZE];
    static final int[] MINE_INDEXES = new int[MINES];

    boolean running;

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

        running = true;

        generateMines();
        fillField();
        count();
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

        g.setFont(new Font("Consolas", Font.BOLD, 60));

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                if (!(FIELD[i][j] == null)) {

                    FIELD[i][j].draw(g);
                }
            }
        }
    }

    public void fillField() {

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                if (FIELD[i][j] == null) {

                    FIELD[i][j] = new Cell(i, j, CELL_SIZE);
                }
            }
        }
    }

    public void generateMines() {

        random = new Random();

        for (int i = 0; i < MINES;) {

            int r =random.nextInt(((GAME_WIDTH / CELL_SIZE) * (GAME_HEIGHT / CELL_SIZE)));

            if (!arrayContains(MINE_INDEXES, r)) {

                MINE_INDEXES[i] = r;
                i++;
            }
        }

        for (int i : MINE_INDEXES) {

            int column = i % (GAME_WIDTH / CELL_SIZE);
            int row = i / (GAME_WIDTH / CELL_SIZE);

            FIELD[column][row] = new Cell(column, row, CELL_SIZE);
            FIELD[column][row].isMine = true;
            FIELD[column][row].revealed = false;
        }
    }

    public void count() {

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                FIELD[i][j].countMines();
            }
        }
    }

    public static boolean arrayContains(int[] arr, int x) {

        boolean result = false;

        for (int i : arr) {

            if (i == x) {

                result = true;
                break;
            }
        }

        return result;
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

    public void gameOver() {

        for (int i = 0; i < FIELD.length; i++) {

            for (int j = 0; j < FIELD[i].length; j++) {

                FIELD[i][j].revealed = true;
            }
        }

        repaint();

        running = false;
    }

    public class ML extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            int cursorX = e.getX();
            int cursorY = e.getY();
            int cursorColumn = cursorX / CELL_SIZE;
            int cursorRow = cursorY / CELL_SIZE;

            if (e.getButton() == MouseEvent.BUTTON3) {

                if (FIELD[cursorColumn][cursorRow].isFlag) {

                    FIELD[cursorColumn][cursorRow].isFlag = false;
                    FIELD[cursorColumn][cursorRow].revealed = false;

                    repaint();
                } else if (!(FIELD[cursorColumn][cursorRow].isFlag) && !(FIELD[cursorColumn][cursorRow].revealed)) {

                    FIELD[cursorColumn][cursorRow].isFlag = true;
                    FIELD[cursorColumn][cursorRow].revealed = true;

                    repaint();
                }
            } else if (e.getButton() == MouseEvent.BUTTON1) {

                if (running) {

                    if (!(FIELD[cursorColumn][cursorRow] == null)) {

                        if (FIELD[cursorColumn][cursorRow].isMine) {

                            System.out.println("This square is a mine.");
                            gameOver();
                        } else if (FIELD[cursorColumn][cursorRow].revealed) {

                            boolean flag = false;

                            for (int xoff = -1; xoff <= 1; xoff++) {

                                int col = FIELD[cursorColumn][cursorRow].column + xoff;

                                if (col < 0 || (col >= (GamePanel.GAME_WIDTH / GamePanel.CELL_SIZE))) {

                                    continue;
                                }

                                for (int yoff = -1; yoff <= 1; yoff++) {

                                    int row = FIELD[cursorColumn][cursorRow].row + yoff;

                                    if (row < 0 || (row >= (GamePanel.GAME_HEIGHT / GamePanel.CELL_SIZE))) {

                                        continue;
                                    }

                                    Cell neighbor = GamePanel.FIELD[col][row];

                                    if (neighbor == null) {

                                        return;
                                    } else if (neighbor.isMine) {

                                        if (neighbor.isFlag) {

                                            flag = true;
                                        } else {

                                            flag = false;
                                        }
                                    }
                                }
                            }

                            if (flag) {

                                for (int xoff = -1; xoff <= 1; xoff++) {

                                int col = FIELD[cursorColumn][cursorRow].column + xoff;

                                if (col < 0 || (col >= (GamePanel.GAME_WIDTH / GamePanel.CELL_SIZE))) {

                                    continue;
                                }

                                for (int yoff = -1; yoff <= 1; yoff++) {

                                    int row = FIELD[cursorColumn][cursorRow].row + yoff;

                                    if (row < 0 || (row >= (GamePanel.GAME_HEIGHT / GamePanel.CELL_SIZE))) {

                                        continue;
                                    }

                                    Cell neighbor = GamePanel.FIELD[col][row];

                                    if (neighbor == null) {

                                        return;
                                    } else if (!neighbor.isMine && !neighbor.isFlag) {

                                        neighbor.reveal();
                                    }
                                }
                            }
                            }
                        } else {

                            System.out.println("This square is safe.");
                        }

                        FIELD[cursorColumn][cursorRow].isFlag = false;
                        FIELD[cursorColumn][cursorRow].reveal();

                        repaint();

                        System.out.println(FIELD[cursorColumn][cursorRow].mineCount);
                    } else {

                        System.out.println("This cell has not been initialized.");
                    }
                } else {

                    for (int i = 0; i < FIELD.length; i++) {

                        for (int j = 0; j < FIELD[i].length; j++) {

                            FIELD[i][j] = new Cell(i, j, CELL_SIZE);
                        }
                    }

                    running = true;

                    generateMines();
                    fillField();
                    count();
                }
            }
        }
    }
}