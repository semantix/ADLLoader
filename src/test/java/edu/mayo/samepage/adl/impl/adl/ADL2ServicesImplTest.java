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

        testArch.addArchetypeTerm("id2", null, "testDefinition2", null, "http://snomed.info/1234567");
        testArch.addArchetypeTerm("id3", "es", "testDefinitionES3", null, "http://snomed.info/345678");
        testArch.addArchetypeTerm("id4", "es", "testDefinitionES4", "loinc", "http://loinc.terms/5672rf");
        testArch.addArchetypeTerm("id5", null, "testDefinition5", "loinc", "http://snomed.info/3444en");
        String archText = adlServices.serialize(testArch);
        assertNotNull(archText);
    }
 }