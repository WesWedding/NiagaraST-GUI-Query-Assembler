package niagaraGUI;
import java.io.Serializable;
import java.util.Iterator;
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
	
	public GraphNode(OperatorTemplate opTemplate){
		super(opTemplate);
	}

	
	public String toString(){
		String s = new String();
		s += this.name + '\n';
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

}
