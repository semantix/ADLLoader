package edu.mayo.samepage.adl.impl.adl2;

import ch.qos.logback.classic.Logger;
import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.adl.IF.ADLSetting;
import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.am.ArchetypeTerminology;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.ObjectFactory;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dks02 on 12/12/14.
 */
public class ADL2ServicesImpl implements ADLServices
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ADL2ServicesImpl.class);

    public Archetype initializeArchetype(String name,
                                         CComplexObject definition,
                                         List<ADLSetting> settings)
    {
        logger.debug("Creating Archetype for:" + name);

        ADLBuilder ab = new ADLBuilder(settings);

        ObjectFactory of = new ObjectFactory();

        Archetype arch = of.createDifferentialArchetype();

        arch.setAdlVersion(ab.getADLVersion());
        arch.setRmRelease(ab.getRMRelaseVersion());
        arch.setIsGenerated(true);
        arch.setArchetypeId(ab.createArchetypeId(name));

        arch.setOriginalLanguage(ab.getOriginalLanguage());

        arch.setDescription(ab.createResourceDescription());

        if (definition != null)
            arch.setDefinition(definition);

        return arch;
    }


    public void updateDefinition(Archetype archetype, CComplexObject definition)
    {
        if (archetype == null)
            return ;

        archetype.setDefinition(definition);
    }

    public void updateTerminology(Archetype archetype, ArchetypeTerminology terminology)
    {
        if (archetype == null)
            return;

        if (terminology == null)
            return;

        archetype.setTerminology(terminology);
    }
}
