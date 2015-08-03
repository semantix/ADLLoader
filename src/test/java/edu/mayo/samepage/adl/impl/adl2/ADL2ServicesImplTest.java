package edu.mayo.samepage.adl.impl.adl2;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.utils.ADLUtils;
import junit.framework.TestCase;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.*;
import org.openehr.jaxb.am.ObjectFactory;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    private ObjectFactory of = new ObjectFactory();
    private org.openehr.jaxb.rm.ObjectFactory ofrm = new org.openehr.jaxb.rm.ObjectFactory();

    public void testGetArchetype()
    {
        ADLServices  adlServices = new ADL2ServicesImpl();
        Archetype testArch = adlServices.initializeArchetype("testArchetype", null, null);
        assertNotNull(testArch);

        CComplexObject definition = getTestDefinition();
        adlServices.updateDefinition(testArch, definition);

        ArchetypeTerminology terminology = getTestTerminology();
        adlServices.updateTerminology(testArch, terminology);

        String archText = ADLUtils.getArchetypeText(testArch);
        assertNotNull(archText);
    }

    private CComplexObject getTestDefinition()
    {

        CComplexObject cComplexObject = of.createCComplexObject();
        ItemSingle isingle = ofrm.createItemSingle();

        Element elm = ofrm.createElement();
        DvText dvt = ofrm.createDvText();
        dvt.setValue("Patient");
        elm.setName(dvt);
        isingle.setItem(elm);

        cComplexObject.setRmTypeName("ITEM-GROUP");
        cComplexObject.setNodeId("id1");

        CAttribute item = of.createCAttribute();
        item.setRmAttributeName("item");
        MultiplicityInterval mult = ofrm.createMultiplicityInterval();
        mult.setLower(0);
        mult.setUpper(1);
        cComplexObject.getAttributes().add(item);

        return cComplexObject;
    }

    private ArchetypeTerminology getTestTerminology()
    {
        TermBindingSet tbs = of.createTermBindingSet();
        tbs.setTerminology("snomed-ct");
        TermBindingItem ti1 = of.createTermBindingItem();
        ti1.setCode("id1");
        ti1.setValue("testID1 value");
        TermBindingItem ti2 = of.createTermBindingItem();
        ti2.setCode("id2");
        ti2.setValue("testID2 value");
        tbs.getItems().add(ti1);
        tbs.getItems().add(ti2);

        ArchetypeTerminology aterm = of.createArchetypeTerminology();
        aterm.getTermBindings().add(tbs);

        return aterm;
    }
}