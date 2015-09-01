package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLParam;
import org.openehr.adl.rm.RmModel;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dks02 on 8/20/15.
 */
public class ADLSettings
{
    protected String nsDelimiter_ = "::";
    protected String rmDelimiter_ = "-";
    protected String delimiter_ = ".";

    protected String idPrefix_ = "id";
    protected String vsPrefix_ = "ac";
    protected String pvPrefix_ = "at";

    protected AtomicInteger idCounter = new AtomicInteger(0);
    protected AtomicInteger vsCounter = new AtomicInteger(0);
    protected AtomicInteger pvCounter = new AtomicInteger(0);

    private String BLANK = "";

    private Hashtable<ADLParam, Object> settings_ = new Hashtable<ADLParam, Object>();

    protected RmModel rmModel_ = null;

    public ADLSettings()
    {
        settings_.put(ADLParam.RM_RELEASE, ADLConstants.RM_RELEASE_VERSION);
        settings_.put(ADLParam.ADL_VERSION, ADLConstants.ADL_DEFAULT_VERSION);
        settings_.put(ADLParam.ARCHETYPE_NAMESPACE, ADLConstants.ADL_DEFAULT_NS);
        settings_.put(ADLParam.RM_PUBLISHER, ADLConstants.RM_DEFAULT_PUBLISHER);
        settings_.put(ADLParam.RM_PACKAGE, ADLConstants.RM_DEFAULT_PACKAGE);
        settings_.put(ADLParam.RM_CLASS, ADLConstants.RM_DEFAULT_CLASS);
        settings_.put(ADLParam.RESOURCE_PACKAGE_URI, BLANK);
        settings_.put(ADLParam.MAJOR_VERSION, ADLConstants.ARCH_DEFAULT_MAJOR_VERSION);
        settings_.put(ADLParam.MINOR_VERSION, ADLConstants.ARCH_DEFAULT_MINOR_VERSION);
        settings_.put(ADLParam.PATCH_VERSION, ADLConstants.ARCH_DEFAULT_PATCH_VERSION);
        settings_.put(ADLParam.VERSION_STATUS, ADLConstants.ARCH_DEFAULT_VERSION_STATUS);
        settings_.put(ADLParam.BUILD_COUNT, ADLConstants.ARCH_DEFAULT_BUILD_COUNT);
        settings_.put(ADLParam.ORIGINAL_LANGUAGE, BLANK);
        settings_.put(ADLParam.COPYRIGHT, ADLConstants.ARCH_DEFAULT_COPYRIGHT);
        settings_.put(ADLParam.DETAILS, BLANK);
        settings_.put(ADLParam.LIFECYCLE_STATE, ADLConstants.ARCH_DEFAULT_LIFECYCLE_STATE);
        settings_.put(ADLParam.OTHER_CONTRIBUTORS, BLANK);
        settings_.put(ADLParam.PARENT_ARCHETYPE_ID, BLANK);
    }

    public boolean getIsGenerated()
    {
        return true;
    }

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

    public String createNewId()
    {
        return idPrefix_ + idCounter.incrementAndGet();
    }
    public String createNewValueSetId()
    {
        return vsPrefix_ + vsCounter.incrementAndGet();
    }
    public String createNewPermissibleValueId()
    {
        return pvPrefix_ + pvCounter.incrementAndGet();
    }

}
