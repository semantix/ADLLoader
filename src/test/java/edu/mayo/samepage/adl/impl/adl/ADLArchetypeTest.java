package edu.mayo.samepage.adl.impl.adl;

import com.google.common.collect.ImmutableList;
import edu.mayo.samepage.adl.impl.adl.env.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.env.IDType;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import junit.framework.TestCase;
import org.junit.Test;
import org.opencimi.adl.rm.OpenCimiRmModel;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;
import org.openehr.jaxb.am.CObject;
import org.openehr.jaxb.am.Cardinality;
import org.openehr.jaxb.rm.MultiplicityInterval;

import static org.openehr.adl.am.AmObjectFactory.newCAttribute;
import static org.openehr.adl.am.AmObjectFactory.newCComplexObject;
import static org.openehr.adl.am.AmObjectFactory.newCardinality;
import static org.openehr.adl.rm.RmObjectFactory.newMultiplicityInterval;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADLArchetypeTest extends TestCase
{
    public String rmPackageName = "DBGAP";

    public RmType ITEM_GROUP = null;
    private RmType ELEMENT = null;
    private RmType CODEDTEXT = null;
    private RmType COUNT = null;

    private RmTypeAttribute item = null;
    private RmTypeAttribute value = null;
    private RmTypeAttribute code = null;
    private RmTypeAttribute terminologyId = null;

    private ADLMetaData cimiMetaData = null;

    private ADLArchetypeHelper helper_ = new ADLArchetypeHelper();

    private OpenCimiRmModel cimirm_ = OpenCimiRmModel.getInstance();

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
        String topClass = "ITEM_GROUP";

        super.setUp();
        ADLSettings adlSettings = new ADLSettings();
        ADLRMSettings adlrmSettings = new ADLRMSettings(ADLRM.OPENCIMI);

        // Get Reference Model Classes
        ITEM_GROUP = cimirm_.getRmType(topClass);
        assertNotNull(ITEM_GROUP);

        ELEMENT = cimirm_.getRmType("ELEMENT");
        assertNotNull(ELEMENT);

        CODEDTEXT = cimirm_.getRmType("CODED_TEXT");
        assertNotNull(CODEDTEXT);

        COUNT = cimirm_.getRmType("COUNT");
        assertNotNull(COUNT);

        item = cimirm_.getRmAttribute(ITEM_GROUP.getRmType(), "item");
        assertNotNull(item);

        value = cimirm_.getRmAttribute(ELEMENT.getRmType(), "value");
        assertNotNull(value);

        code = cimirm_.getRmAttribute(CODEDTEXT.getRmType(), "code");
        assertNotNull(code);

        terminologyId = cimirm_.getRmAttribute(CODEDTEXT.getRmType(), "terminology_id");
        assertNotNull(code);

        adlrmSettings.setRMPackage(rmPackageName);

        cimiMetaData = new ADLMetaData(adlSettings, adlrmSettings, topClass);

        // Another way of setting Top RM Class for which this archetype is about
        // DO NOT DELETE FOLLOWING LINE
        //adlrmSettings.setTopRMClassName(topClass);

        cimiMetaData.setDefaultTerminologySetName("snomed-ct");

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

        ADLArchetype testArch = new ADLArchetype(name, cimiMetaData);

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
    //            ITEM_GROUP[id1] matches {    -- dbGapTestArchetype
    //            item matches {
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

        ADLArchetype dbGapArch = new ADLArchetype(name, cimiMetaData);
        assertNotNull(dbGapArch);

        String id1 = dbGapArch.addNewId(IDType.TERM, name, description);
        String id2 = dbGapArch.addNewId(IDType.TERM, "SUBJID", "Subject Identification");
        String id3 = dbGapArch.addNewId(IDType.TERM, "IDENTIFIER", "RM TYPE IDENTIFIER");

//        String id1 = "id1";
//        String id2 = "id2";
//        String id3 = "id3";

        RmType IDENTIFIER = cimirm_.getRmType("IDENTIFIER");
        assertNotNull(IDENTIFIER);

        dbGapArch.setDefinition(
                newCComplexObject(ITEM_GROUP.getRmType(), null, id1, ImmutableList.of(
                        newCAttribute(item.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                newCComplexObject(ELEMENT.getRmType(), occurrence1n, id2, ImmutableList.of(
                                        newCAttribute(value.getAttributeName(), null, null, ImmutableList.<CObject>of(
                                                newCComplexObject(IDENTIFIER.getRmType(), null, id3, null)
                                        ))
                                ))
                        ))
                )));


        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = dbGapArch.serialize();

        assertNotNull(dbGapArchTxt);
    }

    /*
    @Test
    public void testRangeConstraint()
    {
        ADLArchetype dbGapArch = adlServices_.createArchetype("dbGapTestArchetype2",
                "Test Archetype for Testing Integer Range Constraint",
                cimiMetaData);
        assertNotNull(dbGapArch);

        // ----------------------------------------------------------------------
        String cId = cimiMetaData.createNewId(); // creates next id
        String text = "Patient Age";
        String description = "Patinet Age";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
        // ----------------------------------------------------------------------

        CComplexObject c1 = am_.createComplexObjectConstraint(ELEMENT, cId, occurrence11);

        cId = cimiMetaData.createNewId(); // creates next id
        text = "Age";
        description = "Age Range";
        conceptReference = ADLTerminologyServices.getConceptReference(cId);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        CComplexObject c11 = am_.createComplexObjectConstraint(COUNT, cId, occurrence01);

        IntervalOfInteger intervalOfInteger = RmObjectFactory.newIntervalOfInteger(33, 90);
        CPrimitiveObject intervalConstraint = newCInteger(CIMIPrimitiveTypes.INTEGER, intervalOfInteger, null);

        am_.addAttributeConstraints(c11, value, null, null, intervalConstraint);

        am_.addAttributeConstraints(c1, value, null, null, c11);

        am_.addAttributeConstraints(dbGapArch.getDefinition(), item, null, null, c1);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices_.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }

    @Test
    public void testValueSetConstraint()
    {
        ADLArchetype dbGapArch = adlServices_.createArchetype("dbGapTestArchetype3",
                "Test Archetype for Testing Value Set Constraint",
                cimiMetaData);
        assertNotNull(dbGapArch);

        String cId1 = cimiMetaData.createNewId(); // creates next id
        String text = "site";
        String description = "Procedure Site";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId1);
        dbGapArch.addArchetypeTerm(cId1, null, text, description, null, conceptReference);

        CComplexObject c1 = am_.createComplexObjectConstraint(ELEMENT, cId1, occurrence01);

        // ----------------------------------------------------------------------
        // Value Set and its entries
        String vsId = cimiMetaData.createNewValueSetId(); // creates first value set id
        String vsDef = "primary sites";
        String vsDesc = "primary sites description here";
        String vsConRef = ADLTerminologyServices.getConceptReference(vsId);
        dbGapArch.addArchetypeTerm(vsId, null, vsDef, vsDesc, null, vsConRef);
        // ----------------------------------------------------------------------

        // Permissible Value 1
        String pvId1 = cimiMetaData.createNewPermissibleValueId();
        String pv1Def = "COLN";
        String pv1Desc = "colonic (includes appendix)";
        String pv1ConRef = ADLTerminologyServices.getConceptReference(pvId1);
        dbGapArch.addArchetypeTerm(pvId1, null, pv1Def, pv1Desc, null, pv1ConRef);

        // Permissible Value 2
        String pvId2 = cimiMetaData.createNewPermissibleValueId();
        String pv2Def = "RECT";
        String pv2Desc = "Rectal";
        String pv2ConRef = ADLTerminologyServices.getConceptReference(pvId2);
        dbGapArch.addArchetypeTerm(pvId2, null, pv2Def, pv2Desc, null, pv2ConRef);

        String [] members = null;
        //members = new String[]{"at1", "at2"};
        // Creates Value-Set entries, Associate permissible value into this value set
        dbGapArch.updateValueSet(vsId, pvId1, pvId2);
        CTerminologyCode terminologyCode = am_.getTerminologyConstraint(vsId, members);

        String cId11 = cimiMetaData.createNewId(); // creates next id
        CComplexObject c11 = am_.createComplexObjectConstraint(CODEDTEXT, cId11, occurrence01);

        if (terminologyCode != null)
        {
            if (members != null)
                am_.addAttributeConstraints(c11, code, null, null, terminologyCode);
            else
            {

                CString stringValue = (CString) am_.getPrimitiveType(CIMIPrimitiveTypes.STRING);
                stringValue.setAssumedValue("*");

                am_.addAttributeConstraints(c11, terminologyId, null, null, terminologyCode);
                am_.addAttributeConstraints(c11, code, null, null, stringValue);
            }
        }

        am_.addAttributeConstraints(c1, value, null, null, c11);

        am_.addAttributeConstraints(dbGapArch.getDefinition(), item, null, null, c1);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices_.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }

    @Test
    public void testChoice1ToNConstraint()
    {
        ADLArchetype dbGapArch = adlServices_.createArchetype("dbGapTestArchetype3",
                "Test Archetype for Testing Value Set Constraint",
                cimiMetaData);
        assertNotNull(dbGapArch);

        String cId1 = cimiMetaData.createNewId(); // creates next id
        String text = "site";
        String description = "Procedure Site";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId1);
        dbGapArch.addArchetypeTerm(cId1, null, text, description, null, conceptReference);

        CComplexObject c1 = am_.createComplexObjectConstraint(ELEMENT, cId1, occurrence01);

        // ----------------------------------------------------------------------
        // Value Set and its entries
        String vsId = cimiMetaData.createNewValueSetId(); // creates first value set id
        String vsDef = "primary sites";
        String vsDesc = "primary sites description here";
        String vsConRef = ADLTerminologyServices.getConceptReference(vsId);
        dbGapArch.addArchetypeTerm(vsId, null, vsDef, vsDesc, null, vsConRef);
        // ----------------------------------------------------------------------

        // Permissible Value 1
        String pvId1 = cimiMetaData.createNewPermissibleValueId();
        String pv1Def = "COLN";
        String pv1Desc = "colonic (includes appendix)";
        String pv1ConRef = ADLTerminologyServices.getConceptReference(pvId1);
        dbGapArch.addArchetypeTerm(pvId1, null, pv1Def, pv1Desc, null, pv1ConRef);

        // Permissible Value 2
        String pvId2 = cimiMetaData.createNewPermissibleValueId();
        String pv2Def = "RECT";
        String pv2Desc = "Rectal";
        String pv2ConRef = ADLTerminologyServices.getConceptReference(pvId2);
        dbGapArch.addArchetypeTerm(pvId2, null, pv2Def, pv2Desc, null, pv2ConRef);

        String [] members = null;
        //members = new String[]{"at1", "at2"};
        // Creates Value-Set entries, Associate permissible value into this value set
        dbGapArch.updateValueSet(vsId, pvId1, pvId2);
        CTerminologyCode terminologyCode = am_.getTerminologyConstraint(vsId, members);

        String cId11 = cimiMetaData.createNewId(); // creates next id
        CComplexObject c11 = am_.createComplexObjectConstraint(CODEDTEXT, cId11, occurrence01);

        if (terminologyCode != null)
        {
            if (members != null)
                am_.addAttributeConstraints(c11, code, null, null, terminologyCode);
            else
            {

                CString stringValue = (CString) am_.getPrimitiveType(CIMIPrimitiveTypes.STRING);
                stringValue.setAssumedValue("*");

                am_.addAttributeConstraints(c11, terminologyId, null, null, terminologyCode);
                am_.addAttributeConstraints(c11, code, null, null, stringValue);
            }
        }

        am_.addAttributeConstraints(c1, value, null, null, c11);

        am_.addAttributeConstraints(dbGapArch.getDefinition(), item, null, null, c1);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices_.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }
    */
}