package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.impl.adl.am.ADLConstants;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMFactory;
import edu.mayo.samepage.adl.services.CIMIPrimitiveTypes;
import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.MultiplicityInterval;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLAMFactory
{
    public ObjectFactory amFactory_ = new ObjectFactory();
    public ADLRMFactory rmFactory_ = new ADLRMFactory();

    public Archetype createArchetype()
    {
        return amFactory_.createArchetype();
    }

    public ArchetypeTerminology createArchetypeTerminology()
    {
        return amFactory_.createArchetypeTerminology();
    }

    public CodeDefinitionSet createCodeDefinitionSet(String language)
    {
        CodeDefinitionSet cds = amFactory_.createCodeDefinitionSet();
        cds.setLanguage(language);
        return cds;
    }

    public Cardinality createCardinality(MultiplicityInterval interval,
                                         boolean isUnique,
                                         boolean isOrdered)
    {
        Cardinality card = amFactory_.createCardinality();
        card.setIsUnique(isUnique);
        card.setIsOrdered(isOrdered);

        if (interval != null)
            card.setInterval(interval);

        return card;
    }

    // Term Related
    public TermBindingItem createTermBindingItem(String code, String value)
    {
        TermBindingItem tbi = amFactory_.createTermBindingItem();
        tbi.setCode(code);
        tbi.setValue(value);
        return tbi;
    }

    public TermBindingSet createTermBindingSet(String setName)
    {
        TermBindingSet tbs = amFactory_.createTermBindingSet();
        tbs.setTerminology(setName);

        return tbs;
    }

    public ArchetypeTerm createArcehtypeTerm(String code, String definition, String description)
    {
        ArchetypeTerm aT =  amFactory_.createArchetypeTerm();
        aT.setCode(code);

        String def = definition;
        if (StringUtils.isEmpty(definition))
            def = "";

        aT.getItems().add(rmFactory_.createStringDictionaryItem(
                getTermDefinitionTextProperty(), def));

        if (!StringUtils.isEmpty(description))
            aT.getItems().add(rmFactory_.createStringDictionaryItem(
                    getTermDescriptionProperty(), description));

        return aT;
    }


    public String getTermDefinitionTextProperty()
    {
        return ADLConstants.TERM_DEFINITION_TEXT_PROPERTY;
    }

    public String getTermDescriptionProperty()
    {
        return ADLConstants.TERM_DEFINITION_DESCRIPTION_PROPERTY;
    }

    public ValueSetItem createValueSetItem(String id, String... members)
    {
        if (StringUtils.isEmpty(id))
            return null;

        ValueSetItem valueSetItem = amFactory_.createValueSetItem();
        valueSetItem.setId(id);

        for(String member :members)
            valueSetItem.getMembers().add(member);

        return valueSetItem;
    }

    public CPrimitiveObject getPrimitiveType(CIMIPrimitiveTypes type)
    {
        if (type == null)
            return null;

        switch (type)
        {
            case INTEGER: return amFactory_.createCInteger();
            case REAL: return amFactory_.createCReal();
            case STRING: return amFactory_.createCString();
            case BOOLEAN: return amFactory_.createCBoolean();
            case DATE: return amFactory_.createCDate();
            case TIME: return amFactory_.createCTime();
            case DATETIME: return amFactory_.createCDateTime();
            case DURATION: return amFactory_.createCDuration();
            default: return amFactory_.createCPrimitiveObject();
        }
    }
}
