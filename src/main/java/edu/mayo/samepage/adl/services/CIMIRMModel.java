package edu.mayo.samepage.adl.services;

import org.openehr.adl.rm.OpenEhrRmModel;
import org.openehr.adl.rm.RmModel;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;

import java.util.List;

/**
 * Created by dks02 on 8/31/15.
 */
public class CIMIRMModel implements RmModel
{
    private OpenEhrRmModel rm_ = OpenEhrRmModel.getInstance();

    public Class<?> getRmClass(String s)
    {
        return rm_.getRmClass(s);
    }

    public RmType getRmType(String s)
    {
        return rm_.getRmType(s);
    }

    public String getRmTypeName(Class<?> aClass)
    {
        return getRmTypeName(aClass);
    }

    public boolean rmTypeExists(String s)
    {
        return rm_.rmTypeExists(s);
    }

    public RmTypeAttribute getRmAttribute(String s, String s1)
    {
        return rm_.getRmAttribute(s, s1);
    }

    public List<RmType> getAllTypes()
    {
        return rm_.getAllTypes();
    }
}
