package edu.mayo.samepage.adl.services;

import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.am.CTerminologyCode;
import org.openehr.jaxb.rm.ObjectFactory;

/**
 * Created by dks02 on 8/25/15.
 */
public class CIMITypes
{
    private static org.openehr.jaxb.am.ObjectFactory of_ = new org.openehr.jaxb.am.ObjectFactory();
    private static ObjectFactory ofrm_ = new ObjectFactory();



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
