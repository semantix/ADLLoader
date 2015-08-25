package edu.mayo.samepage.utils;

import edu.mayo.samepage.adl.impl.adl.ADLLoader;
import org.openehr.adl.serializer.ArchetypeSerializer;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.DvText;
import org.openehr.jaxb.rm.Element;
import org.openehr.jaxb.rm.ItemSingle;
import org.openehr.jaxb.rm.MultiplicityInterval;

import java.io.IOException;
import java.net.URL;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADLUtils
{
    public static ObjectFactory of = new ObjectFactory();
    public static org.openehr.jaxb.rm.ObjectFactory ofrm = new org.openehr.jaxb.rm.ObjectFactory();

    public static String getArchetypeText(Archetype archetype)
    {
        if (archetype == null)
            return "";

        return ArchetypeSerializer.serialize(archetype);
    }

    public static String getTestArchetypeText()
    {
        String testAdl1 = "https://raw.githubusercontent.com/opencimi/archetypes/master/miniCIMI/CIMI-CORE-PARTY.party.v1.0.0.adls";
        String testAdl2 = "https://raw.githubusercontent.com/opencimi/archetypes/master/miniCIMI/CIMI-CORE-ACTOR.organization.v1.0.0.adls";

        try
        {
            Archetype arch = ADLLoader.loadFromURL(
                    new URL(testAdl2));

            return ADLUtils.getArchetypeText(arch);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static CComplexObject getTestDefinition()
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
        item.setExistence(mult);
        cComplexObject.getAttributes().add(item);

        return cComplexObject;
    }

    public static ArchetypeTerminology getTestTerminology()
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
