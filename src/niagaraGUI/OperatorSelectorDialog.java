package niagaraGUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class OperatorSelectorDialog implements ActionListener{
	private JFrame frame;
	private JList opList;
	private List<String> opNames;
	private JButton addBtn;
	private Container content;
	private MainFrame master;
	public OperatorSelectorDialog(List<String> opNames, MainFrame masterFrame){
		super();
		this.opNames = opNames;
		Collections.sort(this.opNames);
		this.master = masterFrame;
		initComponents();
		
	}
	private void initComponents(){
		frame = new JFrame("Operators");
		content = frame.getContentPane();
	    content.setLayout(new GridLayout(0,1));
	    
	    opList = new JList(opNames.toArray());
		JScrollPane listScroller = new JScrollPane(opList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		content.add(listScroller);
		
	    addBtn = new JButton("Add Operator");
	    addBtn.addActionListener(this);
	    content.add(addBtn);
	    

	    frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == addBtn){
			Object[] ops = opList.getSelectedValues();
			for (Object o: ops){
				System.out.println(master.addOperator((String)o));
				System.out.println((String)o);
				
			}
			int[] a = {};
			opList.setSelectedIndices(a);
			
		}
		
	}
}
