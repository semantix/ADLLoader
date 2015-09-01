package edu.mayo.samepage.adl.IF;

import edu.mayo.samepage.adl.impl.adl.ADLArchetype;
import edu.mayo.samepage.adl.impl.adl.ADLArchetypeHelper;
import edu.mayo.samepage.adl.impl.adl.ADLMetaData;

/**
 * Created by dks02 on 7/28/15.
 */
public interface ADLServices
{
    public ADLArchetype createArchetype(String name, String description, ADLMetaData metaData, ADLArchetypeHelper helper);
    public String serialize(ADLArchetype archetype);
}
