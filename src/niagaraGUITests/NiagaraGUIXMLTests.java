package niagaraGUITests;

import niagaraGUI.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Hashtable;
import java.util.Vector;


public class NiagaraGUIXMLTests {
    
    
    //Tests only that loading a DTDInterpreter with a filename will place the correct keys
    //into the hashtable.  This doesn't verify that the corresponding buckets have the correct
    // objects in them.
    @Test
    public void testOperatorTemplateTablePopulationFromConstructorSuccessPresentOnly() {
        
        DTDInterpreter interpreter = new DTDInterpreter("exampleFile1.dtd");
        Vector<String> operatorStrings;
        operatorStrings.add("select"); operatorStrings.add("unnest"); operatorStrings.add("sort"); operatorStrings.add("expression"); operatorStrings.add("avg");
        operatorStrings.add("slidingAvg"); operatorStrings.add("sum"); operatorStrings.add("slidingSum"); operatorStrings.add("max");
        operatorStrings.add("slidingMax"); operatorStrings.add("count"); operatorStrings.add("slidingCount"); operatorStrings.add("incrmax"); operatorStrings.add("incravg"); operatorStrings.add("select"); operatorStrings.add("dup"); operatorStrings.add("union"); operatorStrings.add("join");
        operatorStrings.add("dtdscan"); operatorStrings.add("resource"); operatorStrings.add("constant"); operatorStrings.add("timer");
        operatorStrings.add("prefix"); operatorStrings.add("punctuate"); operatorStrings.add("send"); operatorStrings.add("display"); operatorStrings.add("topn"); 
        operatorStrings.add("firehosescan"); operatorStrings.add("filescan"); operatorStrings.add("construct");
        operatorStrings.add("bucket"); operatorStrings.add("partitionavg"); operatorStrings.add("partitionmax");
        operatorStrings.add("accumulate"); operatorStrings.add("nest"); operatorStrings.add("magic_construct");
        operatorStrings.add("windowCount"); operatorStrings.add("windowMax"); operatorStrings.add("windowAverage "); operatorStrings.add("windowJoin");
        operatorStrings.add("store"); operatorStrings.add("load"); operatorStrings.add("xmlscan");
        Hashtable<String, OperatorTemplate> table = interpreter.getTemplates();
        
        for (String s : operatorStrings) {
            table.containsKey(s);
            assertEquals("Operator "+s+"not found", 0,0);
        }
    }

}
