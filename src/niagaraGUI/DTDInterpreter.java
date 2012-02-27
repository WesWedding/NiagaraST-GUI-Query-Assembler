package niagaraGUI;
import java.io.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.xmlmiddleware.schemas.dtds.*;
import org.xmlmiddleware.xmlutils.*;


/**
 * 
 * @author Weston Wedding
 *
 *  DTDInterpreter is a class that, when given a DTD filename (that exists), will parse the
 *  DTD to determine what operators are available and populate a table with the information
 *  we need to create a query:  operator names, attributes (and if they're required), and
 *  possible sub elements
 *  
 *   This uses Java's SAX as well as the DTDParser packages from Ronald Bourret at
 *   http://www.rpbourret.com/dtdparser/index.htm
 */
public class DTDInterpreter extends DefaultHandler {
    private Hashtable<String, OperatorTemplate> templateLookup;
	//private XMLReader xr;
	private DTDParser dp;
	private DTD dtd;
	private String filename;
	
	
	public DTDInterpreter() {}
	public DTDInterpreter(String fileName) {
	    filename = fileName;
	    templateLookup = new Hashtable<String, OperatorTemplate>();
	    parse();
	}
	
	public boolean loadFile(String filename) { return false;}
	public Hashtable<String, OperatorTemplate> getTemplates() {return templateLookup;}
	
	private void parse() {
	    ElementType plan;
	    Hashtable<XMLName, ElementType> elements;
	    ArrayList<XMLName> elementNames;
	    try {
            //xr = XMLReaderFactory.createXMLReader();
            dp = new DTDParser();
            FileReader r = new FileReader(filename);
            dtd = dp.parseExternalSubset(new InputSource(r), null);        
        } catch (Exception e) {
            System.err.println("Exception: "
                    + e.getMessage());
        }
        XMLName planName = XMLName.create("plan");
        plan = (ElementType)((Hashtable<XMLName, ElementType>)dtd.elementTypes).get(planName);
        elements = (Hashtable<XMLName, ElementType>)plan.children;
        
        //The following trickery is done because Enumerations don't implement iterators.
        //This pattern was suggested at: http://www.wikijava.org/wiki/Iterate_through_an_Enumeration
        elementNames = new ArrayList<XMLName>(Collections.list((Enumeration<XMLName>)elements.keys()));

	}
}
