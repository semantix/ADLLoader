package edu.mayo.samepage.adl.impl.adl;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.CAttribute;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.CObject;
import org.openehr.jaxb.am.Cardinality;
import org.openehr.jaxb.rm.MultiplicityInterval;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLAMConstraintFactory extends ADLAMFactory
{
    // Attribute Constraints
    private CAttribute createAttributeConstraint(String rmAttributeName,
                                                 MultiplicityInterval existence,
                                                 Cardinality cardinality)
    {
        CAttribute attriuteConstraint = amFactory_.createCAttribute();
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
        CAttribute attriuteConstraint = createAttributeConstraint(rmAttributeName,
                existence,
                cardinality);

        attriuteConstraint.getChildren().addAll(match);

        return attriuteConstraint;
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

    public CComplexObject createComplexObjectConstraint(String rmType,
                                                        String id,
                                                        MultiplicityInterval occurrence)
    {
        if (StringUtils.isEmpty(rmType))
            return null;

        if (StringUtils.isEmpty(id))
            return null;

        CComplexObject cComplexObject = amFactory_.createCComplexObject();
        cComplexObject.setRmTypeName(rmType);
        cComplexObject.setNodeId(id);

        if (occurrence != null)
            cComplexObject.setOccurrences(occurrence);

        return cComplexObject;
    }
}
