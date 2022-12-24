package com.example.demo.Utils;

import java.lang.reflect.Field;

public class FieldInjector {
    public static void injectFiled(Object target, String fieldName, Object injectedObject) {
        Boolean isPrivate = false;
        try {
            Field injectedField = target.getClass().getDeclaredField(fieldName);
            if (!injectedField.isAccessible()) {
                injectedField.setAccessible(true);
                isPrivate = true;
            }
            injectedField.set(target, injectedObject);
            if (isPrivate) {
                injectedField.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
