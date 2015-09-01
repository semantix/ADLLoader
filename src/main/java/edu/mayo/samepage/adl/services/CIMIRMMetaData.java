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
        setRMClassName("ITEM_GROUP");
        setRMRelaseVersion("2.0.2");

        setADLVersion("2.0");

        setDefaultTerminologySetName("snomed-ct");
    }
}
