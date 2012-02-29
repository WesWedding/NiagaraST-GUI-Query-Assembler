package niagaraGUI;

import java.util.*;

public class OperatorTemplate {
    private String name;
    private HashMap<String, attributeType> attributes;
    private HashMap<String, attributeType> subElements;
    
    static public enum attributeType {REQUIRED, OPTIONAL}
    
    public HashMap<String, attributeType> getAttributes() {
        return attributes;
    }
    
    public HashMap<String, attributeType> getSubElements() {
        return subElements;
    }
    
    public void setName(String inName) {
        name = inName;
    }
    
    public String getName() {
        return name;
    }
}
