package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLParam;
import org.openehr.jaxb.rm.ArchetypeId;
import org.openehr.jaxb.rm.CodePhrase;

/**
 * Created by dks02 on 8/20/15.
 */
public class ADLMetaDataSettings extends ADLSettings
{
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

    public String getRMPublisher()
    {
        return getString(ADLParam.RM_PUBLISHER, ADLConstants.RM_DEFAULT_PUBLISHER);
    }

    public void setRMPublisher(String rmPublisher)
    {
        setValue(ADLParam.RM_PUBLISHER, rmPublisher);
    }

    public String getRMPackage()
    {
        return getString(ADLParam.RM_PACKAGE, ADLConstants.RM_DEFAULT_PACKAGE);
    }

    public void setRMPackage(String rmPackage)
    {
        setValue(ADLParam.RM_PACKAGE, rmPackage);
    }

    public String getRMRelaseVersion()
    {
        return getString(ADLParam.RM_RELEASE, ADLConstants.RM_RELEASE_VERSION);
    }

    public void setRMRelaseVersion(String rmVersion)
    {
        setValue(ADLParam.RM_RELEASE, rmVersion);
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

    public String getRMClassName()
    {
        return getString(ADLParam.RM_CLASS, ADLConstants.RM_DEFAULT_CLASS);
    }

    public void setRMClassName(String rmClassName)
    {
        setValue(ADLParam.RM_CLASS, rmClassName);
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
