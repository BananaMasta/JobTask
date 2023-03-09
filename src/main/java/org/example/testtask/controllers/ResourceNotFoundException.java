package org.example.testtask.controllers;

public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID = -9079454849611061074L;
    private String code;
    private String usCode1;
    private String usCode2;
    private String rejectValue;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message, String code, String rejectValue) {
        super(message);
        this.code = code;
        this.rejectValue = rejectValue;
    }

    public ResourceNotFoundException(String usCode1, String usCode2) {
        this.usCode1 = usCode1;
        this.usCode2 = usCode2;
    }

    public String getUsCode1() {
        return usCode1;
    }

    public void setUsCode1(String usCode1) {
        this.usCode1 = usCode1;
    }

    public String getUsCode2() {
        return usCode2;
    }

    public void setUsCode2(String usCode2) {
        this.usCode2 = usCode2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRejectValue() {
        return rejectValue;
    }

    public void setRejectValue(String rejectValue) {
        this.rejectValue = rejectValue;
    }
}
