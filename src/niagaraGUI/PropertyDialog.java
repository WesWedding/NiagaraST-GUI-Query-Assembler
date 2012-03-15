package niagaraGUI;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PropertyDialog{
  JFrame frame;

  public PropertyDialog(GraphNode n){
  frame = new JFrame("Properties for " + n.getName());
  JLabel label = new JLabel(n.toString());
  frame.add(label);
  frame.setVisible(true);
  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }
  
}