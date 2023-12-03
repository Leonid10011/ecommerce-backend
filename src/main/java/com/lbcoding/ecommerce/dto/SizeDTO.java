package com.lbcoding.ecommerce.dto;

public class SizeDTO {
    private long size_id;
    private String name;

    public SizeDTO() {
    }

    public SizeDTO(long size_id, String name) {
        this.size_id = size_id;
        this.name = name;
    }

    public long getSize_id() {
        return size_id;
    }

    public void setSize_id(long size_id) {
        this.size_id = size_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
