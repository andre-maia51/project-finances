package com.andre.project_finances.infra.excepctions;

public class BusinessRuleConflict extends RuntimeException{
    public BusinessRuleConflict(String message) {
        super(message);
    }
}
