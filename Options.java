import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends JPanel implements ActionListener {
   private Game game;
   private JButton aIMove;
   private JButton undo;
   private JButton redo;

   public Options(Game gm) {
      this.game = gm;
      aIMove = new JButton();
      setButton(aIMove, "AI Move");
      undo = new JButton();
      setButton(undo, "Undo");
      redo = new JButton();
      setButton(redo, "Redo");

      setLayout(new GridLayout(1, 3));
      setVisible(true);
   }

   private void setButton(JButton button, String text) {
      button.setText(text);
      button.setBackground(new Color(240, 240, 255));
      button.setFont(new Font("Geneva", Font.BOLD, 30));
      button.setFocusable(false);
      button.addActionListener(this);
      add(button);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == aIMove) {
         this.game.aiMove();
      } else if (e.getSource() == undo) {
         this.game.undo();
      } else if (e.getSource() == redo) {
         this.game.redo();
      }  
   }

   public void gameover() {
      aIMove.setEnabled(false);
      undo.setEnabled(false);
      redo.setEnabled(false);
   }
}
