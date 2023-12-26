import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;

public class Start extends JFrame implements ActionListener {

   JButton tictactoe;

   Start() {

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLayout(new GridLayout());

      tictactoe = new JButton();
      tictactoe.setText("TicTacToe");
      tictactoe.addActionListener(this);



      this.add(tictactoe);
      this.pack();
      this.setVisible(true);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == tictactoe) {
         this.dispose();
         new TicTacToe(false);
      }
      
   }

}
