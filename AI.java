import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AI {
   final int DEPTH = 4;
   int size;
   List<Integer> board;
   List<Consumer<Board>> collapseMethods = new ArrayList<>();

   public AI(int sz, List<Integer> bd) {
      size = sz;
      board = bd;
      collapseMethods.add(b -> b.verticalCollapse(true, false));
      collapseMethods.add(b -> b.verticalCollapse(false, false));
      collapseMethods.add(b -> b.horizontalCollapse(false, false));
      collapseMethods.add(b -> b.horizontalCollapse(true, false));
   }

   public int playerMove() {
      int bestMove = 0;
      int maxEval = Integer.MIN_VALUE;

      for ( Consumer<Board> method : collapseMethods ) {
         Board bd = new Board(size, board);
         List<Integer> tempBd = new ArrayList<>(bd.getBoard());
         method.accept(bd);
         if (bd.sameBoard(tempBd)) {
            continue;
         }
         int eval = minimax(bd.getBoard(), DEPTH, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
         if (eval >= maxEval) {
            bestMove = collapseMethods.indexOf(method) + 1;
            maxEval = eval;
         }
      }
      return bestMove;
   }

   private int minimax(List<Integer> board, int depth, boolean maximizingPlayer, int alpha, int beta) {
      if (depth == 0) {
         return getScore(board);
      }
      if (maximizingPlayer) {
         int maxEval = Integer.MIN_VALUE;
         for ( Consumer<Board> method : collapseMethods ) {
            Board bd = new Board(size, board);
            List<Integer> tempBd = new ArrayList<>(bd.getBoard());
            method.accept(bd);
            if (bd.sameBoard(tempBd)) {
               continue;
            }
            int eval = minimax(bd.getBoard(), depth - 1, false, alpha, beta);
            maxEval = Math.max(maxEval, eval);
            alpha = Math.max(alpha, maxEval);
            if (beta <= alpha) {
               break;
            }
         }
         return maxEval;
      } else {
         int minEval = Integer.MAX_VALUE;
         for (int i = 0; i < size * size; i++) {
            for (int j = 1; j <= 2; j++) {
               List<Integer> bd = new ArrayList<>(board);
               if (bd.get(i) != 0) {
                  continue;
               }
               bd.set(i, j * 2);
               int eval = minimax(bd, depth - 1, true, alpha, beta);
               minEval = Math.min(minEval, eval);
               beta = Math.min(beta, minEval);
               if (beta <= alpha) {
                  break;
               }
            }
         }
         return minEval;
      }
   }

   private int getScore(List<Integer> board) {
      int score = 0;
      score += emptyCells(board, 25);
      score += highestConcentration(board, 5, 45, 35);
      score += combinable(board, 2);
      return score;
   }

   private int emptyCells(List<Integer> board, int m) {
      int score = 0;
      for (int i = 0; i < size * size; i++) {
         if (board.get(i) == 0) {
            score++;
         }
      }
      return score * m;
   }

   private int highestConcentration(List<Integer> board, int m1, int m2, int m3) {
      int score = 0;
      int highest = 0;
      int highestX = 0;
      int highestY = 0;
      for (int i = 0; i < size * size; i++) {
         if (board.get(i) > highest) {
            highest = board.get(i);
            highestX = i % size;
            highestY = i / size;
         }
      }
      if ((highestX == 0 || highestX == size - 1) && (highestY == 0 || highestY == size - 1)) {
         score += m1;
      }
      if (highestX == 0 || highestX == size - 1 || highestY == 0 || highestY == size - 1) {
         score += m2;
      }
      return score + (int)(Math.log(highest) / Math.log(2)) * m3;
   }

   private int combinable(List<Integer> board, int m) {
      int score = 0;
      int[][] stacks = new int[size * 2][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[i][j] = board.get(i + j * size);
         }
      }
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[j + size][i] = board.get(i + j * size);
         }
      }
      for ( int[] stack : stacks ) {
         for (int i = 0; i < size - 1; i++) {
            if (Math.abs((int)(Math.log(stack[i]) / Math.log(2)) - (int)(Math.log(stack[i + 1]) / Math.log(2))) <= 1) {
               score += Math.min((int)(Math.log(stack[i]) / Math.log(2)), (int)(Math.log(stack[i + 1]) / Math.log(2)));
            }
         }
      }
      return score * m;
   }
}
