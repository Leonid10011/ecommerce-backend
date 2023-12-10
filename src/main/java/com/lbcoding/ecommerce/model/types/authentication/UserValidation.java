package com.lbcoding.ecommerce.model.types.authentication;

public class UserValidation {
    private boolean valid;
    private long id;

    public UserValidation(boolean valid, long id) {
        this.valid = valid;
        this.id = id;
    }

    public UserValidation(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
