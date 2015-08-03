package edu.mayo.samepage.adl.IF;

import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.am.ArchetypeTerminology;
import org.openehr.jaxb.am.CComplexObject;

import java.util.List;

/**
 * Created by dks02 on 7/28/15.
 */
public interface ADLServices
{
    public Archetype initializeArchetype(String name, CComplexObject definition, List<ADLSetting> initalSettings);
    public void updateDefinition(Archetype archetype, CComplexObject definition);
    public void updateTerminology(Archetype archetype, ArchetypeTerminology terminology);
}
