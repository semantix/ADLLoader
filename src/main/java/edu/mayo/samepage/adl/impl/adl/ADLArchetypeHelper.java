package edu.mayo.samepage.adl.impl.adl;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.*;
import org.openehr.jaxb.rm.ObjectFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dks02 on 8/21/15.
 */
public class ADLArchetypeHelper
{
    private org.openehr.jaxb.am.ObjectFactory of_ = new org.openehr.jaxb.am.ObjectFactory();
    private ObjectFactory ofrm_ = new ObjectFactory();

    public Archetype createArchetype()
    {
        return of_.createDifferentialArchetype();
    }

    public ArchetypeTerminology createArchetypeTerminology()
    {
        return of_.createArchetypeTerminology();
    }

    public CodePhrase createCodePhrase(String code, String value)
    {
        CodePhrase phrase = ofrm_.createCodePhrase();
        phrase.setCodeString(code);
        TerminologyId termId = ofrm_.createTerminologyId();
        termId.setValue(value);
        phrase.setTerminologyId(termId);

        return phrase;
    }

    public ResourceDescription createResourceDescription(String copyright,
                                                         String lifeCycleState,
                                                         String packageUri,
                                                         AuthoredResource parentResource)
    {
        ResourceDescription rd = ofrm_.createResourceDescription();
        rd.setCopyright(copyright);
        rd.setLifecycleState(lifeCycleState);

        if (parentResource != null)
            rd.setParentResource(parentResource);

        if (!StringUtils.isEmpty(packageUri))
            rd.setResourcePackageUri(packageUri);

        return rd;
    }

    private CAttribute createAttributeConstraint(String rmAttributeName,
                                                 MultiplicityInterval existence,
                                                 Cardinality cardinality)
    {
        CAttribute attriuteConstraint = of_.createCAttribute();
        attriuteConstraint.setRmAttributeName(rmAttributeName);

        if (existence != null)
            attriuteConstraint.setExistence(existence);

        if (cardinality != null)
            attriuteConstraint.setCardinality(cardinality);

        return attriuteConstraint;
    }

    public CAttribute createAttributeConstraints(String rmAttributeName,
                                                 MultiplicityInterval existence,
                                                 Cardinality cardinality,
                                                 List<CObject> match)
    {
        CAttribute attriuteConstraint = createAttributeConstraint(rmAttributeName, existence, cardinality);

        attriuteConstraint.getChildren().addAll(match);

        return attriuteConstraint;
    }

    public MultiplicityInterval createMultiplicityInterval(int lower, int upper)
    {
        MultiplicityInterval mult = ofrm_.createMultiplicityInterval();
        mult.setLower(lower);
        mult.setUpper(upper);
        return mult;
    }

    public Cardinality createCardinality(MultiplicityInterval interval,
                                         boolean isUnique,
                                         boolean isOrdered)
    {
        Cardinality card = of_.createCardinality();
        card.setIsUnique(isUnique);
        card.setIsOrdered(isOrdered);

        if (interval != null)
            card.setInterval(interval);

        return card;
    }

    public CodeDefinitionSet createCodeDefinitionSet(String language)
    {
        CodeDefinitionSet cds = of_.createCodeDefinitionSet();
        cds.setLanguage(language);
        return cds;
    }

    public StringDictionaryItem createStringDictionaryItem(String id, String value)
    {
        StringDictionaryItem sdi = new StringDictionaryItem();
        sdi.setId(id);
        sdi.setValue(value);
        return sdi;
    }

    public String getTermDefinitionTextProperty()
    {
        return ADLConstants.TERM_DEFINITION_TEXT_PROPERTY;
    }

    public String getTermDescriptionProperty()
    {
        return ADLConstants.TERM_DEFINITION_DESCRIPTION_PROPERTY;
    }

    public ArchetypeTerm createArcehtypeTerm(String code, String definition, String description)
    {
        ArchetypeTerm aT =  of_.createArchetypeTerm();
        aT.setCode(code);

        String def = definition;
        if (StringUtils.isEmpty(definition))
            def = "";

        aT.getItems().add(createStringDictionaryItem(getTermDefinitionTextProperty(), def));

        if (!StringUtils.isEmpty(description))
            aT.getItems().add(createStringDictionaryItem(getTermDescriptionProperty(), description));

        return aT;
    }

    public TermBindingItem createTermBindingItem(String code, String value)
    {
        TermBindingItem tbi = of_.createTermBindingItem();
        tbi.setCode(code);
        tbi.setValue(value);
        return tbi;
    }

    public TermBindingSet createTermBindingSet(String setName)
    {
        TermBindingSet tbs = of_.createTermBindingSet();
        tbs.setTerminology(setName);

        return tbs;
    }

    public CComplexObject createComplexObjectConstraint(String rmType,
                                                        String id,
                                                        MultiplicityInterval occurrence)
    {
        if (StringUtils.isEmpty(rmType))
            return null;

        if (StringUtils.isEmpty(id))
            return null;

        CComplexObject cComplexObject = of_.createCComplexObject();
        cComplexObject.setRmTypeName(rmType);
        cComplexObject.setNodeId(id);

        if (occurrence != null)
            cComplexObject.setOccurrences(occurrence);

        return cComplexObject;
    }

    public void addAttributeConstraints(CComplexObject container,
                                       String attribute,
                                       MultiplicityInterval occurrence,
                                       Cardinality cardinality,
                                       CObject... contained)
    {
        if (contained == null)
            return;

        List<CObject> matches = Arrays.asList(contained);
        addAttributeConstraints(container, attribute, occurrence, cardinality, matches);
    }

    public void addAttributeConstraints(CComplexObject container,
                                        String attribute,
                                        MultiplicityInterval occurrence,
                                        Cardinality cardinality,
                                        List<CObject> contained)
    {
        if (container == null)
            return;

        if (contained == null)
            return;

        if (StringUtils.isEmpty(attribute))
            return;

        CAttribute attConstraints = createAttributeConstraints(attribute, occurrence, cardinality, contained);

        if (attConstraints != null)
            container.getAttributes().add(attConstraints);
    }

    public ValueSetItem createValueSetItem(String id, String... members)
    {
        if (StringUtils.isEmpty(id))
            return null;

        ValueSetItem valueSetItem = of_.createValueSetItem();
        valueSetItem.setId(id);

        for(String member :members)
            valueSetItem.getMembers().add(member);

        return valueSetItem;
    }
}
