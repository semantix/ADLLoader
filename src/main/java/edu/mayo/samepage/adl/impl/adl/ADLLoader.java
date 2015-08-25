package edu.mayo.samepage.adl.impl.adl;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.openehr.adl.parser.AdlDeserializer;
import org.openehr.adl.parser.BomSupportingReader;
import org.openehr.jaxb.am.Archetype;

import java.io.*;
import java.net.URL;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADLLoader
{
    public static Archetype loadFromFile(File adlsFile) throws FileNotFoundException, IOException
    {
        AdlDeserializer adlDeserializer = new AdlDeserializer();
        InputStream in = new FileInputStream(adlsFile);
        Reader reader = new BomSupportingReader(in, Charsets.UTF_8);
        return adlDeserializer.parse(IOUtils.toString(reader));
    }

    public static Archetype loadFromURL(URL adlsUrl) throws FileNotFoundException, IOException
    {
        AdlDeserializer adlDeserializer = new AdlDeserializer();
        InputStream in = adlsUrl.openStream();
        Reader reader = new BomSupportingReader(in, Charsets.UTF_8);
        return adlDeserializer.parse(IOUtils.toString(reader));
    }
}
