package edu.mayo.samepage.adl.impl.adl.rm;

import edu.mayo.samepage.adl.IF.ADLParam;
import edu.mayo.samepage.adl.impl.adl.am.Settings;

/**
 * Created by dks02 on 10/1/15.
 */
public class ADLRMSettings extends Settings
{
    public ADLRMSettings(ADLRM rm)
    {
        switch (rm)
        {
            case OPENCIMI:
                    setValue(ADLParam.RM_RELEASE, ADLRMConstants.OpenCIMIRM_RELEASE_VERSION);
                    setValue(ADLParam.RM_PUBLISHER, ADLRMConstants.OpenCIMIRM_DEFAULT_PUBLISHER);
                    setValue(ADLParam.RM_PACKAGE, ADLRMConstants.OpenCIMIRM_DEFAULT_PACKAGE);
                    setValue(ADLParam.RM_CLASS, ADLRMConstants.OpenCIMIRM_DEFAULT_CLASS);
                    break;
            default:
                    setValue(ADLParam.RM_RELEASE, ADLRMConstants.OpenEHRRM_RELEASE_VERSION);
                    setValue(ADLParam.RM_PUBLISHER, ADLRMConstants.OpenEHRRM_DEFAULT_PUBLISHER);
                    setValue(ADLParam.RM_PACKAGE, ADLRMConstants.OpenEHRRM_DEFAULT_PACKAGE);
                    setValue(ADLParam.RM_CLASS, ADLRMConstants.OpenEHRRM_DEFAULT_CLASS);
        }
    }


    public String getRMPublisher()
    {
        return getString(ADLParam.RM_PUBLISHER, ADLRMConstants.OpenEHRRM_DEFAULT_PUBLISHER);
    }

    public void setRMPublisher(String rmPublisher)
    {
        setValue(ADLParam.RM_PUBLISHER, rmPublisher);
    }

    public String getRMPackage()
    {
        return getString(ADLParam.RM_PACKAGE, ADLRMConstants.OpenEHRRM_DEFAULT_PACKAGE);
    }

    public void setRMPackage(String rmPackage)
    {
        setValue(ADLParam.RM_PACKAGE, rmPackage);
    }

    public String getRMRelaseVersion()
    {
        return getString(ADLParam.RM_RELEASE, ADLRMConstants.OpenEHRRM_RELEASE_VERSION);
    }

    public void setRMRelaseVersion(String rmVersion)
    {
        setValue(ADLParam.RM_RELEASE, rmVersion);
    }
    public String getRMClassName()
    {
        return getString(ADLParam.RM_CLASS, ADLRMConstants.OpenEHRRM_DEFAULT_CLASS);
    }

    public void setRMClassName(String rmClassName)
    {
        setValue(ADLParam.RM_CLASS, rmClassName);
    }
}
