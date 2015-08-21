package edu.mayo.samepage.adl.impl.adl2;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.utils.ADLUtils;
import junit.framework.TestCase;
import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.am.ArchetypeTerminology;
import org.openehr.jaxb.am.CComplexObject;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    public void testGetArchetype()
    {
        ADLServices  adlServices = new ADL2ServicesImpl();
        Archetype testArch = adlServices.initializeArchetype("testArchetype", null, null);
        assertNotNull(testArch);

        CComplexObject definition = ADLUtils.getTestDefinition();
        adlServices.updateDefinition(testArch, definition);

        ArchetypeTerminology terminology = ADLUtils.getTestTerminology();
        adlServices.updateTerminology(testArch, terminology);

        String archText = ADLUtils.getArchetypeText(testArch);
        assertNotNull(archText);
    }

 }