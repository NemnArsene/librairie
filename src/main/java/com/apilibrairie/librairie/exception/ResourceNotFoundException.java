package com.apilibrairie.librairie.exception;

public class ResourceNotFoundException extends RuntimeException {
    // creation des variables pour personnaliser les erreurs
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName,
            Object fieldValue) {
        super(String.format("%s not found with %s :'%s'", resourceName, fieldName,
                fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // variable message pour personnaliser les messages d'erreurs
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // getters
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
