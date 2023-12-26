import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class Cell extends JButton {
   ColorPalette colorPalette = new ColorPalette();

   public Cell(int size) {
      setBackground(new Color(255, 255, 255));
      setPreferredSize(new Dimension(500 / size, 500 / size));
      setFont(new Font("Geneva", Font.BOLD, 30 - size * 2));
      setFocusable(false);
   }

   public void update(int val) {
      update(String.valueOf(val));
   }

   public void update(String val) {
      setText(val);
      setBackground(colorPalette.getColor(val));
   }

   public void gameover() {
      setBackground(colorPalette.getColor("gameover"));
      setEnabled(false);
   }

   }
