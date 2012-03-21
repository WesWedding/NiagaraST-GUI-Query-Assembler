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
    protected HashMap<String, String> reqAttribs; //String name, String value
    protected HashMap<String,String> optAttribs;  //String name, String value
    protected String elements; //houses xml code for any internal elements like predicates for Select
    protected String name;//This is name of operator NOT id
    protected String comments;//Comments to be inserted with this operators XML code
    protected boolean isTop = false;
    protected org.jdom.Attribute a;
    public Operator(OperatorTemplate opTemplate) {
        a = new org.jdom.Attribute("ADB", "DHH");
        HashMap<String, Attribute> templateAttributes = opTemplate.getAttributes();
        reqAttribs = new HashMap<String,String>();
        optAttribs = new HashMap<String,String>();
        name = opTemplate.getName();
        //Sort out the required and optional attributes
        Iterator<Map.Entry<String, Attribute>> it = templateAttributes.entrySet().iterator();

        while (it.hasNext()) {
          Map.Entry<String, Attribute> entry = it.next();

          //Sort out the attributes into required and optionals
          if (entry.getValue().required == Attribute.REQUIRED_REQUIRED) {
              reqAttribs.put(entry.getKey(), null);
          } else if(entry.getValue().required == Attribute.REQUIRED_OPTIONAL) {
              optAttribs.put(entry.getKey(), null);
          }
        }
    }
    public String getName(){
    	return this.name;
    }
    public String getComments(){
        return this.comments;
    }
    public String getElements(){
        return this.elements;
    }
    public String getAttribute(String attrib){
    	if (reqAttribs.containsKey(attrib)){
    		return reqAttribs.get(attrib);
    	}
    	else if(optAttribs.containsKey(attrib)){
    		return optAttribs.get(attrib);
    	}
    	return null;
    }
    public boolean setAttribute(String name, String value) {
    	if (reqAttribs.containsKey(name)){
    		reqAttribs.put(name, value);
    		return true;
    	}
    	else if (optAttribs.containsKey(name)){
    		optAttribs.put(name, value);
    		return true;
    	}
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
    public boolean addInput(Operator input){
    	String inName = input.getAttribute("id");//get the id of input to add
    	if (inName == null || inName.isEmpty()){//make sure input has a valid name
    		System.out.println("Input node has no id");//no valid name, print warning and abort
    		return false;
    	}
    	String inputString = this.getAttribute("input");//get the input string for this op
		ArrayList<String> ins;//delcare an array list
		if (inputString == null || inputString.isEmpty()){//if the input string is empty
			ins = new ArrayList<String>();//make an empty array list
		}
		else{//input string is not empty
			//split input into ArrayList at spaces
			ins =  new ArrayList<String>(Arrays.asList(this.getAttribute("input").split(" ")));
		}
		if (ins.contains(inName)){//check if input is already present
			return false;//input already present, abort
		}
		else {//input is not yet present
			ins.add(inName);//add input to array list
			String allIns = "";// empty string to hold list of inputs
			for (String s : ins){//iterate over inputs
				allIns += s + " ";//append input name to string with space
			}
			allIns = allIns.trim();//remove trailing space
			this.setAttribute("input", allIns);//set intput attribute to this string of inputs
			return true;//success! return true
		}
    }
    public boolean removeInput(Operator input){
    	String inName = input.getAttribute("id");//get the name if input to remove from this
    	if (inName == null || inName.isEmpty()){//no name of input to remove
    		System.out.println("Input node has no id");//warn
    		return false;//no valid input name to remove, abort
    	}
    	String inputString = this.getAttribute("input");// get string listing input names for this
		ArrayList<String> ins;//declare an array list
		if (inputString == null || inputString.isEmpty()){//empty list to remove from
			return false;//nothing to remove, abort
		}
		else{//input string is not empty
			//split input string at spaces and make new array list
			ins =  new ArrayList<String>(Arrays.asList(this.getAttribute("input").split(" ")));
		}
		
		if (!ins.contains(inName)){//input is not present to be removed
			return false;//no input to remove, abort
		}
		else{//remove the input
			ins.remove(inName);//remove the desired input.
			
			//rebuild input string
			String allIns = "";// empty string to hold list of inputs
			for (String s : ins){//iterate over inputs
				allIns += s + " ";//append input name to string with space
			}
			allIns = allIns.trim();//remove trailing space
			this.setAttribute("input", allIns);//set intput attribute to this string of inputs
			return true;//success! return true
		}
    }
    public void setTop(boolean set){
    	this.isTop = set;
    }
}
