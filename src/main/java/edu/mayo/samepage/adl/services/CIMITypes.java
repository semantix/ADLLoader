package edu.mayo.samepage.adl.services;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.*;
import org.opencimi.jaxb.rm.*;
import org.opencimi.jaxb.rm.ObjectFactory;

/**
 * Created by dks02 on 8/25/15.
 */
public class CIMITypes
{
    private static org.openehr.jaxb.am.ObjectFactory of_ = new org.openehr.jaxb.am.ObjectFactory();
    private static ObjectFactory ofrm_ = new ObjectFactory();

    public static CPrimitiveObject getPrimitiveType(CIMIPrimitiveTypes type)
    {
        if (type == null)
            return null;

        switch (type)
        {
            case INTEGER: return of_.createCInteger();
            case REAL: return of_.createCReal();
            case STRING: return of_.createCString();
            case BOOLEAN: return of_.createCBoolean();
            case DATE: return of_.createCDate();
            case TIME: return of_.createCTime();
            case DATETIME: return of_.createCDateTime();
            case DURATION: return of_.createCDuration();
            default: return of_.createCPrimitiveObject();
        }
    }

    public static CTerminologyCode getTerminologyConstraint(String id, String... members)
    {
        if (StringUtils.isEmpty(id))
            return null;

        CTerminologyCode terminologyCode = of_.createCTerminologyCode();
        terminologyCode.setTerminologyId(id);

        if (members != null)
            for (String member : members)
                terminologyCode.getCodeList().add(member);

        return terminologyCode;
    }

    public static Interval getIntervalFor(CIMIPrimitiveTypes type)
    {
        if (type == null)
            return null;

        switch (type) {
            case INTEGER:
                return ofrm_.createIntervalOfInteger();
            case REAL:
                return ofrm_.createIntervalOfReal();
            case DATE:
                return ofrm_.createIntervalOfDate();
            case TIME:
                return ofrm_.createIntervalOfTime();
            case DATETIME:
                return ofrm_.createIntervalOfDateTime();
            case DURATION:
                return ofrm_.createIntervalOfDuration();
            default:
                return null;
        }
    }

    public static CPrimitiveObject createPrimitiveTypeConstraints(CIMIPrimitiveTypes primitiveType,
                                                                  Interval interval,
                                                                  MultiplicityInterval occurrence)
    {
        if (primitiveType == null)
            return null;

        CPrimitiveObject primitiveObject = CIMITypes.getPrimitiveType(primitiveType);

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

        return primitiveObject;
    }

    public static CIMIPrimitiveTypes getCIMIPrimitiveType(String typeName)
    {
        if (StringUtils.isEmpty(typeName))
            return CIMIPrimitiveTypes.STRING;

        if (typeName.toLowerCase().indexOf("integer") != -1)
            return CIMIPrimitiveTypes.INTEGER;
        if (typeName.toLowerCase().indexOf("real") != -1)
            return CIMIPrimitiveTypes.REAL;
        if (typeName.toLowerCase().indexOf("datetime") != -1)
            return CIMIPrimitiveTypes.DATETIME;
        if (typeName.toLowerCase().indexOf("time") != -1)
            return CIMIPrimitiveTypes.TIME;
        if (typeName.toLowerCase().indexOf("date") != -1)
            return CIMIPrimitiveTypes.DATE;
        if (typeName.toLowerCase().indexOf("duration") != -1)
            return CIMIPrimitiveTypes.DURATION;
        if (typeName.toLowerCase().indexOf("boolean") != -1)
            return CIMIPrimitiveTypes.BOOLEAN;

        return CIMIPrimitiveTypes.STRING;
    }
}
