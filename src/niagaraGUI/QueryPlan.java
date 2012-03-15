package niagaraGUI;

import java.util.*;

public class QueryPlan {
    private String filename;
    static private Hashtable<String, OperatorTemplate> opTemplates;
    private List<Operator> opList;//
    private Operator top;//reference to the top operator
    private String queryName;
    private DTDInterpreter dtdInterp;
            
    public QueryPlan(String name, String filename) {
        opTemplates = new Hashtable<String, OperatorTemplate>();
        dtdInterp = new DTDInterpreter(filename);
        opTemplates = dtdInterp.getTemplates();
        
    }
    
    static public Hashtable<String, OperatorTemplate> getOpTemplates() {
        return opTemplates;
    }
    
    static public OperatorTemplate addTemplate(OperatorTemplate opTemplate) {
        return opTemplates.put(opTemplate.getName(), opTemplate);      
    }
    
    public void generateXML(String filename) {
        
    }
    public String[] getOperatorNames(){
    	if (opTemplates != null){
    		Set<String> opNameSet = opTemplates.keySet();
    		String[] opNameAry = new String[opNameSet.size()];
    		opNameAry = opNameSet.toArray(opNameAry);
    		
    		return opNameAry;
    	}
    	else return null;
    }
    
    public void setName(String name) {
        queryName = name;
    }
    
    public String getName() {
        return queryName;
    }
    
    // This design pattern is in place to ease future import if
    // additional types are added
    public Boolean parse(String filename) {
        return parseDTD(filename);   
    }
    public Boolean parse(String filename, String docType) {
        if(docType == null) {
        docType = "DTD";            
        }
        if(docType == "DTD") {
            return parseDTD(filename);
        } else return false;
    }

    private Boolean parseDTD(String filename) {
        return false;        
    }
    
    public String toString() {
        return null;        
    }
}
