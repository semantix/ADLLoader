package edu.mayo.samepage.adl.impl.adl2;

import edu.mayo.samepage.adl.IF.ADLServices;
import junit.framework.TestCase;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    public void testGetArchetype()
    {
        ADLServices  adlServices = new ADL2ServicesImpl();
        assertNotNull(adlServices.createArchetype("testArchetype"));
    }
}