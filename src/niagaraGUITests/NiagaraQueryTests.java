package niagaraGUITests;

import static org.junit.Assert.fail;
import niagaraGUI.*;

import org.junit.Before;
import org.junit.Test;

public class NiagaraQueryTests {
    QueryPlan simpleQP;

    @Before
    public void setUp() throws Exception {
        simpleQP = new QueryPlan("blankQuery", "nofileName");
    }

    @Test
    public void testGenerateXML() {
        fail("Not yet implemented");
    }

    @Test
    public void testParseString() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetName() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetName() {
        fail("Not yet implemented");
    }

    @Test
    public void testParseStringString() {
        fail("Not yet implemented");
    }
    
    @Test
    public void testAddOperator() {
        simpleQP.addOperator(new Operator("anOp"));
    }
    
    public void testRemoveOperator() {
        simpleQP.removeOperator("anOp");
    }
    
    @Test
    public void testToString() {
        fail("Not yet implemented");
    }
}
