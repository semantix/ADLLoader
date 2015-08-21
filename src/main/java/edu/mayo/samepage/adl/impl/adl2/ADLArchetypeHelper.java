package edu.mayo.samepage.adl.impl.adl2;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.*;
import org.openehr.jaxb.rm.ObjectFactory;

/**
 * Created by dks02 on 8/21/15.
 */
public class ADLArchetypeHelper
{
    private static org.openehr.jaxb.am.ObjectFactory of_ = new org.openehr.jaxb.am.ObjectFactory();
    private static ObjectFactory ofrm_ = new ObjectFactory();

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
                                                         AuthoredResource parent)
    {
        ResourceDescription rd = ofrm_.createResourceDescription();
        rd.setCopyright(copyright);
        rd.setLifecycleState(lifeCycleState);

        if (parent != null)
            rd.setParentResource(parent);

        if (!StringUtils.isEmpty(packageUri))
            rd.setResourcePackageUri(packageUri);

        return rd;
    }

    public CComplexObject createComplexObjectConstraint(String rmType,
                                                        String id)
    {
        if (StringUtils.isEmpty(rmType))
            return null;

        if (StringUtils.isEmpty(id))
            return null;

        CComplexObject cComplexObject = of_.createCComplexObject();
        cComplexObject.setRmTypeName(rmType);
        cComplexObject.setNodeId(id);

        return cComplexObject;
    }

    public CAttribute createAttributeConstraint(String rmAttributeName,
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
}
