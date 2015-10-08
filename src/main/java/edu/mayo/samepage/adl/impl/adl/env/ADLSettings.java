package edu.mayo.samepage.adl.impl.adl.env;

import org.openehr.jaxb.rm.ArchetypeId;
import org.openehr.jaxb.rm.CodePhrase;

/**
 * Created by dks02 on 8/20/15.
 */
public class ADLSettings extends Settings
{
    public ADLSettings()
    {
        setValue(ADLParam.ADL_VERSION, ADLConstants.ADL_DEFAULT_VERSION);
        setValue(ADLParam.ARCHETYPE_NAMESPACE, ADLConstants.ADL_DEFAULT_NS);
        setValue(ADLParam.RESOURCE_PACKAGE_URI, BLANK);
        setValue(ADLParam.MAJOR_VERSION, ADLConstants.ARCH_DEFAULT_MAJOR_VERSION);
        setValue(ADLParam.MINOR_VERSION, ADLConstants.ARCH_DEFAULT_MINOR_VERSION);
        setValue(ADLParam.PATCH_VERSION, ADLConstants.ARCH_DEFAULT_PATCH_VERSION);
        setValue(ADLParam.VERSION_STATUS, ADLConstants.ARCH_DEFAULT_VERSION_STATUS);
        setValue(ADLParam.BUILD_COUNT, ADLConstants.ARCH_DEFAULT_BUILD_COUNT);
        setValue(ADLParam.ORIGINAL_LANGUAGE, BLANK);
        setValue(ADLParam.COPYRIGHT, ADLConstants.ARCH_DEFAULT_COPYRIGHT);
        setValue(ADLParam.DETAILS, BLANK);
        setValue(ADLParam.LIFECYCLE_STATE, ADLConstants.ARCH_DEFAULT_LIFECYCLE_STATE);
        setValue(ADLParam.OTHER_CONTRIBUTORS, BLANK);
        setValue(ADLParam.PARENT_ARCHETYPE_ID, BLANK);
    }

    public boolean getIsGenerated()
    {
        return true;
    }
    public CodePhrase getOriginalLanguage()
    {
        Object orgLang = getValue(ADLParam.ORIGINAL_LANGUAGE);

        if ((orgLang != null)&&(orgLang instanceof CodePhrase))
            return (CodePhrase) orgLang;

        return null;
    }

    public void setOriginalLanguage(CodePhrase language)
    {
        setValue(ADLParam.ORIGINAL_LANGUAGE, language);
    }

    public String getADLVersion()
    {
        return getString(ADLParam.ADL_VERSION, ADLConstants.ADL_DEFAULT_VERSION);
    }

    public void setADLVersion(String adlVersion)
    {
        setValue(ADLParam.ADL_VERSION, adlVersion);
    }

    public String getMajorVersion()
    {
        return getString(ADLParam.MAJOR_VERSION, ADLConstants.ARCH_DEFAULT_MAJOR_VERSION);
    }

    public void setMajorVersion(String majorVersion)
    {
        setValue(ADLParam.MAJOR_VERSION, majorVersion);
    }

    public String getMinorVersion()
    {
        return getString(ADLParam.MINOR_VERSION, ADLConstants.ARCH_DEFAULT_MINOR_VERSION);
    }

    public void setMinorVersion(String minorVersion)
    {
        setValue(ADLParam.MINOR_VERSION, minorVersion);
    }

    public String getPatchVersion()
    {
        return getString(ADLParam.PATCH_VERSION, ADLConstants.ARCH_DEFAULT_PATCH_VERSION);
    }

    public void setPatchVersion(String patchVersion)
    {
        setValue(ADLParam.PATCH_VERSION, patchVersion);
    }

    public String getVersionStatus()
    {
        return getString(ADLParam.VERSION_STATUS, ADLConstants.ARCH_DEFAULT_VERSION_STATUS);
    }

    public void setVersionStatus(String versionStatus)
    {
        setValue(ADLParam.VERSION_STATUS, versionStatus);
    }

    public String getBuildCount()
    {
        return getString(ADLParam.BUILD_COUNT, ADLConstants.ARCH_DEFAULT_BUILD_COUNT);
    }

    public void setBuildCount(String buildCount)
    {
        setValue(ADLParam.BUILD_COUNT, buildCount);
    }

    public String getNameSpace()
    {
        return getString(ADLParam.ARCHETYPE_NAMESPACE, ADLConstants.ADL_DEFAULT_NS);
    }

    public void setNameSpace(String namespace)
    {
        setValue(ADLParam.ARCHETYPE_NAMESPACE, namespace);
    }

    public String getCopyright()
    {
        return getString(ADLParam.COPYRIGHT, ADLConstants.ARCH_DEFAULT_COPYRIGHT);
    }

    public void setCopyright(String copyright)
    {
        setValue(ADLParam.COPYRIGHT, copyright);
    }

    public String getResourcePackageURI()
    {
        return getString(ADLParam.RESOURCE_PACKAGE_URI, null);
    }

    public void setResourcePackageURI(String uriString)
    {
        setValue(ADLParam.RESOURCE_PACKAGE_URI, uriString);
    }

    public String getLifeCycleState()
    {
        return getString(ADLParam.LIFECYCLE_STATE, ADLConstants.ARCH_DEFAULT_LIFECYCLE_STATE);
    }

    public void setLifeCycleState(String lifeCycleState)
    {
        setValue(ADLParam.LIFECYCLE_STATE, lifeCycleState);
    }

    public ArchetypeId getParentID()
    {
        Object parentId =  getValue(ADLParam.PARENT_ARCHETYPE_ID);

        if ((parentId != null)&&(parentId instanceof ArchetypeId))
            return (ArchetypeId) parentId;

        return null;
    }

    public void setParentID(ArchetypeId parentId)
    {
        setValue(ADLParam.PARENT_ARCHETYPE_ID, parentId);
    }
}
