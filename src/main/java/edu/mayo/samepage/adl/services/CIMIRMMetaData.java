package edu.mayo.samepage.adl.services;

import edu.mayo.samepage.adl.impl.adl.ADLMetaData;

/**
 * Created by dks02 on 8/25/15.
 */
public class CIMIRMMetaData extends ADLMetaData
{
    public CIMIRMModel cimirm = new CIMIRMModel();

    public CIMIRMMetaData()
    {
        super();
        setRMPublisher("CIMI");
        setRMRelaseVersion("3.0.4");

        setADLVersion("2.0");

        setDefaultTerminologySetName("snomed-ct");
    }
}
