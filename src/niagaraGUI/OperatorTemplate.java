package niagaraGUI;

import java.util.*;
import org.xmlmiddleware.schemas.dtds.*;

public class OperatorTemplate implements java.io.Serializable{
    private String name;
    private HashMap<String, Attribute> attributes;
    private HashMap<String, ElementType> subElements;
    
   // static public enum DefaultValue {VAL, REQUIRED, IMPLIED}
    
    /** Contructors **/
    public OperatorTemplate() {
        init();
    }
    
    public OperatorTemplate(String inName) {
        name = inName;
        init();
    }
    
    private void init() {
        attributes = new HashMap<String, Attribute>();
        subElements = new HashMap<String, ElementType>();        
    }
    /** End Constructors **/
    
    public void addAttribute(Attribute attrib) {
        String name = attrib.name.getLocalName();
        attributes.put(name, attrib);
    }
    
    public HashMap<String, Attribute> getAttributes() {
        return attributes;
    }
    
    public void addSubElement(ElementType elem) {
        subElements.put(elem.name.getLocalName(), elem);
    }
    
    public HashMap<String, ElementType> getSubElements() {
        return subElements;
    }
    
    public void setName(String inName) {
        name = inName;
    }
    
    public String getName() {
        return name;
    }
}
