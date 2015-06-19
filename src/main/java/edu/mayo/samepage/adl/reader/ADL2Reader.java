package edu.mayo.samepage.adl.reader;

import org.openehr.adl.rm.RmModel;
import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.openehr.adl.parser.AdlDeserializer;
import org.openehr.adl.parser.BomSupportingReader;
import org.openehr.adl.rm.OpenEhrRmModel;
import org.openehr.adl.rm.RmModel;
import org.openehr.jaxb.am.Archetype;

import java.io.*;

/**
 * Created by dks02 on 12/12/14.
 */
public class ADL2Reader
{
    private String adlFolder = ".";
    private RmModel rmModel = OpenEhrRmModel.getInstance();
    private String defaultRegex = ".\\.adls";

    public String getAdlFolderOfFilePath()
    {
        return adlFolder;
    }

    public void setAdlFolderOfFilePath(String adlFolder)
    {
        this.adlFolder = adlFolder;
    }

    public RmModel getReferenceModel()
    {
        return rmModel;
    }

    public void setReferenceModel(RmModel refModel)
    {
        if (refModel != null)
            rmModel = refModel;
    }

    public Archetype getADLArchetype(File adlFile) throws FileNotFoundException, IOException
    {
        AdlDeserializer adlDeserializer = new AdlDeserializer();
        InputStream in = new FileInputStream(adlFile);
        Reader reader = new BomSupportingReader(in, Charsets.UTF_8);
        return adlDeserializer.parse(IOUtils.toString(reader));
    }
}
