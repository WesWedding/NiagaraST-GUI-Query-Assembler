package niagaraGUI;
import java.util.Hashtable;



public class DTDInterpreter {
	private Hashtable<String, OperatorTemplate> templateLookup; 
	
	
	public DTDInterpreter() {}
	public DTDInterpreter(String filename) {
	    templateLookup = new Hashtable<String, OperatorTemplate>();
	}
	
	public boolean loadFile(String filename) { return false;}
	public Hashtable<String, OperatorTemplate> getTemplates() {return templateLookup;}
}
