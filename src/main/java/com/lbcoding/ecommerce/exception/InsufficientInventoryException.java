package com.lbcoding.ecommerce.exception;

public class InsufficientInventoryException  extends Exception{
    private final int availableQuantity;

    public InsufficientInventoryException(String message, int availableQuantity){
        super(message);
        this.availableQuantity = availableQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
