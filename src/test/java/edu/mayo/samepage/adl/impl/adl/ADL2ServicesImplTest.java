package edu.mayo.samepage.adl.impl.adl;

import edu.mayo.samepage.adl.IF.ADLServices;
import edu.mayo.samepage.adl.services.ADL2ServicesImpl;
import edu.mayo.samepage.adl.services.CIMIPrimitiveTypes;
import edu.mayo.samepage.adl.services.CIMIRMMetaData;
import edu.mayo.samepage.adl.services.CIMITypes;
import junit.framework.TestCase;
import org.junit.Test;
import org.openehr.jaxb.am.CComplexObject;
import org.openehr.jaxb.am.CPrimitiveObject;
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

        ADLArchetype dbGapArch = adlServices.createArchetype("dbGapTestArchetype", "This is a test", cimi, helper);
        assertNotNull(dbGapArch);

        String item_group = "ITEM_GROUP";
        String element = "ELEMENT";
        String count = "COUNT";
        String itemAtt = "item";
        String valueAtt = "value";


        MultiplicityInterval occurrence11 = helper.createMultiplicityInterval(1, 1);
        MultiplicityInterval occurrence01 = helper.createMultiplicityInterval(0, 1);

        String cId = cimi.createNewId();
        String text = "SUBJID";
        String description = "Subject Identification";
        String conceptReference = "http://snomed.info/123456-" + cId;

        CComplexObject c1 = helper.createComplexObjectConstraint(element, cId, occurrence11);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        cId = cimi.createNewId();
        text = "Site of Primary (Event)";
        description = "Indicates the site of primary for the patient's cancer";
        conceptReference = "http://snomed.info/123456-" + cId;

        CComplexObject c2 = helper.createComplexObjectConstraint(element, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        cId = cimi.createNewId();
        text = "Age";
        description = "Age at the time the specimen was taken";
        conceptReference = "http://snomed.info/123456-" + cId;

        CComplexObject c3 = helper.createComplexObjectConstraint(element, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        cId = cimi.createNewId();
        text = "Age Interval";
        description = "Valid age Interval";
        conceptReference = "http://snomed.info/123456-" + cId;

        CComplexObject c31 = helper.createComplexObjectConstraint(count, cId, occurrence01);
        dbGapArch.addArchetypeTerm(cId, null, text, description, null, conceptReference);

        helper.addAttributeConstraints(c3, valueAtt, null, null, c31);

        IntervalOfInteger intervalOfInteger = (IntervalOfInteger) CIMITypes.getIntervalFor(CIMIPrimitiveTypes.INTEGER);
        intervalOfInteger.setLower(33);
        intervalOfInteger.setUpper(90);

        CPrimitiveObject primitiveObject = CIMITypes.createPrimitiveTypeConstraints(CIMIPrimitiveTypes.INTEGER, intervalOfInteger, null);
        helper.addAttributeConstraints(c31, valueAtt, null, null, primitiveObject);

        helper.addAttributeConstraints(dbGapArch.getDefinition(), itemAtt, null, null, c1, c2, c3);

        String dbGapArchTxt = adlServices.serialize(dbGapArch);
        assertNotNull(dbGapArchTxt);
    }

 }