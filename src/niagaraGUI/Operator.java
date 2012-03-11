package niagaraGUI;

import java.util.*;
//import niagaraGUI.OperatorTemplate.DefaultValue;
import org.xmlmiddleware.schemas.dtds.*;

/**
 * 
 * @author Weston Wedding
 *
 */

public class Operator {
    private HashMap<String, String> reqAttribs; //String name, String value
    private HashMap<String,String> optAttribs;  //String name, String value
    private Vector<String> elements;
    
    public Operator(OperatorTemplate opTemplate) {
        
        HashMap<String, Attribute> templateAttributes = opTemplate.getAttributes();
        
        
        //Sort out the required and optional attributes
        Iterator<Map.Entry<String, Attribute>> it = templateAttributes.entrySet().iterator();

        while (it.hasNext()) {
          Map.Entry<String, Attribute> entry = it.next();

          //Sort out the attributes into required and optionals
          if (entry.getValue().required == Attribute.REQUIRED_OPTIONAL) {
              reqAttribs.put(entry.getKey(), null);
          } else if(entry.getValue().required == Attribute.REQUIRED_OPTIONAL) {
              optAttribs.put(entry.getKey(), null);
          }
        }
    }
    
    public Boolean setAttribute(String name, String value) {
        return false;
    }
    
    // Attribute "getter" methods.  There are three distinct methods
    // even though I don't see a cause for using the last two, yet. - Wes
    
    public HashMap<String,String> getAttributes() {
        HashMap<String,String> allAttribs = new HashMap<String,String>();
        allAttribs.putAll(reqAttribs);
        allAttribs.putAll(optAttribs);
        return allAttribs;
    }
    
    public HashMap<String,String> getRequiredAttributes() {
        return reqAttribs;
    }
    public HashMap<String,String> getOptionalAttributes() {
        return optAttribs;
    }
}
