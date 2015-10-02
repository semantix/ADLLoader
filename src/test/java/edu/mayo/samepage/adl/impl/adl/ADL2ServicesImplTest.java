package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.adl.impl.adl.am.ADLSettings;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRM;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMFactory;
import edu.mayo.samepage.adl.impl.adl.rm.ADLRMSettings;
import edu.mayo.samepage.adl.services.ADL2ServicesImpl;
import edu.mayo.samepage.adl.services.ADLTerminologyServices;
import edu.mayo.samepage.adl.services.CIMIPrimitiveTypes;
import junit.framework.TestCase;
import org.junit.Test;
import org.opencimi.adl.rm.OpenCimiRmModel;
import org.openehr.adl.rm.RmObjectFactory;
import org.openehr.adl.rm.RmType;
import org.openehr.adl.rm.RmTypeAttribute;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.CPrimitiveObject;
import org.openehr.jaxb.am.Cardinality;
import org.openehr.jaxb.rm.IntervalOfInteger;
import org.openehr.jaxb.rm.MultiplicityInterval;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    public String rmPackageName = "DBGAP";

    public RmType ITEM_GROUP = null;
    private RmType ELEMENT = null;
    private RmType COUNT = null;

    private RmTypeAttribute item = null;
    private RmTypeAttribute value = null;

    private ADLMetaData cimiMetaData = null;
    private ADLServices adlServices_ = new ADL2ServicesImpl();

    private ADLAMConstraintFactory am_ = new ADLAMConstraintFactory();
    private ADLRMFactory rm_ = new ADLRMFactory();

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
        super.setUp();
        ADLSettings adlSettings = new ADLSettings();
        ADLRMSettings adlrmSettings = new ADLRMSettings(ADLRM.OPENCIMI);

        // Get Reference Model Classes
        ITEM_GROUP = cimirm_.getRmType("ITEM_GROUP");
        assertNotNull(ITEM_GROUP);

        ELEMENT = cimirm_.getRmType("ELEMENT");
        assertNotNull(ELEMENT);

        COUNT = cimirm_.getRmType("COUNT");
        assertNotNull(COUNT);

        item = cimirm_.getRmAttribute(ITEM_GROUP.getRmType(), "item");
        assertNotNull(item);

        value = cimirm_.getRmAttribute(ELEMENT.getRmType(), "value");
        assertNotNull(value);

        adlrmSettings.setRMPackage(rmPackageName);

        adlrmSettings.setRMClassName(ITEM_GROUP.getRmType());

        cimiMetaData = new ADLMetaData(adlSettings, adlrmSettings);
        cimiMetaData.setDefaultTerminologySetName("snomed-ct");

        occurrence01 = rm_.createMultiplicityInterval(0, 1);
        occurrence11 = rm_.createMultiplicityInterval(1, 1);
        occurrence0n = rm_.createMultiplicityInterval(0, null);
        occurrence1n = rm_.createMultiplicityInterval(1, null);

        cardinality01 = am_.createCardinality(occurrence01, true, false);
        cardinality11 = am_.createCardinality(occurrence11, true, false);
        cardinality0n = am_.createCardinality(occurrence0n, true, false);
        cardinality1n = am_.createCardinality(occurrence1n, true, false);
    }

    private ADLArchetype createBaseArchetype(String name, String description)
    {
        return adlServices_.createArchetype(name, description, cimiMetaData);
    }

    @Test
    public void testBaseArchetypeWithTerms()
    {
        String name = "testArchetype";
        String description = "This is a test archetype";

        ADLArchetype testArch = createBaseArchetype(name, description);
        assertNotNull(testArch);

        testArch.addArchetypeTerm("id2", null, "testDefinition2", "testDefinition2-desc",
                null, "http://snomed.info/1234567");
        testArch.addArchetypeTerm("id3", "es", "testDefinitionES3", "testDefinitionES3-desc",
                null, "http://snomed.info/345678");
        testArch.addArchetypeTerm("id4", "es", "testDefinitionES4", "testDefinitionES4-desc",
                "loinc", "http://loinc.terms/5672rf");
        testArch.addArchetypeTerm("id5", null, "testDefinition5", "testDefinition5-desc",
                "loinc", "http://snomed.info/3444en");

        String archText = adlServices_.serialize(testArch);
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
        ADLArchetype dbGapArch = adlServices_.createArchetype("dbGapTestArchetype1",
                "Test Archetype for Testing Identifier constraint",
                cimiMetaData);
        assertNotNull(dbGapArch);

        // ----------------------------------------------------------------------
        String cId = cimiMetaData.createNewId(); // creates next id
        String text = "SUBJID";
        String description = "Subject Identification";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
        // ----------------------------------------------------------------------

        CComplexObject c1 = am_.createComplexObjectConstraint(ELEMENT, cId, occurrence1n);

        // ----------------------------------------------------------------------
        cId = cimiMetaData.createNewId();
        text = "IDENTIFIER";
        description = "RM TYPE IDENTIFIER";
        conceptReference = ADLTerminologyServices.getConceptReference(cId);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
        // ----------------------------------------------------------------------

        RmType IDENTIFIER = cimirm_.getRmType("IDENTIFIER");
        assertNotNull(IDENTIFIER);

        CComplexObject c11 = am_.createComplexObjectConstraint(IDENTIFIER, cId, null);

        am_.addAttributeConstraints(c1, value, null, null, c11);

        am_.addAttributeConstraints(dbGapArch.getDefinition(), item, null, null, c1);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices_.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }

    @Test
    public void testRangeConstraint()
    {
        ADLArchetype dbGapArch = adlServices_.createArchetype("dbGapTestArchetype2",
                "Test Archetype for Testing Integer Range Constraint",
                cimiMetaData);
        assertNotNull(dbGapArch);

        // ----------------------------------------------------------------------
        String cId = cimiMetaData.createNewId(); // creates next id
        String text = "Age";
        String description = "Age Range";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
        // ----------------------------------------------------------------------

        CComplexObject c1 = am_.createComplexObjectConstraint(ELEMENT, cId, occurrence11);

        IntervalOfInteger intervalOfInteger = RmObjectFactory.newIntervalOfInteger(33, 90);
        CPrimitiveObject intervalConstraint = am_.createIntervalConstraint(CIMIPrimitiveTypes.INTEGER, intervalOfInteger, null);

        am_.addAttributeConstraints(c1, value, null, null, intervalConstraint);

        am_.addAttributeConstraints(dbGapArch.getDefinition(), item, null, null, c1);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices_.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }

//        // ############ Constraint 2 #########################
//        // Constraint referencing to a value set - encoded values
//        //
//        // ELEMENT[id3] occurrences matches {0..1} matches
//        //
//        CComplexObject c2 = helper.createComplexObjectConstraint(element, cId, occurrence01);
//        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
//
//        // Value Set and its entries
//        String vsId = cimiMetaData.createNewValueSetId(); // creates first value set id
//        String vsDef = "primary sites";
//        String vsDesc = "primary sites description here";
//        String vsConRef = ADLTerminologyServices.getConceptReference(vsId);
//        //
//        //  ["ac1"] = <
//        //      text = <"primary sites">
//        //      description = <"xxx">
//        //  >
//        //
//        dbGapArch.addArchetypeTerm(vsId, null, vsDef, vsDesc, null, vsConRef);
//
//        String pvId1 = cimiMetaData.createNewPermissibleValueId();
//        String pv1Def = "COLN";
//        String pv1Desc = "colonic (includes appendix)";
//        String pv1ConRef = ADLTerminologyServices.getConceptReference(pvId1);
//        //
//        //        "at4"] = <
//        //        text = <"COLN">
//        //            description = <"Colonic (includes appendix)">
//        //        >
//        //
//        dbGapArch.addArchetypeTerm(pvId1, null, pv1Def, pv1Desc, null, pv1ConRef);
//
//        String pvId2 = cimiMetaData.createNewPermissibleValueId();
//        String pv2Def = "RECT";
//        String pv2Desc = "Rectal";
//        String pv2ConRef = ADLTerminologyServices.getConceptReference(pvId2);
//        //
//        //        ["at5"] = <
//        //        text = <"RECT">
//        //            description = <"Rectal">
//        //        >
//        //
//        dbGapArch.addArchetypeTerm(pvId2, null, pv2Def, pv2Desc, null, pv2ConRef);
//
//        // Creates Value-Set entries
//        //
//        //  value_sets = <
//        //      ["ac1"] = <
//        //      id = <"ac1">
//        //          members = <"at4", "at5">
//        //          >
//        //
//        dbGapArch.updateValueSet(vsId, pvId1, pvId2);
//        CTerminologyCode terminologyCode = CIMITypes.getTerminologyConstraint(vsId, null);
//
//        if (terminologyCode != null)
//            helper.addAttributeConstraints(c2, valueAtt, null, null, terminologyCode);
//
//        // ----------------------------------------------------------------------
//        cId = cimiMetaData.createNewId();
//        text = "Age";
//        description = "Age at the time the specimen was taken";
//        conceptReference = ADLTerminologyServices.getConceptReference(cId);
//
//        // ############ Constraint 3 #########################
//        // Constraint with integer interval
//        CComplexObject c3 = helper.createComplexObjectConstraint(element, cId, occurrence01);
//        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
//
//        cId = cimiMetaData.createNewId();
//        text = "Age Interval";
//        description = "Valid age Interval";
//        conceptReference = ADLTerminologyServices.getConceptReference(cId);
//
//        CComplexObject c31 = helper.createComplexObjectConstraint(count, cId, occurrence01);
//        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);
//
//        helper.addAttributeConstraints(c3, valueAtt, null, null, c31);
//
//        IntervalOfInteger intervalOfInteger = (IntervalOfInteger) CIMITypes.getIntervalFor(CIMIPrimitiveTypes.INTEGER);
//        intervalOfInteger.setLower(33);
//        intervalOfInteger.setUpper(90);
//        intervalOfInteger.setLowerIncluded(Boolean.TRUE);
//        intervalOfInteger.setUpperIncluded(Boolean.TRUE);
//
//        CPrimitiveObject primitiveObject = CIMITypes.createPrimitiveTypeConstraints(CIMIPrimitiveTypes.INTEGER, intervalOfInteger, null);
//        helper.addAttributeConstraints(c31, valueAtt, null, null, primitiveObject);
//
//        // ----------------------------------------------------------------------
//        //
//        // Finally add all constraints to the main definition of the archetype
//        //
//
//        helper.addAttributeConstraints(dbGapArch.getDefinition(), itemAtt, null, null, c1, c2, c3);
//
//        // ----------------------------------------------------------------------
//        // Serialize the Archetype into ADL 2.0 text rendering
//        String dbGapArchTxt = adlServices.serialize(dbGapArch);
//
//        assertNotNull(dbGapArchTxt);
}