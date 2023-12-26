import javax.swing.JFrame;
import java.awt.FlowLayout;

public class Display extends JFrame {
   Game display;
   KeyboardListener input;
   Options options;

   public Display(int size) {
      display = new Game(size, this);
      input = new KeyboardListener(display);
      options = new Options(display);
            
      add(display);
      add(options);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new FlowLayout(1));
      setVisible(true);
      addKeyListener(input);
      pack();
   }

   public void gameover() {
      removeKeyListener(input);
      options.gameover();
   }
}
