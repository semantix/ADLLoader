package edu.mayo.samepage.adl.IF;

import edu.mayo.samepage.adl.impl.adl2.ADLArchetype;
import edu.mayo.samepage.adl.impl.adl2.ADLArchetypeHelper;
import edu.mayo.samepage.adl.impl.adl2.ADLMetaData;

/**
 * Created by dks02 on 7/28/15.
 */
public interface ADLServices
{
    public ADLArchetype createArchetype(String name, ADLMetaData metaData, ADLArchetypeHelper helper);
    public String serialize(ADLArchetype archetype);
}
