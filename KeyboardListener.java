import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
   Game display;

   public KeyboardListener(Game d) {
      display = d;
   }

   @Override
   public void keyTyped(KeyEvent e) {
      return;
   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()) == "Up") {
         display.up();
      } else if (KeyEvent.getKeyText(e.getKeyCode()) == "Down") {
         display.down();
      } else if (KeyEvent.getKeyText(e.getKeyCode()) == "Right") {
         display.right();
      } else if (KeyEvent.getKeyText(e.getKeyCode()) == "Left") {
         display.left();
      } else if (KeyEvent.getKeyText(e.getKeyCode()) == "Shift") {
         display.aiMove();
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {
      return;
   }
}
