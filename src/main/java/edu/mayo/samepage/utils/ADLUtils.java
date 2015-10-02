package edu.mayo.samepage.utils;

import edu.mayo.samepage.adl.impl.adl.ADLLoader;
import edu.mayo.samepage.adl.impl.adl.ADLMetaData;
import edu.mayo.samepage.adl.impl.adl.am.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import org.openehr.adl.serializer.ArchetypeSerializer;
import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.am.ObjectFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADLUtils
{
    public static ObjectFactory of = new ObjectFactory();
    public static org.openehr.jaxb.rm.ObjectFactory ofrm = new org.openehr.jaxb.rm.ObjectFactory();

    public static ADLMetaData initCIMIMetaData()
    {
        ADLSettings adlSettings = new ADLSettings();

        ADLRMSettings adlrmSettings = new ADLRMSettings(ADLRM.OPENCIMI);

        ADLMetaData cimi = new ADLMetaData(adlSettings, adlrmSettings);

        cimi.setDefaultTerminologySetName("snomed-ct");

        return cimi;
    }

    public static ADLMetaData initOpenEHRMetaData()
    {
        ADLSettings adlSettings = new ADLSettings();

        ADLRMSettings adlrmSettings = new ADLRMSettings(ADLRM.OPENEHR);

        return new ADLMetaData(adlSettings, adlrmSettings);
    }

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
}
