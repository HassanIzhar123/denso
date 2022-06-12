
package com.tech.denso.Models.Locations;



public class Datum {

    private String id;

    private String createDate;
    //@SerializedName("cityName")

    private String cityName;
    //@SerializedName("email")

    private String email;
    //@SerializedName("phoneNumber")

    private Integer phoneNumber;
    //@SerializedName("address")

    private String address;
    //@SerializedName("openingSaturday")

    private String openingSaturday;
    //@SerializedName("tillThursday")

    private String tillThursday;
    //@SerializedName("latitude")

    private Double latitude;
    //@SerializedName("longitude")

    private Double longitude;
    //@SerializedName("__v")

    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningSaturday() {
        return openingSaturday;
    }

    public void setOpeningSaturday(String openingSaturday) {
        this.openingSaturday = openingSaturday;
    }

    public String getTillThursday() {
        return tillThursday;
    }

    public void setTillThursday(String tillThursday) {
        this.tillThursday = tillThursday;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
