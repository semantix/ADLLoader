package edu.mayo.samepage.adl.impl.adl.rm;

import org.apache.commons.lang.StringUtils;
import org.opencimi.adl.rm.OpenCimiRmModel;
import org.openehr.adl.rm.RmObjectFactory;
import org.openehr.jaxb.rm.*;

/**
 * Created by dks02 on 10/2/15.
 */
public class ADLRMFactory extends RmObjectFactory
{
    public ObjectFactory openEHRrm_ = new ObjectFactory();
    public OpenCimiRmModel openCIMIrm_ = OpenCimiRmModel.getInstance();

    // TERMINOLOGY_ID
    public TerminologyId createTerminologyId(String value)
    {
        return RmObjectFactory.newTerminologyId(value);
    }

    // CODE_PHRASE {"terminologyId", "codeString"}
    public CodePhrase createCodePhrase(TerminologyId id, String code)
    {
        return RmObjectFactory.newCodePhrase(id, code);
    }

    public ResourceDescription createResourceDescription(String copyright,
                                                         String lifeCycleState,
                                                         String packageUri,
                                                         AuthoredResource parentResource)
    {
        ResourceDescription rd = openEHRrm_.createResourceDescription();
        rd.setCopyright(copyright);
        rd.setLifecycleState(lifeCycleState);

        if (parentResource != null)
            rd.setParentResource(parentResource);

        if (!StringUtils.isEmpty(packageUri))
            rd.setResourcePackageUri(packageUri);

        return rd;
    }

    public MultiplicityInterval createMultiplicityInterval(Integer lower, Integer upper)
    {
        return RmObjectFactory.newMultiplicityInterval(lower, upper);
    }

    public StringDictionaryItem createStringDictionaryItem(String id, String value)
    {
        return RmObjectFactory.newStringDictionaryItem(id, value);
    }
}
