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
	  partnerNode = n;
	  show();
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
	private void initDraw(){
		txtBoxes = new HashMap<String, JTextField>();
		frame = new JFrame("Properties for " + partnerNode.getName());
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
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
	  
		contentPane.add(attribPanel);
	  
		contentTextPanel = new JPanel(new BorderLayout());
		contentText = new JTextArea(partnerNode.elements,20,20);
		JScrollPane scrollPane1 = new JScrollPane(contentText);
		contentText.setLineWrap(true);
		contentTextPanel.add(new JLabel("Content Text"),BorderLayout.PAGE_START);
		contentTextPanel.add(scrollPane1, BorderLayout.CENTER);
		
		contentPane.add(contentTextPanel);
		
		commentTextPanel = new JPanel(new BorderLayout());
		commentTextPanel.add(new JLabel("Comment Text:"),BorderLayout.PAGE_START);
		commentText = new JTextArea(partnerNode.comments, 20,20);
		JScrollPane scrollPane = new JScrollPane(commentText); 
		commentText.setLineWrap(true);
		commentTextPanel.add(scrollPane, BorderLayout.CENTER);
		
		contentPane.add(commentTextPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnCancel);
		
		contentPane.add(buttonPanel);
		
		frame.pack();
		//Dimension minSize = new Dimension(400,200);
		//frame.setMinimumSize(minSize);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	public void show(){
		if (frame != null){
			frame.setVisible(true);
			updateContents();
		}
		else{
			initDraw();
		}
	}
	public void updateContents(){
		//updates all fields to reflect state of partnerNode
		HashMap<String,String> reqAttribs = partnerNode.getRequiredAttributes();
		HashMap<String,String> optAttribs = partnerNode.getOptionalAttributes();
		Set<String> keys = reqAttribs.keySet();
		JTextField txt;
		for(String k : keys){
			txt = (JTextField)this.txtBoxes.get(k);
			txt.setText(reqAttribs.get(k));
		}
		keys = optAttribs.keySet();
		for(String k : keys){
			txt = (JTextField)this.txtBoxes.get(k);
			txt.setText(optAttribs.get(k));
		}
		this.contentText.setText(partnerNode.elements);
		this.commentText.setText(partnerNode.comments);
	}
  
}