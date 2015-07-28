package edu.mayo.samepage.adl.impl.adl2;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import edu.mayo.samepage.adl.IF.ADLServices;
import org.openehr.jaxb.am.Archetype;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.ObjectFactory;
import org.openehr.jaxb.rm.*;

/**
 * Created by dks02 on 12/12/14.
 */
public class ADL2ServicesImpl implements ADLServices
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ADL2ServicesImpl.class);

    public Archetype createArchetype(String id)
    {
        logger.debug("Creating Archetype for %1", id);
        ObjectFactory of = new ObjectFactory();
        org.openehr.jaxb.rm.ObjectFactory ofrm = new org.openehr.jaxb.rm.ObjectFactory();
        //OpenEhrRmModel oehrrm = OpenEhrRmModel.getInstance();
        Archetype arch = of.createDifferentialArchetype();
        ArchetypeId aid = new ArchetypeId();
        aid.setValue("testID");
        arch.setArchetypeId(aid);

        ResourceDescription rd = ofrm.createResourceDescription();
        rd.setCopyright("Deepak");
        arch.setDescription(rd);

        CComplexObject cComplexObject = of.createCComplexObject();
        ItemSingle isingle = ofrm.createItemSingle();

        Element elm = ofrm.createElement();
        DvText dvt = ofrm.createDvText();
        dvt.setValue("Patient");
        elm.setName(dvt);
        isingle.setItem(elm);

        cComplexObject.setRmTypeName("Item");
        arch.setDefinition(cComplexObject);

        return arch;
    }
}
