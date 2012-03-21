package niagaraGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxEdgeStyle;

import javax.swing.*;


public class MainFrame extends JFrame implements ActionListener {
	private JDesktopPane jd;
	private mxGraph graph;
	private Object parent;
	private JPopupMenu popup;
	private JMenuItem propMenuItm;//menu to bring up operator properties
	private JMenuItem topMenuItm;//menu to set operator as top
	private JMenuItem exportXMLMenuItm;
	private JMenuItem setDTDMenuItm;
	private QueryPlan queryPlan;
	private Hashtable<String,OperatorTemplate> operatorTemplates;
	private String[] operatorNames;
	private OperatorSelectorDialog opPicker;
	
	
	private mxGraphComponent graphComponent;
	private mxCell clickedCell;
	
	private class EdgeListener implements mxIEventListener{
		JFrame master;
		
		public EdgeListener(JFrame master){
			this.master = master;
		}
		@Override //Edge creation
		public void invoke(Object sender, mxEventObject evt) {
			mxCell edge = (mxCell) evt.getProperty("cell");
			GraphNode.IOPort out = (GraphNode.IOPort) edge.getSource();// gives the source cell
			GraphNode.IOPort in = (GraphNode.IOPort) edge.getTarget();//gives the target cell
			GraphNode sourceOp;
			GraphNode sinkOp;
			try{
				if (in.isInput() && out.isOutput()){//good edge
					System.out.println("GOOD EDGE");
				}
				else if (in.isOutput() && out.isInput()){//backwards edge
					System.out.println("BACKWARDS EDGE");
					
					edge.setTerminal(out, false);
					edge.setTerminal(in, true);
					graphComponent.getGraph().addCell(edge);
					GraphNode.IOPort hold = out;
					out = in;
					in = hold;
				}
				else{//bad edge
					throw new Exception("BAD EDGE");
				}
				sourceOp = (GraphNode)out.getParent().getValue();
				sinkOp = (GraphNode)in.getParent().getValue();
				if (sourceOp == sinkOp){//self input, no good!
					throw new Exception("BAD EDGE");
				}
				if (!sinkOp.connect(sourceOp)) {
					JOptionPane.showMessageDialog(this.master,
							"You have made a connection from an unidentified node or you have made a duplicate connection.\n",
							"Invalid Connection",
							JOptionPane.ERROR_MESSAGE);
					throw new Exception("Invalid Connection");
				}
				System.out.println("New Edge " + sourceOp.getName() + " to " + sinkOp.getName());
			}
			catch (Exception e){
				mxCell[] toRemove = {edge};
				graphComponent.getGraph().removeCells(toRemove);
				System.out.println(e);
				return;
			}
		}
	}
	
	private class KeyEventListener implements KeyListener{
		JFrame master;
		public KeyEventListener(JFrame master){
			this.master = master;
		}
		@Override
		public void keyPressed(KeyEvent arg0) {
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()){
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				System.out.println("DELETE KEY PRESSED");
				mxCell toDelete = (mxCell) graph.getSelectionCell();
				if (toDelete.isEdge()){
					System.out.println("EDGE REMOVAL");
					removeConnection(toDelete);
				}
				else if (toDelete.isVertex()){
					System.out.println("VERTEX REMOVAL");
					removeOperator(toDelete);
				}
				
				
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class MenuListener implements ActionListener{
		JFrame master;
		public MenuListener(JFrame master){
			this.master = master;
		}
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == exportXMLMenuItm ){
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showSaveDialog(master);
				if (returnVal == fc.APPROVE_OPTION){
					String fileName = fc.getSelectedFile().getAbsolutePath();
					//System.out.println(fileName);
					queryPlan.generateXML(fileName);
				}
			}
			else if (evt.getSource() == propMenuItm){
				if (clickedCell == null) return;
				PropertyDialog pd = new PropertyDialog((GraphNode)clickedCell.getValue());
				System.out.println("cell=" + graph.getLabel(clickedCell));
			}
			else if (evt.getSource() == topMenuItm){
				if (clickedCell == null) return;
				GraphNode newTop = (GraphNode)clickedCell.getValue();
				GraphNode oldTop = (GraphNode)queryPlan.getTop();
				if (newTop == null) return;
				queryPlan.setTop(newTop);
				newTop.update();
				if (!(oldTop==null)) oldTop.update();
			}
			else if (evt.getSource() == setDTDMenuItm){
				
			}
			
		}
		
	}
	private class MouseListener extends MouseAdapter{
		@Override
        public void mousePressed(MouseEvent e) {
        	if (e.getButton() == e.BUTTON3){
	            clickedCell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
	            System.out.println("Mouse click in graph component");
	            popup.show(graphComponent, e.getX(), e.getY());
        	}
        }
	
	}
	
	public MainFrame(){
		super();
		Dimension minSize = new Dimension(400,200);
		this.setMinimumSize(minSize);
		initMembers();
		initComponents();
		buildMenus();
	}
	private void initMembers(){//initialize class members
		queryPlan = new QueryPlan("QP","queryplan.dtd");
		operatorTemplates = queryPlan.getOpTemplates();
		operatorNames = queryPlan.getOperatorNames();
		List ops = Arrays.asList(operatorNames);
		opPicker = new OperatorSelectorDialog(ops, this);
		graph = new mxGraph();
		parent = graph.getDefaultParent();
	}
	private void initComponents(){//initialize UI components
		
		graph.getModel().beginUpdate();
		graph.setCellsEditable(false);
		graph.setAutoSizeCells(true);
		
		Map<String, Object> stil = new HashMap<String, Object>();
		stil.put(mxConstants.STYLE_ROUNDED, true);
		stil.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ENTITY_RELATION);
		stil.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		stil.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		stil.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		stil.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		stil.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
		stil.put(mxConstants.STYLE_FONTCOLOR, "#446299");
		
		Map<String, Object> stil2 = new HashMap<String, Object>();
		
		stil2.put(mxConstants.STYLE_STROKECOLOR, "#000000");
		stil2.put(mxConstants.STYLE_SEPARATORCOLOR, "#000000");
		stil2.put(mxConstants.STYLE_LABEL_BORDERCOLOR, "#000000");
		stil2.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#bfbfef");
		stil2.put(mxConstants.STYLE_RESIZABLE, false);
		stil2.put(mxConstants.STYLE_FILLCOLOR, "#00aa10");
		stil2.put(mxConstants.STYLE_FOLDABLE, false);
		stil2.put(mxConstants.STYLE_ROUNDED, true);
		stil2.put(mxConstants.STYLE_AUTOSIZE, true);

		mxStylesheet foo = new mxStylesheet();
		foo.setDefaultEdgeStyle(stil);
		//foo.setDefaultVertexStyle(stil2);
		graph.setStylesheet(foo);
		
		graph.setCellStyle( "edgeStyle=mxEdgeStyle.orthoConnector" );
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setDragEnabled(false);
		getContentPane().add(graphComponent);
		graphComponent.getGraphControl().addMouseListener(new MouseListener());
		graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, new EdgeListener(this));
		graphComponent.addKeyListener(new KeyEventListener(this));
		
		
		
		graph.getModel().endUpdate();
	}

	private void buildMenus(){
		MenuListener ml = new MenuListener(this);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		exportXMLMenuItm = new JMenuItem("Export XML");
		fileMenu.add(exportXMLMenuItm);
		exportXMLMenuItm.addActionListener(ml);
		
		setDTDMenuItm = new JMenuItem("Select DTD");
		fileMenu.add(setDTDMenuItm);
		setDTDMenuItm.addActionListener(ml);
		
		this.setJMenuBar(menuBar);
		
		popup = new JPopupMenu();
		propMenuItm = new JMenuItem("Properties");
		propMenuItm.addActionListener(ml);
		topMenuItm = new JMenuItem("Set as top");
		topMenuItm.addActionListener(ml);
		popup.add(topMenuItm);
		popup.add(propMenuItm);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}

	
	//Listeners
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean addOperator(String opName){
		graph.getModel().beginUpdate();
		try
		{
			System.out.println(opName);
			System.out.println(operatorTemplates.get(opName));
			GraphNode newNode = new GraphNode(operatorTemplates.get(opName), graph, parent);
			queryPlan.addOperatorInstance(newNode);
			newNode.draw();
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		return true;
	}
	
	public boolean removeConnection(mxCell connectionEdge){
		GraphNode.IOPort out = (GraphNode.IOPort) connectionEdge.getSource();// gives the source cell
		GraphNode.IOPort in = (GraphNode.IOPort) connectionEdge.getTarget();//gives the target cell
		
		GraphNode sourceOp = (GraphNode)out.getParent().getValue();
		GraphNode sinkOp = (GraphNode)in.getParent().getValue();
		boolean result = sinkOp.disconnect(sourceOp);
		mxCell[] toRemove = {connectionEdge};
		graph.removeCells(toRemove);
		
		return result;
	}
	public boolean removeOperator(mxCell operatorNode){
		GraphNode op;
		if (operatorNode instanceof GraphNode.IOPort){
			op = (GraphNode)operatorNode.getParent().getValue();
			operatorNode = (mxCell)operatorNode.getParent();
		}
		else{
			op = (GraphNode)operatorNode.getValue();
		}
		List<mxCell> edges = op.getOutgoingEdges();
		for (mxCell e: edges){
			removeConnection(e);
		}
		queryPlan.removeOperatorInstance(op);
		mxCell[] toRemove = {operatorNode};
		graph.removeCells(toRemove);
		return false;
	}

		

}
