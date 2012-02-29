package niagaraGUI;

import java.util.*;
import niagaraGUI.OperatorTemplate.attributeType;

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
        
        HashMap<String, attributeType> templateAttributes = opTemplate.getAttributes();
        
        
        //Sort out the required and optional attributes
        Iterator<Map.Entry<String, attributeType>> it = templateAttributes.entrySet().iterator();

        while (it.hasNext()) {
          Map.Entry<String, attributeType> entry = it.next();

          //Sort out the attributes into required and optionals
          if (entry.getValue() == attributeType.REQUIRED) {
              reqAttribs.put(entry.getKey(), null);
          } else if(entry.getValue() == attributeType.OPTIONAL) {
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
