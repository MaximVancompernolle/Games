import java.awt.*;

public class Cell extends Rectangle {

    int column;
    int row;
    int xPosition;
    int yPosition;
    int width;
    int mineCount;

    boolean isMine;
    boolean revealed;
    boolean isFlag;

    Cell(int column, int row, int width) {

        this.column = column;
        this.row = row;
        this.xPosition = column * width;
        this.yPosition = row * width;
        this.width = width;
        this.mineCount = 0;

        this.isMine = false;
        this.revealed = false;
        this.isFlag = false;
    }

    public void draw(Graphics g) {

        if (this.revealed) {

            if (this.isFlag) {

                g.setColor(new Color(127, 0, 0));
                g.drawLine((this.xPosition + (GamePanel.CELL_SIZE / 10)), (this.yPosition + (GamePanel.CELL_SIZE / 10)), (this.xPosition + ((9 * GamePanel.CELL_SIZE) / 10)), (this.yPosition + ((9 * GamePanel.CELL_SIZE) / 10)));
                g.drawLine((this.xPosition + ((9 * GamePanel.CELL_SIZE) / 10)), (this.yPosition + (GamePanel.CELL_SIZE / 10)), (this.xPosition + (GamePanel.CELL_SIZE / 10)), (this.yPosition + ((9 * GamePanel.CELL_SIZE) / 10)));

            } else if (this.isMine) {

                g.setColor(new Color(0, 0, 0));
                g.fillOval(this.xPosition, this.yPosition, GamePanel.CELL_SIZE, GamePanel.CELL_SIZE);
            } else {

                switch (this.mineCount) {

                    case 0:

                        g.setColor(new Color(127, 127, 127));
                        g.fillRect(this.xPosition, this.yPosition, GamePanel.CELL_SIZE, GamePanel.CELL_SIZE);
                        break;

                    case 1:

                        g.setColor(new Color(0, 0, 255));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 2:

                        g.setColor(new Color(0, 127, 0));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 3:

                        g.setColor(new Color(255, 0, 0));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 4:

                        g.setColor(new Color(0, 0, 127));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 5:

                        g.setColor(new Color(127, 0, 0));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 6:

                        g.setColor(new Color(0, 127, 127));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 7:

                        g.setColor(new Color(0, 0, 0));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;

                    case 8:

                        g.setColor(new Color(127, 127, 127));
                        g.drawString(String.valueOf(this.mineCount), this.xPosition, this.yPosition + GamePanel.CELL_SIZE);
                        break;
                }
            }
        } else {

            g.setColor(Color.lightGray);
            g.fillRect(this.xPosition, this.yPosition, GamePanel.CELL_SIZE, GamePanel.CELL_SIZE);
        }
    }

    public void reveal() {

        this.revealed = true;
        if (this.mineCount == 0) {

            this.floodFill();
        }
    }

    public void countMines() {

        if (this.isMine) {

            this.mineCount = -1;
            return;
        }

        int total = 0;

        for (int xoff = -1; xoff <= 1; xoff++) {

            int col = this.column + xoff;

            if (col < 0 || (col >= (GamePanel.GAME_WIDTH / GamePanel.CELL_SIZE))) {

                continue;
            }

            for (int yoff = -1; yoff <= 1; yoff++) {

                int row = this.row + yoff;

                if (row < 0 || (row >= (GamePanel.GAME_HEIGHT / GamePanel.CELL_SIZE))) {

                    continue;
                }

                Cell neighbor = GamePanel.FIELD[col][row];

                if (neighbor == null) {

                    return;
                } else if (neighbor.isMine) {

                    total++;
                }
            }
        }

        this.mineCount = total;
    }

    public void floodFill() {

        if (this.isMine) {

            return;
        }

        for (int xoff = -1; xoff <= 1; xoff++) {

            int col = this.column + xoff;

            if (col < 0 || (col >= (GamePanel.GAME_WIDTH / GamePanel.CELL_SIZE))) {

                continue;
            }

            for (int yoff = -1; yoff <= 1; yoff++) {

                int row = this.row + yoff;

                if (row < 0 || (row >= (GamePanel.GAME_HEIGHT / GamePanel.CELL_SIZE))) {

                    continue;
                }

                Cell neighbor = GamePanel.FIELD[col][row];

                if (!neighbor.revealed && !neighbor.isMine) {

                    neighbor.reveal();
                }
            }
        }
    }
}