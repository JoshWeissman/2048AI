import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
   private List<Integer> board = new ArrayList<Integer>();
   public boolean gameover = false;
   private int size;
   private List<List<Integer>> past;
   private List<List<Integer>> future;

   public Board(int sz) {
      size = sz;
      this.gameover = false;
      this.board = generateBoard();
      this.past = new ArrayList<>();
      this.future = new ArrayList<>();
   }

   public Board(int sz, List<Integer> bd) {
      size = sz;
      this.gameover = false;
      this.board = new ArrayList<>(bd);
   }

   private int generatePiece() {
      int[] options = { 2, 2, 2, 2, 2, 2, 2, 2, 2, 4 };
      return options[new Random().nextInt(options.length)];
   }
   
   private <T> int occurences(List<T> board, T target) {
      int counter = 0;
      for ( T cell : board) {
         if (cell == target) {
            counter ++;
         }
      }
      return counter;
   }

   private List<Integer> generateBoard() {
      List<Integer> board = new ArrayList<>();
      for (int i = 0; i < size * size; i++) {
         board.add(0);
      }
      while (occurences(board, 0) > size * size - size + 2) {
         int cell = new Random().nextInt(size * size);
         board.set(cell, generatePiece());
      }
      return board;
   }

   public void verticalCollapse(boolean reverse, boolean addPiece) {
      List<Integer> tempBoard = new ArrayList<>(this.board);
      int[][] stacks = new int[size][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[i][j] = this.board.get(i + j * size);
         }
      }
      int[][] newStacks = collapse(stacks, reverse);
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            this.board.set(i + j * size, newStacks[i][j]);
         }
      }
      if (!sameBoard(tempBoard) && addPiece) {
         this.past.add(new ArrayList<>(tempBoard));
         addPiece();
      }
   }
   
   public void horizontalCollapse(boolean reverse, boolean addPiece) {
      List<Integer> tempBoard = new ArrayList<>(this.board);
      int[][] stacks = new int[size][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[j][i] = this.board.get(i + j * size);
         }
      }
      int[][] newStacks = collapse(stacks, reverse);
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            this.board.set(i + j * size, newStacks[j][i]);
         }
      }
      if (!sameBoard(tempBoard) && addPiece) {
         this.past.add(new ArrayList<>(tempBoard));
         addPiece();
      }
   }

   private int[][] collapse(int[][] stacks, boolean reverse) {
      int[][] newStacks = new int[size][size];
      for (int i = 0; i < size; i++) {
         List<Integer> oldStack = new ArrayList<>();
         for ( int cell : stacks[i]) {
            if (cell != 0) {
               oldStack.add(cell);
            }
         }
         if (!reverse) {
            Collections.reverse(oldStack);
         }
         List<Integer> newStack = new ArrayList<>();
         while (oldStack.size() >= 2) {
            if ((int) oldStack.get(0) == (int) oldStack.get(1)) {
               newStack.add(oldStack.remove(1) + oldStack.remove(0));
            } else {
               newStack.add(oldStack.remove(0));
            }
         }
         if (oldStack.size() >= 1) {
            newStack.add(oldStack.remove(0));
         }
         if (!reverse) {
            Collections.reverse(newStack);
         }
         if (reverse) {
            while (newStack.size() < size) {
               newStack.add(0);
            }
         } else {
            while (newStack.size() < size) {
               newStack.add(0, 0);
            }
         }
         for (int j = 0; j < size; j++) {
            newStacks[i][j] = newStack.get(j);
         }
      }
      return newStacks;
   }

   private void addPiece() {
      while (true) {
         int cell = new Random().nextInt(size * size);
         if (this.board.get(cell) == 0) {
            this.board.set(cell, generatePiece());
            break;
         }
      }
      checkGameover();
   }

   public void addPiece(int i) {
      this.board.set(i, generatePiece());
   }

   private void checkGameover() {
      if (this.board.contains(0)) {
         return;
      }
      int[][] stacks = new int[size * 2][size];
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[i][j] = this.board.get(i + j * size);
         }
      }
      for (int i = 0; i < size; i++) {
         for (int j = 0; j < size; j++) {
            stacks[j + size][i] = this.board.get(i + j * size);
         }
      }
      for ( int[] stack : stacks ) {
         for (int i = 0; i < size - 1; i++) {
            if (stack[i] == stack[i + 1]) {
               return;
            }
         }
      }
      this.gameover = true;
   }

   public List<Integer> getBoard() {
      return new ArrayList<>(this.board);
   }

   public List<String> getStringBoard() {
      List<String> board = new ArrayList<>();
      for (int i = 0; i < size * size; i++) {
            board.add(String.valueOf(this.board.get(i)));
      }
      return board;
   }

   public boolean sameBoard(List<Integer> tempBoard) {
      for (int i = 0; i < size * size; i++) {
         if ((int) this.board.get(i) != (int) tempBoard.get(i)) {
            return false;
         }
      }
      return true;
   }

   public void undo() {
      try {
         List<Integer> nb = this.past.remove(this.past.size() - 1);
         this.future.add(new ArrayList<>(this.board));
         this.board = nb;
      } catch (IndexOutOfBoundsException e) {
            
      }
   }

   public void redo() {
      try {
         List<Integer> nb = this.future.remove(this.future.size() - 1);
         this.past.add(new ArrayList<>(this.board));
         this.board = nb;
      } catch (IndexOutOfBoundsException e) {
         
      }
   }
}
