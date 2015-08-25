package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.adl.services.ADL2ServicesImpl;
import edu.mayo.samepage.adl.services.CIMIRMMetaData;
import junit.framework.TestCase;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    public void testGetArchetype()
    {
        CIMIRMMetaData cimi = new CIMIRMMetaData();
        ADLServices  adlServices = new ADL2ServicesImpl();

        ADLArchetype testArch = adlServices.createArchetype("testArchetype", cimi, null);
        assertNotNull(testArch);

        String archText = adlServices.serialize(testArch);
        assertNotNull(archText);
    }

 }