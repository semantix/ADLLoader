package edu.mayo.samepage.adl.impl.adl2;

import ch.qos.logback.classic.Logger;
import edu.mayo.samepage.adl.IF.ADLServices;
import org.openehr.adl.serializer.ArchetypeSerializer;
import org.slf4j.LoggerFactory;

/**
 * Created by dks02 on 12/12/14.
 */
public class ADL2ServicesImpl implements ADLServices
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ADL2ServicesImpl.class);


    public ADLArchetype createArchetype(String name, ADLMetaData metaData, ADLArchetypeHelper helper)
    {
        return new ADLArchetype(name, metaData, helper, null);
    }

    public String serialize(ADLArchetype archetype)
    {
        if (archetype == null)
            return null;

        return ArchetypeSerializer.serialize(archetype.getArchetype());
    }
}
