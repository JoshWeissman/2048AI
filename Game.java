import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.GridLayout;

public class Game extends JPanel {
   private int size;
   private List<Cell> cells;
   private Board board;
   private Display display;

   public Game(int sz, Display d) {
      size = sz;
      cells = new ArrayList<>();
      board = new Board(size);
      display = d;

      for (int i = 0; i < size * size; i++) {
         Cell cell = new Cell(size);
         cells.add(cell);
         add(cell);
      }

      setPreferredSize(new Dimension(2000 / size, 2000 / size));
      setLayout(new GridLayout(size, size));
      setVisible(true);
      update();
   }

   public void aiMove() {
      AI ai = new AI(size, new ArrayList<>(board.getBoard()));
      int direction = ai.playerMove();
      if (direction == 1) {
         up();
      } else if (direction == 2) {
         down();
      } else if (direction == 3) {
         right();
      } else if (direction == 4) {
         left();
      }
   }

   public void up() {
      board.verticalCollapse(true, true);
      update();
   }

   public void down() {
      board.verticalCollapse(false, true);
      update();
   }

   public void right() {
      board.horizontalCollapse(false, true);
      update();
   }

   public void left() {
      board.horizontalCollapse(true, true);
      update();
   }

   public void undo() {
      board.undo();
      update();
   }

   public void redo() {
      board.redo();
      update();
   }

   private void update() {
      for (int i = 0; i < size * size; i++) {
         String val = board.getStringBoard().get(i);
         if (val.equals("0")) {
            cells.get(i).update("");
         } else {
            cells.get(i).update(val);
         }
      }
      if (board.gameover) {
         gameover();
         display.gameover();
      }
   }

   private void gameover() {
      for (int i = 0; i < size * size; i++) {
         cells.get(i).gameover();
      }
   }
}
