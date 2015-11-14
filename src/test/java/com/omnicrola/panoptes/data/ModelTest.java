package com.omnicrola.panoptes.data;

import java.lang.reflect.Field;

import static com.omnicrola.test.util.TestUtil.assertFieldHasAnnotation;
import static com.omnicrola.test.util.TestUtil.getFieldByName;
import static org.junit.Assert.assertEquals;

/**
 * Created by Eric on 10/5/2015.
 */
public abstract class ModelTest {

    private Class<?> modelUnderTest;

    protected void setClassUnderTest(Class<?> classUnderTest) {
        this.modelUnderTest = classUnderTest;
    }

    protected  void checkRequiredFieldIsPresent(String fieldName, Class<?> expectedClass) {
        Field field = checkFieldIsPresent(fieldName, expectedClass);
        assertFieldHasAnnotation(Required.class, field);
    }

    protected   Field checkFieldIsPresent(String fieldName, Class<?> expectedFieldType) {
        Field actualField = getFieldByName(fieldName, this.modelUnderTest);
        assertEquals(expectedFieldType, actualField.getType());
        return actualField;
    }

}
