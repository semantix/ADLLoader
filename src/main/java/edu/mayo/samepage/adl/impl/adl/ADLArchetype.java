package edu.mayo.samepage.adl.impl.adl;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.CodePhrase;
import org.openehr.jaxb.rm.ResourceDescription;

/**
 * Created by dks02 on 8/19/15.
 */
public class ADLArchetype
{
    private Archetype archetype_ = null;
    private ADLMetaData meta_ = new ADLMetaData();
    private ADLArchetypeHelper helper_ = new ADLArchetypeHelper();

    public ADLArchetype(String name,
                        ADLMetaData meta,
                        ADLArchetypeHelper helper)
    {
        if (meta != null)
            meta_ = meta;

        if (helper != null)
            helper_ = helper;

        archetype_ = helper_.createArchetype();

        if (meta_.getParentID() != null)
            archetype_.setParentArchetypeId(meta_.getParentID());

        archetype_.setAdlVersion(meta_.getADLVersion());
        archetype_.setRmRelease(meta_.getRMRelaseVersion());
        archetype_.setIsGenerated(meta_.getIsGenerated());

        archetype_.setArchetypeId(meta_.createArchetypeId(name));

        CodePhrase language = meta_.getOriginalLanguage();
        if (language == null)
            language = helper_.createCodePhrase(ADLConstants.ARCH_DEFAULT_LANGUAGE_CODE,
                                                ADLConstants.ARCH_DEFAULT_LANGUAGE_VALUE);

        archetype_.setOriginalLanguage(language);

        ResourceDescription desc = helper_.createResourceDescription(
                meta_.getCopyright(),
                meta_.getLifeCycleState(),
                meta_.getResourcePackageURI(),
                null);

        archetype_.setDescription(desc);

        CComplexObject topConstraint =
                helper_.createComplexObjectConstraint(meta_.getRMClassName(), meta_.createNewId());
        setDefinition(topConstraint, name);
    }

    public Archetype getArchetype()
    {
        return this.archetype_;
    }

    public void setDefinition(CComplexObject definition, String description)
    {
        if (archetype_ == null)
            return;

        archetype_.setDefinition(definition);
        addTerminologyTerm(null, definition.getNodeId(), description);
    }

    public CComplexObject getDefinition()
    {
        return archetype_.getDefinition();
    }



    public void addToComplexObjectConstraint(CComplexObject constraint, CAttribute attribute)
    {
        if (constraint == null)
            return;

        if (attribute == null)
            return;

        constraint.getAttributes().add(attribute);
    }

    public void addToComplexObjectConstraint(CComplexObject constraint, CAttributeTuple attributeTuple)
    {
        if (constraint == null)
            return;

        if (attributeTuple == null)
            return;

        constraint.getAttributeTuples().add(attributeTuple);
    }

    public void addTerminologyTerm(String termBindingSet,
                                   String termItemCode,
                                   String termItemValue)
    {
        if (archetype_ == null)
            return;

        String tCode = termItemCode;
        if (StringUtils.isEmpty(tCode))
            return;

        String tValue = termItemValue;
        if (StringUtils.isEmpty(tValue))
            tValue = "";

        String setName = termBindingSet;
        if (StringUtils.isEmpty(setName))
            setName = meta_.getDefaultTerminologySetName();

        ArchetypeTerminology archetypeTerminology = archetype_.getTerminology();
        if (archetypeTerminology == null)
            archetypeTerminology = helper_.createArchetypeTerminology();

        boolean added = false;
        for (TermBindingSet set : archetypeTerminology.getTermBindings())
        {
            if (!set.getTerminology().equals(setName))
                continue;

            for (TermBindingItem item : set.getItems())
            {
                if (!item.getCode().equals(tCode))
                    continue;

                item.setValue(tValue);
                added = true;
            }

            if (!added)
            {
                TermBindingItem newItem = helper_.createTermBindingItem(tCode, tValue);
                set.getItems().add(newItem);
                added = true;
            }
        }

        if (!added)
        {
            TermBindingSet tbs = helper_.createTermBindingSet(setName);
            tbs.getItems().add(helper_.createTermBindingItem(tCode, tValue));
            archetypeTerminology.getTermBindings().add(tbs);
        }

        archetype_.setTerminology(archetypeTerminology);
    }
}
