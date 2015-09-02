package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.adl.services.*;
import junit.framework.TestCase;
import org.junit.Test;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.CPrimitiveObject;
import org.openehr.jaxb.am.CTerminologyCode;
import org.openehr.jaxb.rm.IntervalOfInteger;
import org.openehr.jaxb.rm.MultiplicityInterval;

/**
 * Created by dks02 on 7/28/15.
 */
public class ADL2ServicesImplTest extends TestCase
{
    @Test
    public void testGetArchetype()
    {
        CIMIRMMetaData cimi = new CIMIRMMetaData();
        ADLServices  adlServices = new ADL2ServicesImpl();

        String description = "This is a test archetype";

        ADLArchetype testArch = adlServices.createArchetype("testArchetype", description, cimi, null);
        assertNotNull(testArch);

        testArch.addArchetypeTerm("id2", null, "testDefinition2", "testDefinition2-desc", null, "http://snomed.info/1234567");
        testArch.addArchetypeTerm("id3", "es", "testDefinitionES3", "testDefinitionES3-desc", null, "http://snomed.info/345678");
        testArch.addArchetypeTerm("id4", "es", "testDefinitionES4", "testDefinitionES4-desc", "loinc", "http://loinc.terms/5672rf");
        testArch.addArchetypeTerm("id5", null, "testDefinition5", "testDefinition5-desc", "loinc", "http://snomed.info/3444en");
        String archText = adlServices.serialize(testArch);
        assertNotNull(archText);
    }

    @Test
    public void testAddConstraints()
    {
        CIMIRMMetaData cimi = new CIMIRMMetaData();
        cimi.setRMPackage("DBGAP");

        ADLArchetypeHelper helper = new ADLArchetypeHelper();
        ADLServices  adlServices = new ADL2ServicesImpl();

        // This creates Main definition and corresponding term definition
        // ITEM_GROUP[id1] matches
        //
        //term_definitions = <
        //  ["en"] = <
        //      ["id1"] = <
        //          text = <"Epigenetic Colorectal Cancer DD">
        //          description = <"Epigenetic Colorectal Cancer Subject Phenotypes Data Dictionary">
        //              >
        //
        ADLArchetype dbGapArch = adlServices.createArchetype("dbGapTestArchetype", "This is a test", cimi, helper);
        assertNotNull(dbGapArch);

        String item_group = "ITEM_GROUP";
        String element = "ELEMENT";
        String count = "COUNT";
        String itemAtt = "item";
        String valueAtt = "value";

        MultiplicityInterval occurrence11 = helper.createMultiplicityInterval(1, 1);
        MultiplicityInterval occurrence01 = helper.createMultiplicityInterval(0, 1);

        // ----------------------------------------------------------------------
        String cId = cimi.createNewId(); // creates next id
        String text = "SUBJID";
        String description = "Subject Identification";
        String conceptReference = ADLTerminologyServices.getConceptReference(cId);

        // ############ Constraint 1 #########################
        // Simple Constraint with a String type

        // ELEMENT[id2] occurrences matches {1} 	-- SUBJID
        CComplexObject c1 = helper.createComplexObjectConstraint(element, cId, occurrence11);

        // Creates the term definition
        //  ["id2"] = <
        //      text = <"SUBJID">
        //      description = <"De-identified Subject ID">
        //  >
        //
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        // ----------------------------------------------------------------------
        cId = cimi.createNewId();
        text = "Site of Primary (Event)";
        description = "Indicates the site of primary for the patient's cancer";
        conceptReference = ADLTerminologyServices.getConceptReference(cId);

        // ############ Constraint 2 #########################
        // Constraint referencing to a value set - encoded values
        //
        // ELEMENT[id3] occurrences matches {0..1} matches
        //
        CComplexObject c2 = helper.createComplexObjectConstraint(element, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        // Value Set and its entries
        String vsId = cimi.createNewValueSetId(); // creates first value set id
        String vsDef = "primary sites";
        String vsDesc = "primary sites description here";
        String vsConRef = ADLTerminologyServices.getConceptReference(vsId);
        //
        //  ["ac1"] = <
        //      text = <"primary sites">
        //      description = <"xxx">
        //  >
        //
        dbGapArch.addArchetypeTerm(vsId, null, vsDef, vsDesc, null, vsConRef);

        String pvId1 = cimi.createNewPermissibleValueId();
        String pv1Def = "COLN";
        String pv1Desc = "colonic (includes appendix)";
        String pv1ConRef = ADLTerminologyServices.getConceptReference(pvId1);
        //
        //        "at4"] = <
        //        text = <"COLN">
        //            description = <"Colonic (includes appendix)">
        //        >
        //
        dbGapArch.addArchetypeTerm(pvId1, null, pv1Def, pv1Desc, null, pv1ConRef);

        String pvId2 = cimi.createNewPermissibleValueId();
        String pv2Def = "RECT";
        String pv2Desc = "Rectal";
        String pv2ConRef = ADLTerminologyServices.getConceptReference(pvId2);
        //
        //        ["at5"] = <
        //        text = <"RECT">
        //            description = <"Rectal">
        //        >
        //
        dbGapArch.addArchetypeTerm(pvId2, null, pv2Def, pv2Desc, null, pv2ConRef);

        // Creates Value-Set entries
        //
        //  value_sets = <
        //      ["ac1"] = <
        //      id = <"ac1">
        //          members = <"at4", "at5">
        //          >
        //
        dbGapArch.updateValueSet(vsId, pvId1, pvId2);
        CTerminologyCode terminologyCode = CIMITypes.getTerminologyConstraint(vsId, null);

        if (terminologyCode != null)
            helper.addAttributeConstraints(c2, valueAtt, null, null, terminologyCode);

        // ----------------------------------------------------------------------
        cId = cimi.createNewId();
        text = "Age";
        description = "Age at the time the specimen was taken";
        conceptReference = ADLTerminologyServices.getConceptReference(cId);

        // ############ Constraint 3 #########################
        // Constraint with integer interval
        CComplexObject c3 = helper.createComplexObjectConstraint(element, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        cId = cimi.createNewId();
        text = "Age Interval";
        description = "Valid age Interval";
        conceptReference = ADLTerminologyServices.getConceptReference(cId);

        CComplexObject c31 = helper.createComplexObjectConstraint(count, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        helper.addAttributeConstraints(c3, valueAtt, null, null, c31);

        IntervalOfInteger intervalOfInteger = (IntervalOfInteger) CIMITypes.getIntervalFor(CIMIPrimitiveTypes.INTEGER);
        intervalOfInteger.setLower(33);
        intervalOfInteger.setUpper(90);

        CPrimitiveObject primitiveObject = CIMITypes.createPrimitiveTypeConstraints(CIMIPrimitiveTypes.INTEGER, intervalOfInteger, null);
        helper.addAttributeConstraints(c31, valueAtt, null, null, primitiveObject);

        // ----------------------------------------------------------------------
        //
        // Finally add all constraints to the main definition of the archetype
        //

        helper.addAttributeConstraints(dbGapArch.getDefinition(), itemAtt, null, null, c1, c2, c3);

        // ----------------------------------------------------------------------
        // Serialize the Archetype into ADL 2.0 text rendering
        String dbGapArchTxt = adlServices.serialize(dbGapArch);

        assertNotNull(dbGapArchTxt);
    }
 }