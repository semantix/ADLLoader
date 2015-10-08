package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.impl.adl.env.ADLConstants;
import edu.mayo.samepage.adl.impl.adl.env.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.env.IDType;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import org.apache.commons.lang.StringUtils;

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

    private ADLSettings adlSettings_ = new ADLSettings();
    private ADLRMSettings rmSettings_ = new ADLRMSettings(ADLRM.OPENEHR);

    public ADLMetaData(ADLSettings adlConfiguration,
                       ADLRMSettings rmConfiguration,
                       String RMClassName)
    {
        if(adlConfiguration != null)
            this.adlSettings_ = adlConfiguration;

        if (rmConfiguration != null)
            this.rmSettings_ = rmConfiguration;

        rmSettings_.setTopRMClassName(RMClassName);
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
