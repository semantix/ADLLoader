package edu.mayo.samepage.adl.impl.adl;

import ch.qos.logback.classic.Logger;
import edu.mayo.samepage.adl.impl.adl.env.ADLConstants;
import edu.mayo.samepage.adl.impl.adl.env.IDType;
import edu.mayo.samepage.adl.services.ADLTerminologyServices;
import org.apache.commons.lang.StringUtils;
import org.openehr.adl.serializer.ArchetypeSerializer;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.CodePhrase;
import org.openehr.jaxb.rm.ResourceDescription;
import org.openehr.jaxb.rm.StringDictionaryItem;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.openehr.adl.rm.RmObjectFactory.*;
import static org.openehr.adl.am.AmObjectFactory.*;

/**
 * Created by dks02 on 8/19/15.
 */
public class ADLArchetype
{
    final static Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ADLArchetype.class);

    private ADLArchetypeHelper helper_ = new ADLArchetypeHelper();

    private Archetype archetype_ = null;
    private ADLMetaData meta_ = null;

    public ADLArchetype(String name,
                        ADLMetaData meta)
    {
        if (meta != null)
            meta_ = meta;

        archetype_ = new Archetype();
        archetype_.setArchetypeId(newArchetypeId(meta_.createArchetypeId(name)));
        //archetype_.setIsDifferential(true);

        if (meta_.getADLSettings().getParentID() != null)
            archetype_.setParentArchetypeId(meta_.getADLSettings().getParentID());

        archetype_.setAdlVersion(meta_.getADLSettings().getADLVersion());
        archetype_.setRmRelease(meta_.getADLRMSettings().getRMRelaseVersion());
        archetype_.setIsGenerated(meta_.getADLSettings().getIsGenerated());

        CodePhrase language = meta_.getADLSettings().getOriginalLanguage();

        if (language == null)
            language = newCodePhrase(newTerminologyId(ADLConstants.ARCH_DEFAULT_LANGUAGE_VALUE),
                    ADLConstants.ARCH_DEFAULT_LANGUAGE_CODE);

        archetype_.setOriginalLanguage(language);

        ResourceDescription desc = helper_.createResourceDescription(
                meta_.getADLSettings().getCopyright(),
                meta_.getADLSettings().getLifeCycleState(),
                meta_.getADLSettings().getResourcePackageURI(),
                null, null, null, null, null);

        archetype_.setDescription(desc);
    }

    public Archetype getArchetype()
    {
        return this.archetype_;
    }

    public String addNewId(IDType type, String name, String desc)
    {
        String id = meta_.createNewId(type);
        String conceptReference = ADLTerminologyServices.getConceptReference(id);
        addArchetypeTerm(id, null, name, desc, null, conceptReference);
        return id;
    }

    public String serialize()
    {
        return ArchetypeSerializer.serialize(this.getArchetype());
    }

    public ArchetypeTerminology getTerminology()
    {
        ArchetypeTerminology archetypeTerminology = archetype_.getTerminology();
        if (archetypeTerminology == null)
            archetypeTerminology = new ArchetypeTerminology();

        return archetypeTerminology;
    }

    public CComplexObject getDefinition()
    {
        if (archetype_ == null)
            return null;

        return archetype_.getDefinition();
    }

    public void setDefinition(CComplexObject definition)
    {
        if (archetype_ == null)
            return;

        archetype_.setDefinition(definition);
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
                    if (getTermDefinitionTextProperty().equals(dictionaryItem.getId()))
                    {
                        dictionaryItem.setValue(definition);
                        added = true;
                    }

                    if (getTermDescriptionProperty().equals(dictionaryItem.getId()))
                    {
                        dictionaryItem.setValue(description);
                        added = true;
                    }
                }
            }

            if (!added)
            {
                set.getItems().add(createArcehtypeTerm(tCode, definition, description));
                added = true;
            }

        }

        if (!added)
        {
            List<ArchetypeTerm> terms1 = new ArrayList<ArchetypeTerm>();
            terms1.add(createArcehtypeTerm(tCode, definition, description));

            CodeDefinitionSet cds = newCodeDefinitionSet(languageSet, terms1);

            archetypeTerminology.getTermDefinitions().add(cds);
        }
    }

    public ArchetypeTerm createArcehtypeTerm(String code, String definition, String description)
    {
        ArchetypeTerm aT =  new ArchetypeTerm();
        aT.setCode(code);

        String def = definition;
        if (StringUtils.isEmpty(definition))
            def = "";

        aT.getItems().add(newStringDictionaryItem(
                getTermDefinitionTextProperty(), def));

        if (!StringUtils.isEmpty(description))
            aT.getItems().add(newStringDictionaryItem(
                    getTermDescriptionProperty(), description));

        return aT;
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
            ValueSetItem valueSetItem = helper_.createValueSetItem(valueSetId, valueSetMembers);
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
                TermBindingItem newItem = newTermBindingItem(tCode, tBinding);
                set.getItems().add(newItem);
                added = true;
            }
        }

        if (!added)
        {
            List<TermBindingItem> tbi = new ArrayList<TermBindingItem>();
            tbi.add(newTermBindingItem(tCode, tBinding));
            TermBindingSet tbs = newTermBindingSet(setName, tbi);
            archetypeTerminology.getTermBindings().add(tbs);
        }
    }

    public String getTermDefinitionTextProperty()
    {
        return ADLConstants.TERM_DEFINITION_TEXT_PROPERTY;
    }

    public String getTermDescriptionProperty()
    {
        return ADLConstants.TERM_DEFINITION_DESCRIPTION_PROPERTY;
    }
}
