package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.impl.adl.env.ADLConstants;
import edu.mayo.samepage.adl.impl.adl.env.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.env.IDType;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import org.apache.commons.lang.StringUtils;
import org.opencimi.adl.rm.OpenCimiRmModel;
import org.openehr.adl.rm.OpenEhrRmModel;
import org.openehr.adl.rm.RmModel;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dks02 on 8/20/15.
 */
public class ADLMetaData
{
    protected String idPrefix_ = "id";
    protected String vsPrefix_ = "ac";
    protected String pvPrefix_ = "at";

    protected AtomicInteger idCounter = new AtomicInteger(0);
    protected AtomicInteger vsCounter = new AtomicInteger(0);
    protected AtomicInteger pvCounter = new AtomicInteger(0);

    private ADLSettings adlSettings_ = null;
    private ADLRMSettings rmSettings_ = null;
    private RmModel rm_ = OpenCimiRmModel.getInstance();

    public ADLMetaData(ADLRM rm)
    {
        adlSettings_ = new ADLSettings();

        if (rm == ADLRM.OPENCIMI)
        {
            rm_ = OpenCimiRmModel.getInstance();
            rmSettings_ = new ADLRMSettings(ADLRM.OPENCIMI);
        }
        else
        {
            rmSettings_ = new ADLRMSettings(ADLRM.OPENEHR);
            rm_ = OpenEhrRmModel.getInstance();
        }
    }

    public void setRMPackageName(String packageName)
    {
        this.rmSettings_.setRMPackage(packageName);
    }

    public RmType getRmType(String name)
    {
        return rm_.getRmType(name);
    }

    public RmTypeAttribute getRmAttribute(String className, String attribute)
    {
        return rm_.getRmAttribute(className, attribute);
    }

    public List<RmType> getAllTypes()
    {
        return rm_.getAllTypes();
    }

    public ADLSettings getADLSettings()
    {
        return this.adlSettings_;
    }

    public ADLRMSettings getADLRMSettings()
    {
        return this.rmSettings_;
    }

    public String createNewId(IDType type)
    {
        switch(type)
        {
            case VALUESET: return vsPrefix_ + vsCounter.incrementAndGet();
            case VALUESETMEMBER: return pvPrefix_ + pvCounter.incrementAndGet();
            default: return idPrefix_ + idCounter.incrementAndGet();
        }
    }

    public String createArchetypeId(String archetypeName)
    {
        if (StringUtils.isEmpty(archetypeName))
            return null;

        String nsPrefix = adlSettings_.getNameSpace();

        if (!StringUtils.isEmpty(nsPrefix))
            nsPrefix += ADLConstants.nsDelimiter_;

        String archIdPrefix =
                nsPrefix +
                        rmSettings_.getRMPublisher() +
                        ADLConstants.rmDelimiter_ +
                        rmSettings_.getRMPackage() +
                        ADLConstants.rmDelimiter_ +
                        rmSettings_.getTopRMClassName();

        String archVersion = "v" +
                adlSettings_.getMajorVersion() +
                ADLConstants.delimiter_ +
                adlSettings_.getMinorVersion() +
                ADLConstants.delimiter_ +
                adlSettings_.getPatchVersion();

        String status =  adlSettings_.getVersionStatus();
        if (!StringUtils.isEmpty(status))
            status = ADLConstants.rmDelimiter_ + status;

        String buildCount =  adlSettings_.getBuildCount();
        if (!StringUtils.isEmpty(buildCount))
            buildCount = ADLConstants.delimiter_ + buildCount;


        return archIdPrefix + ADLConstants.delimiter_ + archetypeName + ADLConstants.delimiter_
                            + archVersion + status + buildCount;
    }

    public String getDefaultTerminologySetName()
    {
        return ADLConstants.ARCH_DEFAULT_TERMONOLOGY_SET;
    }

    public void setDefaultTerminologySetName(String defaultTerminologySetName)
    {
        if (StringUtils.isEmpty(defaultTerminologySetName))
            return;

        ADLConstants.ARCH_DEFAULT_TERMONOLOGY_SET = defaultTerminologySetName;
    }
}
