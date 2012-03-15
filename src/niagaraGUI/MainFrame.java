package niagaraGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxEdgeStyle;

import javax.swing.*;


public class MainFrame extends JFrame implements ActionListener {
	private JDesktopPane jd;
	private mxGraph graph;
	private Object parent;
	private JPopupMenu popup;
	private JMenuItem propMenuItm;
	private DTDInterpreter dtd;
	private Hashtable<String, OperatorTemplate> operators;
	private GraphNode[] nodes;
	
	
	private mxGraphComponent graphComponent;
	private mxCell clickedCell;
	public MainFrame(){
		super();
		//jd = new JDesktopPane();
		//setContentPane(jd);
		initComponents();
		hw();
	}
	
	private void hw(){
		dtd = new DTDInterpreter("/Users/xianthrops/School/CS410/working/NiagraST-GUI-Query-Assembler/queryplan.dtd");
		operators = dtd.getTemplates();
		Set<String> opNames= operators.keySet();
		Iterator opIt = opNames.iterator();
		nodes = new GraphNode[opNames.size()];
		int i=0;
		while(opIt.hasNext()){
			String s =(String) opIt.next();
			System.out.println(s);
			OperatorTemplate o = operators.get(s);
			System.out.println(o);
			nodes[i++] = new GraphNode(o);
		}
		
		
		try
		{
			
			for(int j=0; j < nodes.length; j++){
				System.out.println(nodes[j]);
				nodes[j].draw(graph, parent);
			}
			
			
			//
			
			


			
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		
	}
	private void initComponents(){
		graph = new mxGraph();
		parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		graph.setCellsEditable(false);
		graph.setAutoSizeCells(true);
		
		Map<String, Object> stil = new HashMap<String, Object>();
		stil.put(mxConstants.STYLE_ROUNDED, true);
		stil.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION); // <-- This is what you want
		stil.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		stil.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		stil.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		stil.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		stil.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
		stil.put(mxConstants.STYLE_FONTCOLOR, "#446299");

		mxStylesheet foo = new mxStylesheet();
		foo.setDefaultEdgeStyle(stil);
		graph.setStylesheet(foo);
		
		graph.setCellStyle( "edgeStyle=mxEdgeStyle.orthoConnector" );
		graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	        	if (e.getButton() == e.BUTTON3){
		            clickedCell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
		            System.out.println("Mouse click in graph component");
		            popup.show(graphComponent, e.getX(), e.getY());
	        	}
	        }
	    });
		
		popup = new JPopupMenu();
		propMenuItm = new JMenuItem("Properties");
		propMenuItm.addActionListener(this);
		popup.add(propMenuItm);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == propMenuItm){
			PropertyDialog pd = new PropertyDialog((GraphNode)clickedCell.getValue());
			System.out.println("cell=" + graph.getLabel(clickedCell));
		}
	}
	public void showPopup(){
		
	}

}
