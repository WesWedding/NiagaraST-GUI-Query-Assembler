package niagaraGUI;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Set;
import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class PropertyDialog{
  JFrame frame;
  GraphNode partnerNode;
  JPanel panel;
  public class AttribPanel extends JPanel{
	  JLabel lbl;
	  JTextField txt;
	  AttribPanel(String attrib, String value){
		  super(new FlowLayout());
		  lbl = new JLabel(attrib);
		  txt = new JTextField(value, 15);
		  lbl.setLabelFor(txt);
		  this.add(lbl);
		  this.add(txt);
	  }
  }
  public PropertyDialog(GraphNode n){
	  partnerNode = n;
	  frame = new JFrame("Properties for " + partnerNode.getName());
	  panel = new JPanel(new GridLayout(0,2));
	  panel.add(new JLabel("Required Attributes"));
	  panel.add(new JLabel(""));
	  //panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	  HashMap<String,String> reqAttribs = partnerNode.getRequiredAttributes();
	  Set<String> keys = reqAttribs.keySet();
	  for (String k : keys){
		  JLabel lbl = new JLabel(k,JLabel.RIGHT);
		  JTextField txt = new JTextField((String)reqAttribs.get(k), 10);
		  if (k.equals("input")){
			  txt.setEditable(false);
		  }
		  lbl.setLabelFor(txt);
		  panel.add(lbl);
		  panel.add(txt);
		  //panel.add(new AttribPanel(k, (String)reqAttribs.get(k)));
	  }
	  
	  panel.add(new JLabel("Optional Attributes"));
	  panel.add(new JLabel(""));
	  
	  HashMap<String,String> optAttribs = partnerNode.getOptionalAttributes();
	  keys = optAttribs.keySet();
	  for (String k : keys){
		  JLabel lbl = new JLabel(k,JLabel.RIGHT);
		  JTextField txt = new JTextField((String)reqAttribs.get(k), 10);
		  lbl.setLabelFor(txt);
		  panel.add(lbl);
		  panel.add(txt);
		  //panel.add(new AttribPanel(k, (String)reqAttribs.get(k)));
	  }
	  
	  frame.add(panel);
	  frame.pack();
	  //Dimension minSize = new Dimension(400,200);
	  //frame.setMinimumSize(minSize);
	  frame.setVisible(true);
	  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }
  
}