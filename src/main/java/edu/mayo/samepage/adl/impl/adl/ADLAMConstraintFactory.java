package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.services.CIMIPrimitiveTypes;
import org.apache.commons.lang.StringUtils;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;
import org.openehr.jaxb.am.*;
import org.openehr.jaxb.rm.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLAMConstraintFactory extends ADLAMFactory
{
    public CPrimitiveObject createIntervalConstraint(CIMIPrimitiveTypes primitiveType,
                                                      org.openehr.jaxb.rm.Interval interval,
                                                      MultiplicityInterval occurrence)
    {
        if (primitiveType == null)
            return null;

        CPrimitiveObject primitiveObject = getPrimitiveType(CIMIPrimitiveTypes.INTEGER);

        switch (primitiveType)
        {
            case INTEGER:
                CInteger cInteger = (CInteger) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfInteger))
                    cInteger.setRange((IntervalOfInteger) interval);
                return cInteger;
            case REAL:
                CReal cReal = (CReal) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfReal))
                    cReal.setRange((IntervalOfReal) interval);
                return cReal;
            case DATE:
                CDate cDate = (CDate) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfDate))
                    cDate.setRange((IntervalOfDate) interval);
                return cDate;
            case TIME:
                CTime cTime = (CTime) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfTime))
                    cTime.setRange((IntervalOfTime) interval);
                return cTime;
            case DATETIME:
                CDateTime cDateTime = (CDateTime) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfTime))
                    cDateTime.setRange((IntervalOfDateTime) interval);
                return cDateTime;
            case DURATION:
                CDuration cDuration = (CDuration) primitiveObject;
                if ((interval != null) && (interval instanceof IntervalOfDuration))
                    cDuration.setRange((IntervalOfDuration) interval);
                return cDuration;
        }

        if (occurrence != null)
            primitiveObject.setOccurrences(occurrence);

        return primitiveObject;
    }

    // Attribute Constraints
    private CAttribute createAttributeConstraint(RmTypeAttribute rmAttribute,
                                                 MultiplicityInterval existence,
                                                 Cardinality cardinality)
    {
        CAttribute attriuteConstraint = amFactory_.createCAttribute();
        attriuteConstraint.setRmAttributeName(rmAttribute.getAttributeName());

        if (existence != null)
            attriuteConstraint.setExistence(existence);

        if (cardinality != null)
            attriuteConstraint.setCardinality(cardinality);

        return attriuteConstraint;
    }

    public CAttribute createAttributeConstraints(RmTypeAttribute rmAttribute,
                                                 MultiplicityInterval existence,
                                                 Cardinality cardinality,
                                                 List<CObject> match)
    {
        CAttribute attriuteConstraint = createAttributeConstraint(rmAttribute,
                existence,
                cardinality);

        attriuteConstraint.getChildren().addAll(match);

        return attriuteConstraint;
    }

    public void addAttributeConstraints(CComplexObject container,
                                        RmTypeAttribute attribute,
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
                                        RmTypeAttribute attribute,
                                        MultiplicityInterval occurrence,
                                        Cardinality cardinality,
                                        List<CObject> contained)
    {
        if (container == null)
            return;

        if (contained == null)
            return;

        if (attribute == null)
            return;

        CAttribute attConstraints = createAttributeConstraints(attribute, occurrence, cardinality, contained);

        if (attConstraints != null)
            container.getAttributes().add(attConstraints);
    }

    public CTerminologyCode getTerminologyConstraint(String id, String... members)
    {
        if (StringUtils.isEmpty(id))
            return null;

        CTerminologyCode terminologyCode = amFactory_.createCTerminologyCode();
        terminologyCode.setTerminologyId(id);

        if (members != null)
            for (String member : members)
                terminologyCode.getCodeList().add(member);

        return terminologyCode;
    }

    public CComplexObject createComplexObjectConstraint(RmType rmType,
                                                        String id,
                                                        MultiplicityInterval occurrence)
    {
        if (rmType == null)
            return null;

        if (StringUtils.isEmpty(id))
            return null;

        CComplexObject cComplexObject = amFactory_.createCComplexObject();
        cComplexObject.setRmTypeName(rmType.getRmType());
        cComplexObject.setNodeId(id);

        if (occurrence != null)
            cComplexObject.setOccurrences(occurrence);

        return cComplexObject;
    }
}
