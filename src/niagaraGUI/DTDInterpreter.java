package niagaraGUI;
import java.util.Hashtable;



public class DTDInterpreter {
	private Hashtable<String, OperatorTemplate> templateLookup; 
	
	public boolean loadFile(String filename) { return false;}
	public Hashtable<String, OperatorTemplate> getTemplates() {return new Hashtable<String, OperatorTemplate>();}
}
