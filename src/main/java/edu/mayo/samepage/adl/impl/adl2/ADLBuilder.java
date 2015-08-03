package edu.mayo.samepage.adl.impl.adl2;

import edu.mayo.samepage.adl.IF.ADLParam;
import edu.mayo.samepage.adl.IF.ADLSetting;
import org.apache.commons.lang.StringUtils;
import org.openehr.jaxb.rm.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dks02 on 8/3/15.
 */
public class ADLBuilder
{
    private String nsDelimiter = "::";
    private String rmDelimiter = "-";
    private String delimiter = ".";

    private ObjectFactory ofrm = new ObjectFactory();
    private List<ADLSetting> settings_ = new ArrayList<ADLSetting>();

    public ADLBuilder(List<ADLSetting> settings)
    {
        if (settings != null)
            this.settings_ = settings;
    }

    public String getADLVersion()
    {
        return getString(ADLParam.ADL_VERSION, ADLConstants.ADL_DEFAULT_VERSION);
    }

    public String getRMRelaseVersion()
    {
        return getString(ADLParam.RM_RELEASE, ADLConstants.RM_RELEASE_VERSION);
    }

    public CodePhrase getOriginalLanguage()
    {
        Object orgLang = getValue(ADLParam.ORIGINAL_LANGUAGE);

        if ((orgLang != null)&&(orgLang instanceof CodePhrase))
            return (CodePhrase) orgLang;


        CodePhrase lang = ofrm.createCodePhrase();
        lang.setCodeString(ADLConstants.ARCH_DEFAULT_LANGUAGE_CODE);
        TerminologyId langTermID = ofrm.createTerminologyId();
        langTermID.setValue(ADLConstants.ARCH_DEFAULT_LANGUAGE_VALUE);
        lang.setTerminologyId(langTermID);

        return lang;
    }

    public ArchetypeId createArchetypeId(String name)
    {
        if (StringUtils.isEmpty(name))
            return null;

        String nsPrefix = getString(ADLParam.ARCHETYPE_NAMESPACE, ADLConstants.ADL_DEFAULT_NS);

        if (!StringUtils.isEmpty(nsPrefix))
            nsPrefix += nsDelimiter;

        String archIdPrefix =
                nsPrefix +
                getString(ADLParam.RM_PUBLISHER, ADLConstants.RM_DEFAULT_PUBLISHER) +
                rmDelimiter +
                getString(ADLParam.RM_PACKAGE, ADLConstants.RM_DEFAULT_PACKAGE) +
                rmDelimiter +
                getString(ADLParam.RM_CLASS, ADLConstants.RM_DEFAULT_CLASS);

        String archVersion = "v" +
                getString(ADLParam.MAJOR_VERSION, ADLConstants.ARCH_DEFAULT_MAJOR_VERSION) +
                delimiter +
                getString(ADLParam.MINOR_VERSION, ADLConstants.ARCH_DEFAULT_MINOR_VERSION) +
                delimiter +
                getString(ADLParam.PATCH_VERSION, ADLConstants.ARCH_DEFAULT_PATCH_VERSION);

        String status =  getString(ADLParam.VERSION_STATUS, ADLConstants.ARCH_DEFAULT_VERSION_STATUS);
        if (!StringUtils.isEmpty(status))
            status = rmDelimiter + status;

        String buildCount =  getString(ADLParam.BUILD_COUNT, ADLConstants.ARCH_DEFAULT_BUILD_COUNT);
        if (!StringUtils.isEmpty(buildCount))
            buildCount = delimiter + buildCount;


        ArchetypeId aid = new ArchetypeId();
        aid.setValue(archIdPrefix + delimiter + name + delimiter + archVersion + status + buildCount);

        return aid;
    }

    public ResourceDescription createResourceDescription()
    {
        ResourceDescription rd = ofrm.createResourceDescription();
        rd.setCopyright(getString(ADLParam.COPYRIGHT, ADLConstants.ARCH_DEFAULT_COPYRIGHT));
        rd.setLifecycleState(getString(ADLParam.LIFECYCLE_STATE, ADLConstants.ARCH_DEFAULT_LIFECYCLE_STATE));

        Object parent = getValue(ADLParam.PARENT_ARCHETYPE);
        if ((parent != null)&&(parent instanceof AuthoredResource))
            rd.setParentResource((AuthoredResource) parent);

        String resourcePackageURI = getString(ADLParam.RESOURCE_PACKAGE_URI, null);
        if (!StringUtils.isEmpty(resourcePackageURI))
            rd.setResourcePackageUri(resourcePackageURI);

        return rd;
    }

    private String getString(ADLParam param, String default_value)
    {
        Object value = getValue(param);

        if (value == null)
            return default_value;

        return value.toString();
    }

    private Object getValue(ADLParam param)
    {
        for (ADLSetting setting : this.settings_)
            if (setting.param == param)
                return setting.value;

        return null;
    }

}
