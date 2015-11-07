package com.omnicrola.panoptes.data;

import org.junit.Test;

import static com.omnicrola.test.util.TestUtil.assertEquality;
import static org.junit.Assert.*;

/**
 * Created by omnic on 10/31/2015.
 */
public class WorkStatementTest extends ModelTest {

    @Test
    public void testEqualityAndHash() throws Exception {
        WorkStatement workStatement = new WorkStatement("project name", "my favorite client", "Afe922", "SOW282", 23.53f);
        WorkStatement equal = new WorkStatement("project name", "my favorite client", "Afe922", "SOW282", 23.53f);

        WorkStatement notEqual1 = new WorkStatement("projec name", "my favorite client", "Afe922", "SOW282", 23.53f);
        WorkStatement notEqual2 = new WorkStatement("project name", "my avorite client", "Afe922", "SOW282", 23.53f);
        WorkStatement notEqual3 = new WorkStatement("project name", "my favorite client", "fe622", "SOW282", 23.53f);
        WorkStatement notEqual4 = new WorkStatement("project name", "my favorite client", "Afe922", "S4W282", 23.53f);
        WorkStatement notEqual5 = new WorkStatement("project name", "my favorite client", "Afe922", "SOW282", 25.53f);

        assertEquality(workStatement, equal, notEqual1, notEqual2, notEqual3, notEqual4, notEqual5);
    }

    @Test
    public void testHasCorrectFields() throws Exception {
        setClassUnderTest(WorkStatement.class);

        checkFieldIsPresent("projectName", String.class);
        checkRequiredFieldIsPresent("client", String.class);
        checkRequiredFieldIsPresent("projectCode", String.class);
        checkRequiredFieldIsPresent("sowCode", String.class);
        checkRequiredFieldIsPresent("billableRate", float.class);
    }
}