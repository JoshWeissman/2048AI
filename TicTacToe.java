import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;

public class TicTacToe extends JFrame implements ActionListener {

   boolean multiplayer;
   String[] symbol_options = {"X", "O"};
   String player_symbol = symbol_options[0];
   String computer_symbol = symbol_options[1];

   JButton tl = new JButton();
   JButton tm = new JButton();
   JButton tr = new JButton();
   JButton ml = new JButton();
   JButton mm = new JButton();
   JButton mr = new JButton();
   JButton bl = new JButton();
   JButton bm = new JButton();
   JButton br = new JButton();
   JButton[] cells = { tl, tm, tr, ml, mm, mr, bl, bm, br };

   TicTacToe(boolean multiplayer) {
      this.multiplayer = multiplayer;

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLayout(new GridLayout(3, 3));

      for (JButton cell : cells) {
         cell.setBackground(new Color(240, 240, 255));
         cell.setPreferredSize(new Dimension(100, 100));
         cell.setFont(new Font("Geneva", Font.BOLD, 30));
         cell.setFocusable(false);
         cell.addActionListener(this);
         this.add(cell);
      }

      this.pack();
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton cell;
      if (e.getSource() == tl) {
         cell = tl;
      } else if (e.getSource() == tm) {
         cell = tm;
      } else if (e.getSource() == tr) {
         cell = tr;
      } else if (e.getSource() == ml) {
         cell = ml;
      } else if (e.getSource() == mm) {
         cell = mm;
      } else if (e.getSource() == mr) {
         cell = mr;
      } else if (e.getSource() == bl) {
         cell = bl;
      } else if (e.getSource() == bm) {
         cell = bm;
      } else if (e.getSource() == br) {
         cell = br;
      } else {
         cell = new JButton();
         System.out.println("UH OH");
      }
      setCell(cell);

      if (multiplayer) {
         return;
      }
      setCell(cells[compMove(getBoard())]);

      // detect win function

   }

   ////////////////////////////////////////////// SETTING CELL//////////////////////////////////////////////
   private void setCell(JButton cell) {
      if (cell.getText() == "") {
         cell.setText(String.valueOf(symbol_options[0]));
         String a = this.symbol_options[0], b = this.symbol_options[1];
         this.symbol_options[0] = b;
         this.symbol_options[1] = a;
      }
   }

   ////////////////////////////////////////////// COMPUTER MOVE//////////////////////////////////////////////
   private int compMove(String[] board) {
      int bestMove = 0;
      int bestScore = Integer.MIN_VALUE;

      for (int i = 0; i < 9; i++) {
         if (board[i] == computer_symbol || board[i] == player_symbol) {
            continue;
         }
         board[i] = computer_symbol;
         int moveScore = minimax(board, getBlankSpaces(board), false);
         if (moveScore > bestScore) {
            bestMove = i;
            bestScore = moveScore;
         }
         board[i] = "";
      }
      return bestMove;
   }

   private int minimax(String[] board, int depth, boolean maximizing_player) {
      int score = getScore(board);
      if (depth == 0 || Math.abs(score) == 10) {
         return score;
      }
      if (maximizing_player) {
         int max_eval = Integer.MIN_VALUE;
         for (int i = 0; i < 9; i++) {
            if (board[i] == computer_symbol || board[i] == player_symbol) {
               continue;
            }
            board[i] = computer_symbol;
            int eval = minimax(board, depth - 1, false);
            max_eval = Math.max(max_eval, eval);
            board[i] = "";
         }
         return max_eval;
      } else {
         int min_eval = Integer.MAX_VALUE;
         for (int i = 0; i < 9; i++) {
            if (board[i] == computer_symbol || board[i] == player_symbol) {
               continue;
            }
            board[i] = player_symbol;
            int eval = minimax(board, depth - 1, true);
            min_eval = Math.min(min_eval, eval);
            board[i] = "";
         }
         return min_eval;
      }
   }

   private String[] getBoard() {
      String[] board = new String[9];
      for (int i = 0; i < 9; i++) {
         board[i] = cells[i].getText();
      }
      return board;
   }

   private int getScore(String[] board) {
      String[][] lines = new String[8][3];
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            lines[i][j] = board[j + 3 * i];
            lines[i + 3][j] = board[i + 3 * j];
         }
         lines[6][i] = board[4 * i];
         lines[7][i] = board[2 + 2 * i];
      }

      for (String[] line : lines) {
         if (line[0].equals(computer_symbol) && line[1].equals(computer_symbol) && line[2].equals(computer_symbol)) {
            return 10;
         } else if (line[0].equals(player_symbol) && line[1].equals(player_symbol) && line[2].equals(player_symbol)) {
            return -10;
         }
      }
      return 0;
   }

   private int getBlankSpaces(String[] board) {
      int blanks = 0;
      for (int i = 0; i < 9; i++) {
         if (board[i].equals("")) {
            blanks++;
         }
      }
      return blanks;
   }

}