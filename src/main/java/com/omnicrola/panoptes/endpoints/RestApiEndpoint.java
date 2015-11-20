package com.omnicrola.panoptes.endpoints;

import com.omnicrola.panoptes.data.Required;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by omnic on 11/14/2015.
 */
public abstract class RestApiEndpoint {

    protected static boolean modelHasRequiredFields(Object modelObject) {
        List<Field> requiredFields = Arrays.asList(modelObject.getClass().getDeclaredFields())
                .stream()
                .filter(f -> f.getAnnotation(Required.class) != null)
                .collect(Collectors.toList());


        boolean anyFieldIsMissing = requiredFields
                .stream()
                .filter(field -> doesNotHaveValue(modelObject, field))
                .findAny()
                .isPresent();

        if (anyFieldIsMissing) {
            return false;
        }
        return true;
    }

    private static boolean doesNotHaveValue(Object modelObject, Field f) {
        try {
            f.setAccessible(true);
            Object value = f.get(modelObject);
            if (value == null) {
                return true;
            } else if (value.equals("")) {
                return true;
            } else if (value.equals(0)) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
