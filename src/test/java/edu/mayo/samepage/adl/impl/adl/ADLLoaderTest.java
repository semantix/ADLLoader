package edu.mayo.samepage.adl.impl.adl;

import org.junit.Test;
import org.lexemantix.utils.file.LMFileUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADLLoaderTest
{
    @Test
    public void testLoadFromFile() throws Exception
    {
        File startDir = new File(System.getProperty("user.dir"));
        List<File> adlsFiles = LMFileUtils.getAllFiles(startDir.getCanonicalPath(), "CIMI.*\\.adls");

        System.out.println("Testing " + adlsFiles.size() + " files from resources in the project...");
        for (File file : adlsFiles)
            assertNotNull(ADLLoader.loadFromFile(file));
        System.out.println("Test load from files completed!");
    }

    @Test
    public void testLoadFromURL() throws Exception
    {
        assertNotNull(ADLLoader.loadFromURL(
                new URL("https://raw.githubusercontent.com/opencimi/archetypes/master/miniCIMI/CIMI-CORE-PARTY.party.v1.0.0.adls")));
    }
}