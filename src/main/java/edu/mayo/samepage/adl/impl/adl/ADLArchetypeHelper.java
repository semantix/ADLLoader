package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.impl.adl.env.ADLConstants;
import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.ValueSetItem;
import org.openehr.jaxb.rm.AuthoredResource;
import org.openehr.jaxb.rm.ResourceDescription;
import org.openehr.jaxb.rm.ResourceDescriptionItem;
import org.openehr.jaxb.rm.StringDictionaryItem;

import java.util.List;

//import static org.openehr.adl.am.AmObjectFactory.*;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLArchetypeHelper
{
    protected org.openehr.jaxb.am.ObjectFactory am_ = new org.openehr.jaxb.am.ObjectFactory();

//    public Cardinality createCardinality(MultiplicityInterval interval,
//                                         boolean isUnique,
//                                         boolean isOrdered)
//    {
//        Cardinality card = newCardinality(isOrdered, isUnique, interval);
//        card.setIsUnique(isUnique);
//        card.setIsOrdered(isOrdered);
//
//        if (interval != null)
//            card.setInterval(interval);
//
//        return card;
//    }
//
//    // Term Related
//    public TermBindingItem createTermBindingItem(String code, String value)
//    {
//        TermBindingItem tbi = newTermBindingItem(code, value);
//        tbi.setCode(code);
//        tbi.setValue(value);
//        return tbi;
//    }
//
//    public TermBindingSet createTermBindingSet(String setName)
//    {
//        TermBindingSet tbs = newTermBindingSet(setName, null);
//        tbs.setTerminology(setName);
//
//        return tbs;
//    }






    public ValueSetItem createValueSetItem(String id, String... members)
    {
        if (StringUtils.isEmpty(id))
            return null;

        ValueSetItem valueSetItem = new ValueSetItem();
        valueSetItem.setId(id);

        for(String member :members)
            valueSetItem.getMembers().add(member);

        return valueSetItem;
    }

//    public CComplexObject createComplexObjectConstraint(RmType rmType,
//                                                        String id,
//                                                        MultiplicityInterval occurrence,
//                                                        List<CAttribute> attributes)
//    {
//        if (rmType == null)
//            return null;
//
//        if (StringUtils.isEmpty(id))
//            return null;
//
//        return newCComplexObject(rmType.getRmType(), occurrence, id, attributes);
//    }
//
//    // Attribute Constraints
//    public CAttribute createAttributeConstraint(RmTypeAttribute rmAttribute,
//                                                MultiplicityInterval existence,
//                                                Cardinality cardinality,
//                                                List<CObject> children)
//    {
//        if (rmAttribute == null)
//            return null;
//
//        return newCAttribute(rmAttribute.getAttributeName(),existence, cardinality, children);
//    }
//
//    public CAttribute createAttributeConstraints(RmTypeAttribute attribute,
//                                              MultiplicityInterval occurrence,
//                                              Cardinality cardinality,
//                                              CObject... contained)
//    {
//        List<CObject> matches = Arrays.asList(contained);
//        return createAttributeConstraint(attribute, occurrence, cardinality, matches);
//    }

    public ResourceDescription createResourceDescription(String copyright,
                                                         String lifeCycleState,
                                                         String packageUri,
                                                         AuthoredResource parentResource,
                                                         List<StringDictionaryItem> authors,
                                                         List<String> otherContributors,
                                                         List<ResourceDescriptionItem> details,
                                                         List<StringDictionaryItem> otherDetails)
    {
        ResourceDescription rd = new ResourceDescription();
        rd.setCopyright(copyright);
        rd.setLifecycleState(lifeCycleState);

        if (parentResource != null)
            rd.setParentResource(parentResource);

        if (!StringUtils.isEmpty(packageUri))
            rd.setResourcePackageUri(packageUri);

        if ((authors != null) && (!authors.isEmpty()))
            rd.getOriginalAuthor().addAll(authors);

        if ((details != null) && (!details.isEmpty()))
            rd.getDetails().addAll(details);

        if ((otherDetails != null) && (!otherDetails.isEmpty()))
            rd.getOtherDetails().addAll(otherDetails);

        if ((otherContributors != null) && (!otherContributors.isEmpty()))
            rd.getOtherContributors().addAll(otherContributors);

        return rd;
    }
}
