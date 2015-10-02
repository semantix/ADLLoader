package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.impl.adl.am.ADLConstants;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMFactory;
import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.CodePhrase;
import org.openehr.jaxb.rm.ResourceDescription;
import org.openehr.jaxb.rm.StringDictionaryItem;

/**
 * Created by dks02 on 8/19/15.
 */
public class ADLArchetype
{
    private ADLAMConstraintFactory am_ = new ADLAMConstraintFactory();
    private ADLRMFactory rm_ = new ADLRMFactory();

    private Archetype archetype_ = null;
    private ADLMetaData meta_ = null;

    public ADLArchetype(String name,
                        String description,
                        ADLMetaData meta)
    {
        if (meta != null)
            meta_ = meta;

        archetype_ = am_.createArchetype();

        if (meta_.getADLSettings().getParentID() != null)
            archetype_.setParentArchetypeId(meta_.getADLSettings().getParentID());

        archetype_.setAdlVersion(meta_.getADLSettings().getADLVersion());
        archetype_.setRmRelease(meta_.getADLRMSettings().getRMRelaseVersion());
        archetype_.setIsGenerated(meta_.getADLSettings().getIsGenerated());

        archetype_.setArchetypeId(meta_.createArchetypeId(name));

        CodePhrase language = meta_.getADLSettings().getOriginalLanguage();

        if (language == null)
            language = rm_.createCodePhrase(rm_.createTerminologyId(ADLConstants.ARCH_DEFAULT_LANGUAGE_VALUE),
                                            ADLConstants.ARCH_DEFAULT_LANGUAGE_CODE);

        archetype_.setOriginalLanguage(language);

        ResourceDescription desc = rm_.createResourceDescription(
                meta_.getADLSettings().getCopyright(),
                meta_.getADLSettings().getLifeCycleState(),
                meta_.getADLSettings().getResourcePackageURI(),
                null);

        archetype_.setDescription(desc);

        CComplexObject topConstraint =
                am_.createComplexObjectConstraint(meta_.getADLRMSettings().getRMClassName(),
                                                    meta_.createNewId(), null);
        setDefinition(topConstraint, name, description);
    }

    public Archetype getArchetype()
    {
        return this.archetype_;
    }

    public ArchetypeTerminology getTerminology()
    {
        ArchetypeTerminology archetypeTerminology = archetype_.getTerminology();
        if (archetypeTerminology == null)
            archetypeTerminology = am_.createArchetypeTerminology();

        return archetypeTerminology;
    }

    public void setDefinition(CComplexObject definition, String definitionText, String descriptionText)
    {
        if (archetype_ == null)
            return;

        archetype_.setDefinition(definition);
        addArchetypeTerm(definition.getNodeId(), null, definitionText, descriptionText, null, null);
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

    public void addArchetypeTerm(String termId,
                                 String termDefinitionSet,
                                 String termDefinition,
                                 String termDescription,
                                 String termBindingSet,
                                 String termItemBindingValue)
    {
        if (archetype_ == null)
            return;

        String tId = termId;
        if (StringUtils.isEmpty(tId))
            return;

        ArchetypeTerminology archetypeTerminology = getTerminology();

        addTermDefinition(archetypeTerminology, termDefinitionSet, termId, termDefinition, termDescription);

        if (termItemBindingValue != null)
            addTermBinding(archetypeTerminology, termBindingSet, termId, termItemBindingValue);

        archetype_.setTerminology(archetypeTerminology);
    }

    public void addTermDefinition(ArchetypeTerminology archetypeTerminology,
                                String language,
                                String termCode,
                                String termDefinition,
                                  String termDescription)
    {
        String tCode = termCode;
        if (StringUtils.isEmpty(tCode))
            return;

        String languageSet = language;
        if (StringUtils.isEmpty(languageSet))
            languageSet = archetype_.getOriginalLanguage().getCodeString();

        String definition = termDefinition;
        if (StringUtils.isEmpty(definition))
            definition = "";

        String description = termDescription;
        if (StringUtils.isEmpty(description))
            definition = "";

        boolean added = false;
        for (CodeDefinitionSet set : archetypeTerminology.getTermDefinitions())
        {
            if (!set.getLanguage().equals(languageSet))
                continue;

            for (ArchetypeTerm term : set.getItems()) {
                if (!term.getCode().equals(tCode))
                    continue;

                for (StringDictionaryItem dictionaryItem : term.getItems())
                {
                    if (am_.getTermDefinitionTextProperty().equals(dictionaryItem.getId()))
                    {
                        dictionaryItem.setValue(definition);
                        added = true;
                    }

                    if (am_.getTermDescriptionProperty().equals(dictionaryItem.getId()))
                    {
                        dictionaryItem.setValue(description);
                        added = true;
                    }
                }
            }

            if (!added)
            {
                set.getItems().add(am_.createArcehtypeTerm(tCode, definition, description));
                added = true;
            }

        }

        if (!added) {
            CodeDefinitionSet cds = am_.createCodeDefinitionSet(languageSet);
            cds.getItems().add(am_.createArcehtypeTerm(tCode, definition, description));
            archetypeTerminology.getTermDefinitions().add(cds);
        }
    }

    public void updateValueSet(String valueSetId, String... valueSetMembers)
    {
        String vsId = valueSetId;
        if (StringUtils.isEmpty(vsId))
            return;

        ArchetypeTerminology archetypeTerminology = getTerminology();

        boolean added = false;
        for (ValueSetItem valueSetItem : archetypeTerminology.getValueSets())
        {
            if (valueSetId.equals(valueSetItem.getId()))
            {
                for (String member : valueSetMembers)
                {
                    if (valueSetItem.getMembers().contains(member))
                        continue;

                    valueSetItem.getMembers().add(member);
                }
                added = true;
            }
        }

        if (!added)
        {
            ValueSetItem valueSetItem = am_.createValueSetItem(valueSetId, valueSetMembers);
            if (valueSetItem != null)
                archetypeTerminology.getValueSets().add(valueSetItem);
        }
    }

    public void addTermBinding(ArchetypeTerminology archetypeTerminology,
                               String termBindingSet,
                               String termCode,
                               String termBinding)
    {
        String tCode = termCode;
        if (StringUtils.isEmpty(tCode))
            return;

        String setName = termBindingSet;
        if (StringUtils.isEmpty(setName))
            setName = meta_.getDefaultTerminologySetName();

        String tBinding = termBinding;
        if (StringUtils.isEmpty(tBinding))
            tBinding = "";

        boolean added = false;
        for (TermBindingSet set : archetypeTerminology.getTermBindings())
        {
            if (!set.getTerminology().equals(setName))
                continue;

            for (TermBindingItem item : set.getItems())
            {
                if (!item.getCode().equals(tCode))
                    continue;

                item.setValue(tBinding);
                added = true;
            }

            if (!added)
            {
                TermBindingItem newItem = am_.createTermBindingItem(tCode, tBinding);
                set.getItems().add(newItem);
                added = true;
            }
        }

        if (!added)
        {
            TermBindingSet tbs = am_.createTermBindingSet(setName);
            tbs.getItems().add(am_.createTermBindingItem(tCode, tBinding));
            archetypeTerminology.getTermBindings().add(tbs);
        }
    }
}
