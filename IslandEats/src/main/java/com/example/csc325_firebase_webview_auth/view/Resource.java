package com.example.csc325_firebase_webview_auth.view;

/**
 *  Resource class is a representation of the location data stored within the firebase database
 * */
public class Resource {
    //Member variables
    private String name;
    private String address;
    private String city;
    private String zipcode;
    private String state;
    private String url;
    private String hours;

    /**
     * Parameterized Constructor
     */
    public Resource(String name, String address, String city, String zipcode, String state, String url, String hours) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
        this.url = url;
        this.hours = hours;
    }

    /**
     * Returns name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Returns state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns hours
     */
    public String getHours() {
        return hours;
    }

    /**
     * Sets hours
     */
    public void setHours(String hours) {
        this.hours = hours;
    }
}
