package niagaraGUI;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

public class GraphNode extends Operator implements Serializable {
	private mxCell portIn;
	private mxCell portOut;
	private mxCell mainBox;
	private mxCell opGroup;
	private mxGraph graph;
	private Object parent;
	
	
	public class IOPort extends mxCell{
		private boolean input = false;
		IOPort(Object value, mxGeometry geometry, String style, boolean input){
			super(value,geometry,style);
			this.input = input;
		}
		public boolean isInput(){
			return this.input;
		}
		public boolean isOutput(){
			return ! this.input;
		}
	}
	
	public GraphNode(OperatorTemplate opTemplate, mxGraph grph, Object prnt){
		super(opTemplate);
		graph = grph;
		parent = prnt;
	}

	
	public String toString(){
		String s = new String();
		s += "\n"+this.name + '\n';
		s += "Required Attributes: " + '\n';
		Set<String> reqAttKeys = reqAttribs.keySet();
		String val;
		for(String k : reqAttKeys){
			val = reqAttribs.get(k);
			s += '\t' + k + ":\t"+val +'\n';
		}
		s += "Optional Attributes: " + '\n';
		Set<String> optAttKeys = optAttribs.keySet();
		for(String k : optAttKeys){
			val = optAttribs.get(k);
			s += '\t' + k+":\t"+val +'\n';
		}
		return s;
	}
	
	public void draw(){
		int PORT_DIAMETER=15;
		int PORT_RADIUS=10;
		mainBox = (mxCell) graph.insertVertex(parent, null, this, 20, 20, 80,
				30, "align=left");
		//mainBox.setStyle("")
		graph.updateCellSize(mainBox);
		mainBox.setConnectable(false);
		mxGeometry geo1 = new mxGeometry(-0.05, 0.5, PORT_DIAMETER,
				PORT_DIAMETER);
		


		// Because the origin is at upper left corner, need to translate to
		// position the center of port correctly
		geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);
		geo1.setAlternateBounds(new mxRectangle(0,0,geo1.getHeight(),geo1.getWidth()));
	

		portIn = new IOPort(">", geo1,null,true);
		portIn.setVertex(true);

		mxGeometry geo2 = new mxGeometry(1.05, 0.5, PORT_DIAMETER,
				PORT_DIAMETER);
		geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo2.setRelative(true);
		geo2.setAlternateBounds(new mxRectangle(0,0,geo2.getHeight(),geo2.getWidth()));

		portOut = new IOPort(">", geo2,null,false);
		portOut.setVertex(true);
		
		graph.addCell(portIn, mainBox);
		graph.addCell(portOut, mainBox);
	}
	
	public void update(){
		graph.getModel().beginUpdate();
		graph.setAutoSizeCells(true);
		graph.refresh();
		graph.getModel().endUpdate();
	}
	
	public boolean connect(GraphNode newInput){
		boolean result = this.addInput(newInput);
		this.update();
		return result;
	}
	
	public boolean disconnect(GraphNode toRemove){
		boolean result = this.removeInput(toRemove);
		this.update();
		return result;
	}
	public List<mxCell> getOutgoingEdges(){
		//returns a hash table where the key is a graph node connecting to this GraphNode
		//and the value is the edge of type mxCell which connects this to the key GraphNode
		List<mxCell> rtn = new ArrayList<mxCell>();
		int edgeCount = this.portOut.getEdgeCount();
		for (int i=0; i<edgeCount; i++){
			mxCell m = (mxCell) this.portOut.getEdgeAt(i);
			rtn.add(m);
		}
		return rtn;
		
	}
	public boolean updateSinks(String oldID){
		//this method updates all nodes which receive data from this node with this nodes new name
		
	}

}
