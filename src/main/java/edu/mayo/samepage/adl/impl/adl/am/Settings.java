package edu.mayo.samepage.adl.impl.adl.am;

import edu.mayo.samepage.adl.IF.ADLParam;

import java.util.Hashtable;

/**
 * Created by dks02 on 10/1/15.
 */
public class Settings
{
    protected String BLANK = "";

    private Hashtable<ADLParam, Object> settings_ = new Hashtable<ADLParam, Object>();

    public String getString(ADLParam param, String defaultValueIfNotFound)
    {
        Object value = getValue(param);

        if (value == null)
            return defaultValueIfNotFound;

        return value.toString();
    }

    public Object getValue(ADLParam param)
    {
        return settings_.get(param);
    }

    public void setValue(ADLParam param, Object value)
    {
        if (param == null)
            return;

        settings_.put(param, value);
    }
}
