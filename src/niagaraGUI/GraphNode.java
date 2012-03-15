package niagaraGUI;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;

public class GraphNode extends Operator {
	private mxCell portIn;
	private mxCell portOut;
	private mxCell mainBox;
	private mxCell opGroup;
	
	public GraphNode(OperatorTemplate opTemplate){
		super(opTemplate);
	}
	
	public String getName(){
		return "OPERATOR NAME";
	}
	
	public String toString(){
		String s = new String();
		s += "OPERATOR NAME" + '\n';
		s += "Required Attributes: " + '\n';
		Set<String> reqAttKeys = reqAttribs.keySet();
		Iterator rkeysit = reqAttKeys.iterator();
		while(rkeysit.hasNext()){
			s += '\t' + (String)rkeysit.next() +'\n';
		}
		s += "Optional Attributes: " + '\n';
		Set<String> optAttKeys = optAttribs.keySet();
		Iterator okeysit = optAttKeys.iterator();
		while(okeysit.hasNext()){
			s += '\t' + (String)okeysit.next() +'\n';
		}
		return s;
	}
	
	public void draw(mxGraph graph, Object parent){
		int PORT_DIAMETER=15;
		int PORT_RADIUS=10;
		mainBox = (mxCell) graph.insertVertex(parent, null, this, 20, 20, 80,
				30, "align=left");
		graph.updateCellSize(mainBox);
		mainBox.setConnectable(false);
		
		mxGeometry geo1 = new mxGeometry(-0.05, 0.5, PORT_DIAMETER,
				PORT_DIAMETER);
		

		

		// Because the origin is at upper left corner, need to translate to
		// position the center of port correctly
		geo1.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo1.setRelative(true);

		portIn = new mxCell(">", geo1,null);
		portIn.setVertex(true);

		mxGeometry geo2 = new mxGeometry(1.05, 0.5, PORT_DIAMETER,
				PORT_DIAMETER);
		geo2.setOffset(new mxPoint(-PORT_RADIUS, -PORT_RADIUS));
		geo2.setRelative(true);

		portOut = new mxCell(">", geo2,null);
		portOut.setVertex(true);
		
		graph.addCell(portIn, mainBox);
		graph.addCell(portOut, mainBox);
	}

}
