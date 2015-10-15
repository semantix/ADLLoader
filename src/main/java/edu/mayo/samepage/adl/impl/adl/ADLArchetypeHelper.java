package edu.mayo.samepage.adl.impl.adl;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.*;

import java.util.List;

import static org.openehr.adl.am.AmObjectFactory.*;
import static org.openehr.adl.rm.RmObjectFactory.newMultiplicityInterval;

//import static org.openehr.adl.am.AmObjectFactory.*;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLArchetypeHelper
{
    protected org.openehr.jaxb.am.ObjectFactory am_ = new org.openehr.jaxb.am.ObjectFactory();

    public Cardinality createCardinality(MultiplicityInterval interval,
                                         boolean isOrdered,
                                         boolean isUnique)
    {
        return newCardinality(isOrdered, isUnique, interval);
    }

    public MultiplicityInterval createMultiplicity(Integer low,
                                                  Integer high,
                                                  boolean lowerIncluded,
                                                  boolean highIncluded)
    {
        return newMultiplicityInterval(low, high, lowerIncluded, highIncluded);
    }

    public MultiplicityInterval createMultiplicity(Integer low,
                                                   Integer high)
    {
        return newMultiplicityInterval(low, high, true, true);
    }

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

    public CComplexObject createComplexObjectConstraint(RmType rmType,
                                                        RmTypeAttribute rmTypeAttribute,
                                                        String id,
                                                        MultiplicityInterval occurrence,
                                                        List<CObject> constraintsOnAttribute)
    {
        if (rmType == null)
            return null;

        if (rmTypeAttribute == null)
            return newCComplexObject(rmType.getRmType(), occurrence, id, null);

        return newCComplexObject(rmType.getRmType(), occurrence, id, ImmutableList.of(
                newCAttribute(rmTypeAttribute.getAttributeName(), null, null, constraintsOnAttribute
                )));
    }

    public CComplexObject createComplexObjectConstraint(RmType rmType,
                                                        String id,
                                                        MultiplicityInterval occurrence,
                                                        List<CAttribute> attributeConstraints)
    {
        if (rmType == null)
            return null;

        return newCComplexObject(rmType.getRmType(), occurrence, id, attributeConstraints);
    }

    public CAttribute createAttributeConstraint(RmTypeAttribute attribute,
                                                MultiplicityInterval occurrence,
                                                Cardinality cardinality,
                                                List<CObject> constraintsOnAttribute)
    {
        if (attribute == null)
            return null;

        return newCAttribute(attribute.getAttributeName(), occurrence, cardinality, constraintsOnAttribute);
    }

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
