package com.example.lael.holamundo;

public class GymPostActivity {
    //Atributos
    private String name;
    private String description;
    private String address;
    private String phone;
    private String open;
    private String close;
    private String logo;


    public GymPostActivity(String name, String description, String address, String phone, String open, String close, String logo) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.open = open;
        this.close = close;
        this.logo = logo;
    }

    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){return description;}

    public void setDescription(String description){
        this.description = description;
    }

    public String getAddress(){return  address;}

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpen() {return open;}

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {return close;}

    public void setClose(String close) {
        this.close = close;
    }

    public String getLogo() {return logo;}

    public void setLogo(String logo) {
        this.logo = logo;
    }
}