package edu.mayo.samepage.adl.impl.adl2;

import edu.mayo.samepage.adl.IF.ADLParam;
import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.rm.ArchetypeId;

/**
 * Created by dks02 on 8/20/15.
 */
public class ADLMetaData extends ADLMetaDataSettings
{
    public ArchetypeId createArchetypeId(String archetypeName)
    {
        if (StringUtils.isEmpty(archetypeName))
            return null;

        String nsPrefix = getString(ADLParam.ARCHETYPE_NAMESPACE, ADLConstants.ADL_DEFAULT_NS);

        if (!StringUtils.isEmpty(nsPrefix))
            nsPrefix += nsDelimiter_;

        String archIdPrefix =
                nsPrefix +
                        getString(ADLParam.RM_PUBLISHER, ADLConstants.RM_DEFAULT_PUBLISHER) +
                        rmDelimiter_ +
                        getString(ADLParam.RM_PACKAGE, ADLConstants.RM_DEFAULT_PACKAGE) +
                        rmDelimiter_ +
                        getString(ADLParam.RM_CLASS, ADLConstants.RM_DEFAULT_CLASS);

        String archVersion = "v" +
                getString(ADLParam.MAJOR_VERSION, ADLConstants.ARCH_DEFAULT_MAJOR_VERSION) +
                delimiter_ +
                getString(ADLParam.MINOR_VERSION, ADLConstants.ARCH_DEFAULT_MINOR_VERSION) +
                delimiter_ +
                getString(ADLParam.PATCH_VERSION, ADLConstants.ARCH_DEFAULT_PATCH_VERSION);

        String status =  getString(ADLParam.VERSION_STATUS, ADLConstants.ARCH_DEFAULT_VERSION_STATUS);
        if (!StringUtils.isEmpty(status))
            status = rmDelimiter_ + status;

        String buildCount =  getString(ADLParam.BUILD_COUNT, ADLConstants.ARCH_DEFAULT_BUILD_COUNT);
        if (!StringUtils.isEmpty(buildCount))
            buildCount = delimiter_ + buildCount;


        ArchetypeId aid = new ArchetypeId();
        aid.setValue(archIdPrefix + delimiter_ + archetypeName + delimiter_
                            + archVersion + status + buildCount);

        return aid;
    }

    public String getDefaultTerminologySetName()
    {
        return ADLConstants.ARCH_DEFAULT_TERMONOLOGY_SET;
    }
}
