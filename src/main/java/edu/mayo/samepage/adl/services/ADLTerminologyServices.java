package edu.mayo.samepage.adl.services;

/**
 * Created by dks02 on 9/2/15.
 */
public class ADLTerminologyServices
{
    public enum PROVIDER
    {
        SNOMED("http://snomed.info/"),
        LOINC("http://loinc.info/"),
        DEFAULT ("http://snomed.info/");

        private String uri_;
        PROVIDER(String uri)
        {
            this.uri_ = uri;
        }

        public String getServiceURI()
        {
            return this.uri_;
        }
    };

    public static String getConceptReference(PROVIDER provider, String id)
    {
        if (provider == null)
            return id;

        return provider.getServiceURI() + id;
    }

    public static String getConceptReference(String id)
    {
        return PROVIDER.DEFAULT.getServiceURI() + id;
    }
}
