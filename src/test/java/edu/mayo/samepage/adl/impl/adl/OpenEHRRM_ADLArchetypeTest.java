package edu.mayo.samepage.adl.impl.adl;

import com.google.common.collect.ImmutableList;
import edu.mayo.samepage.adl.impl.adl.env.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.env.IDType;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import junit.framework.TestCase;
import org.junit.Test;
import org.openehr.adl.rm.OpenEhrRmModel;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;
import org.openehr.jaxb.am.CObject;
import org.openehr.jaxb.am.Cardinality;
import org.openehr.jaxb.rm.MultiplicityInterval;

import static org.openehr.adl.am.AmObjectFactory.*;
import static org.openehr.adl.rm.RmObjectFactory.*;

/**
 * Created by dks02 on 7/28/15.
 */
public class OpenEHRRM_ADLArchetypeTest extends TestCase
{
    public String rmPackageName = "DBGAP";

    public RmType CLUSTER = null;
    private RmType ELEMENT = null;
    private RmType DV_CODED_TEXT = null;
    private RmType DV_COUNT = null;
    private RmType DV_QUANTITY = null;

    private RmTypeAttribute items = null;
    private RmTypeAttribute value = null;
    private RmTypeAttribute defining_code = null;
    //private RmTypeAttribute terminologyId = null;

    private ADLMetaData openEHRMetaData = null;

    private ADLArchetypeHelper helper_ = new ADLArchetypeHelper();

    private OpenEhrRmModel openEHRrm_ = OpenEhrRmModel.getInstance();

    private MultiplicityInterval occurrence11 = null;
    private MultiplicityInterval occurrence01 = null;
    private MultiplicityInterval occurrence0n = null;
    private MultiplicityInterval occurrence1n = null;

    private Cardinality cardinality01 = null;
    private Cardinality cardinality11 = null;
    private Cardinality cardinality0n = null;
    private Cardinality cardinality1n = null;


    @Override
    protected void setUp() throws Exception
    {
        String topClass = "CLUSTER";

        super.setUp();
        ADLSettings adlSettings = new ADLSettings();
        ADLRMSettings adlrmSettings = new ADLRMSettings(ADLRM.OPENEHR);

        // Get Reference Model Classes
        CLUSTER = openEHRrm_.getRmType(topClass);
        assertNotNull(CLUSTER);

        ELEMENT = openEHRrm_.getRmType("ELEMENT");
        assertNotNull(ELEMENT);

        DV_CODED_TEXT = openEHRrm_.getRmType("DV_CODED_TEXT");
        assertNotNull(DV_CODED_TEXT);

        DV_COUNT = openEHRrm_.getRmType("DV_COUNT");
        assertNotNull(DV_COUNT);

        DV_QUANTITY = openEHRrm_.getRmType("DV_QUANTITY");
        assertNotNull(DV_COUNT);

        items = openEHRrm_.getRmAttribute(CLUSTER.getRmType(), "items");
        assertNotNull(items);

        value = openEHRrm_.getRmAttribute(ELEMENT.getRmType(), "value");
        assertNotNull(value);

        defining_code = openEHRrm_.getRmAttribute(DV_CODED_TEXT.getRmType(), "defining_code");
        assertNotNull(defining_code);

        //terminologyId = openEHRrm_.getRmAttribute(DV_CODED_TEXT.getRmType(), "terminology_id");
        //assertNotNull(defining_code);

        adlrmSettings.setRMPackage(rmPackageName);

        openEHRMetaData = new ADLMetaData(adlSettings, adlrmSettings, topClass);

        // Another way of setting Top RM Class for which this archetype is about
        // DO NOT DELETE FOLLOWING LINE
        //adlrmSettings.setTopRMClassName(topClass);

        openEHRMetaData.setDefaultTerminologySetName("snomed-ct");

        occurrence01 = newMultiplicityInterval(0, 1);
        occurrence11 = newMultiplicityInterval(1, 1);
        occurrence0n = newMultiplicityInterval(0, null);
        occurrence1n = newMultiplicityInterval(1, null);

        cardinality01 = newCardinality(true, false, occurrence01);
        cardinality11 = newCardinality(true, false, occurrence11);
        cardinality0n = newCardinality(true, false, occurrence0n);
        cardinality1n = newCardinality(true, false, occurrence1n);
    }

    @Test
    public void testBaseArchetypeWithTerms()
    {
        String name = "testArchetype";
        String description = "This is a test archetype";

        ADLArchetype testArch = new ADLArchetype(name, openEHRMetaData);

        assertNotNull(testArch);

        testArch.addArchetypeTerm("id2", null, "testDefinition2", "testDefinition2-desc",
                null, "http://snomed.info/1234567");
        testArch.addArchetypeTerm("id3", "es", "testDefinitionES3", "testDefinitionES3-desc",
                null, "http://snomed.info/345678");
        testArch.addArchetypeTerm("id4", "es", "testDefinitionES4", "testDefinitionES4-desc",
                "loinc", "http://loinc.terms/5672rf");
        testArch.addArchetypeTerm("id5", null, "testDefinition5", "testDefinition5-desc",
                "loinc", "http://snomed.info/3444en");

        String archText = testArch.serialize();
        assertNotNull(archText);
    }

    // TEST: testIdentifierConstraint
    //
    //            definition
    //            CLUSTER[id1] matches {    -- dbGapTestArchetype
    //            items matches {
    //                ELEMENT[id2] occurrences matches {1} matches {    -- SUBJID
    //                    value matches {
    //                        IDENTIFIER[id3] occurrences matches {1}     -- IDENTIFIER
    //                    }
    //                }
    //            }
    //        }

    @Test
    public void testIdentifierConstraint()
    {
        String name = "dbGapTestArchetype1";
        String description = "Test Archetype for Testing Identifier constraint";

        ADLArchetype dbGapArch = new ADLArchetype(name, openEHRMetaData);
        assertNotNull(dbGapArch);

        String id1 = dbGapArch.addNewId(IDType.TERM, name, description);
        String id2 = dbGapArch.addNewId(IDType.TERM, "SUBJID", "Subject Identification");
        String id3 = dbGapArch.addNewId(IDType.TERM, "IDENTIFIER", "RM TYPE IDENTIFIER");

        RmType DV_IDENTIFIER = openEHRrm_.getRmType("DV_IDENTIFIER");
        assertNotNull(DV_IDENTIFIER);

        dbGapArch.setDefinition(
                newCComplexObject(CLUSTER.getRmType(), null, id1, ImmutableList.of(
                        newCAttribute(items.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                newCComplexObject(ELEMENT.getRmType(), occurrence1n, id2, ImmutableList.of(
                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                newCComplexObject(DV_IDENTIFIER.getRmType(), null, id3, null)
                                        ))
                                ))
                        ))
                )));


        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = dbGapArch.serialize();

        assertNotNull(dbGapArchTxt);
    }

    @Test
    public void testRangeConstraint()
    {
        String name = "dbGapTestArchetype2";
        String description = "Test Archetype for Testing Integer Range Constraint";

        ADLArchetype dbGapArch = new ADLArchetype(name, openEHRMetaData);
        assertNotNull(dbGapArch);

        String id1 = dbGapArch.addNewId(IDType.TERM, name, description);
        String id2 = dbGapArch.addNewId(IDType.TERM, "Patient Age", "Patient Age");
        String id3 = dbGapArch.addNewId(IDType.TERM, "Age", "Age Range");

        dbGapArch.setDefinition(
                newCComplexObject(CLUSTER.getRmType(), null, id1, ImmutableList.of(
                        newCAttribute(items.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                newCComplexObject(ELEMENT.getRmType(), occurrence11, id2, ImmutableList.of(
                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                newCComplexObject(DV_COUNT.getRmType(), occurrence11, id3, ImmutableList.of(
                                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                                newCInteger(newIntervalOfInteger(33, 90), null)
                                                        ))
                                                ))
                                        ))
                                ))
                        ))
                )));


        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = dbGapArch.serialize();

        assertNotNull(dbGapArchTxt);
    }


    @Test
    public void testValueSetConstraint()
    {
        String name = "dbGapTestArchetype3";
        String description = "Test Archetype for Testing Value Set Constraint";

        ADLArchetype dbGapArch = new ADLArchetype(name, openEHRMetaData);
        assertNotNull(dbGapArch);

        String id1 = dbGapArch.addNewId(IDType.TERM, name, description);
        String id2 = dbGapArch.addNewId(IDType.TERM, "Patient Gender", "Administrative Gender Attribute");
        String id3 = dbGapArch.addNewId(IDType.TERM, "Gender", "Patient Gender");

        String vs1 = dbGapArch.addNewId(IDType.VALUESET, "Administrative Gender", "Selected administrative genders");
        String pv1 = dbGapArch.addNewId(IDType.VALUESETMEMBER, "M", "Male");
        String pv2 = dbGapArch.addNewId(IDType.VALUESETMEMBER, "F", "Female");

        dbGapArch.updateValueSet(vs1, pv1, pv2);

        dbGapArch.setDefinition(
                newCComplexObject(CLUSTER.getRmType(), null, id1, ImmutableList.of(
                        newCAttribute(items.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                newCComplexObject(ELEMENT.getRmType(), occurrence11, id2, ImmutableList.of(
                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                newCComplexObject(DV_CODED_TEXT.getRmType(), null, id3, ImmutableList.of(
                                                        newCAttribute(defining_code.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                                newCTerminologyCode(vs1, null)
                                                        ))
                                                ))
                                        ))
                                ))
                        ))
                )));


        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = dbGapArch.serialize();

        assertNotNull(dbGapArchTxt);
    }

    @Test
    public void testChoice1ToNConstraint()
    {
        String name = "dbGapTestArchetype4";
        String description = "Test Archetype for choice 1 to n (value is either from a integer range or from a coded set)";

        ADLArchetype dbGapArch = new ADLArchetype(name, openEHRMetaData);
        assertNotNull(dbGapArch);

        String id1 = dbGapArch.addNewId(IDType.TERM, name, description);
        String id2 = dbGapArch.addNewId(IDType.TERM, "Patient BMI", "Body Mass Index Recording");
        String id3 = dbGapArch.addNewId(IDType.TERM, "BMI", "Patient Body Mass Index");
        String id4 = dbGapArch.addNewId(IDType.TERM, "BMI Alternate", "Patient Body Mass Index Alternate values");

        String vs1 = dbGapArch.addNewId(IDType.VALUESET, "BMI Alternate Aalues", "Alternative set of values");
        String pv1 = dbGapArch.addNewId(IDType.VALUESETMEMBER, ".", "Missing");
        String pv2 = dbGapArch.addNewId(IDType.VALUESETMEMBER, "99", "Unknown");

        dbGapArch.updateValueSet(vs1, pv1, pv2);

        dbGapArch.setDefinition(
                newCComplexObject(CLUSTER.getRmType(), null, id1, ImmutableList.of(
                        newCAttribute(items.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                newCComplexObject(ELEMENT.getRmType(), occurrence11, id2, ImmutableList.of(
                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                newCComplexObject(DV_QUANTITY.getRmType(), occurrence01, id3, ImmutableList.of(
                                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                                newCReal(newIntervalOfReal(16.2, 67.3), null)
                                                        ))
                                                )),
                                                newCComplexObject(DV_CODED_TEXT.getRmType(), occurrence01, id4, ImmutableList.of(
                                                        newCAttribute(defining_code.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                                newCTerminologyCode(vs1, null)
                                                        ))
                                                ))
                                        ))
                                ))
                        ))
                )));


        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = dbGapArch.serialize();

        assertNotNull(dbGapArchTxt);
    }
}