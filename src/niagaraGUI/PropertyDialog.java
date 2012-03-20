package niagaraGUI;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Set;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PropertyDialog implements ActionListener{
  JFrame frame;
  GraphNode partnerNode;
  JPanel attribPanel;
  JPanel contentTextPanel;
  JPanel commentTextPanel;
  JPanel buttonPanel;
  JButton btnUpdate;
  JButton btnCancel;
  JTextArea commentText;
  JTextArea contentText;
  HashMap<String, JTextField> txtBoxes;
  public PropertyDialog(GraphNode n){
	  txtBoxes = new HashMap<String, JTextField>();
	  partnerNode = n;
	  frame = new JFrame("Properties for " + partnerNode.getName());
	  frame.setLayout(new GridLayout(0,1));
	  attribPanel = new JPanel(new GridLayout(0,2));
	  attribPanel.add(new JLabel("Required Attributes"));
	  attribPanel.add(new JLabel(""));
	  //attribPanel.setLayout(new BoxLayout(attribPanel, BoxLayout.PAGE_AXIS));
	  HashMap<String,String> reqAttribs = partnerNode.getRequiredAttributes();
	  Set<String> keys = reqAttribs.keySet();
	  for (String k : keys){
		  JLabel lbl = new JLabel(k,JLabel.RIGHT);
		  JTextField txt = new JTextField((String)reqAttribs.get(k), 10);
		  txtBoxes.put(k, txt);
		  if (k.equals("input")){
			  txt.setEditable(false);
		  }
		  lbl.setLabelFor(txt);
		  attribPanel.add(lbl);
		  attribPanel.add(txt);
		  //attribPanel.add(new AttribPanel(k, (String)reqAttribs.get(k)));
	  }
	  
	  attribPanel.add(new JLabel("Optional Attributes"));
	  attribPanel.add(new JLabel(""));
	  
	  HashMap<String,String> optAttribs = partnerNode.getOptionalAttributes();
	  keys = optAttribs.keySet();
	  for (String k : keys){
		  JLabel lbl = new JLabel(k,JLabel.RIGHT);
		  JTextField txt = new JTextField((String)reqAttribs.get(k), 10);
		  txtBoxes.put(k, txt);
		  lbl.setLabelFor(txt);
		  attribPanel.add(lbl);
		  attribPanel.add(txt);
		  //attribPanel.add(new AttribPanel(k, (String)reqAttribs.get(k)));
	  }
	  
	  frame.add(attribPanel);
	  
	  contentTextPanel = new JPanel();
	  contentTextPanel.setLayout(new BoxLayout(contentTextPanel, BoxLayout.PAGE_AXIS));
	  contentText = new JTextArea(partnerNode.elements);
	  contentTextPanel.add(new JLabel("Content Text"));
	  contentTextPanel.add(contentText);
	  
	  
	  frame.add(contentTextPanel);
	  
	  commentTextPanel = new JPanel();
	  commentTextPanel.setLayout(new BoxLayout(commentTextPanel,BoxLayout.PAGE_AXIS));
	  commentTextPanel.add(new JLabel("Comment Text:"));
	  commentText = new JTextArea(partnerNode.comments);
	  commentTextPanel.add(commentText);
	  
	  buttonPanel = new JPanel();
	  buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
	  btnUpdate = new JButton("Update");
	  btnUpdate.addActionListener(this);
	  btnCancel = new JButton("Cancel");
	  btnCancel.addActionListener(this);
	  buttonPanel.add(btnUpdate);
	  buttonPanel.add(btnCancel);
	  
	  frame.add(commentTextPanel);
	  
	  
	  
	  frame.add(buttonPanel);
	  
	  frame.pack();
	  //Dimension minSize = new Dimension(400,200);
	  //frame.setMinimumSize(minSize);
	  frame.setVisible(true);
	  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  }
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == btnUpdate){
			System.out.println("Update");
			updatePartnerNode();
		}
		else if (src == btnCancel){
			System.out.println("Cancel");
			frame.hide();
		}
		
	}
	
	private void updatePartnerNode(){
		Set<String> keys = txtBoxes.keySet();
		for (String k: keys){
			String s = txtBoxes.get(k).getText();
			String val = null;
			if (!s.isEmpty()) val = s;
			partnerNode.setAttribute(k, val);
		}
		partnerNode.comments = commentText.getText();
		partnerNode.elements = contentText.getText();
		partnerNode.update();
	}
  
}