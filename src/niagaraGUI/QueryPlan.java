package niagaraGUI;

import java.util.*;

public class QueryPlan {
    private String filename;
    static private Hashtable<String, OperatorTemplate> opTemplates;//Table of operator templates indexed by operator name
    private List<Operator> opList;//List of operator Instances in the current query plan
    private Operator top;//reference to the top operator
    private String queryName;//name of the query
    private DTDInterpreter dtdInterp;
            
    public QueryPlan(String name, String filename) {
        opTemplates = new Hashtable<String, OperatorTemplate>();
        dtdInterp = new DTDInterpreter(filename);
        opTemplates = dtdInterp.getTemplates();
        opList = new ArrayList<Operator>();
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
    public boolean addOperatorInstance(Operator newOp){
    	if (opList.contains(newOp)){
    		return false;
    	}
    	else{
    		opList.add(newOp);
    		return true;
    	}
    	
    }
    public boolean removeOperatorInstance(Operator toRemove){
    	if (opList.contains(toRemove)){
    		opList.remove(toRemove);
    		return true;
    	}
    	else{
    		return false;
    	}
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
    
    public void setTop(Operator newTop){
    	if (top != null){
    		top.isTop = false;
    		top = newTop;
    		top.isTop = true;
    	}
    }
}
