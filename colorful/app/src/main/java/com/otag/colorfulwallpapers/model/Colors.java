package com.otag.colorfulwallpapers.model;

public class Colors {

    private String hex;
    private String name;

    public Colors() {
    }

    public Colors(String hex, String name) {
        this.hex = hex;
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
