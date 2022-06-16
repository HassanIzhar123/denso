package com.tech.denso.Models.Locations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private Long phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("openingSaturday")
    @Expose
    private String openingSaturday;
    @SerializedName("tillThursday")
    @Expose
    private String tillThursday;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("postCode")
    @Expose
    private Integer postCode;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("branchName")
    @Expose
    private String branchName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
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

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

}
//public class Datum {
//
//    @SerializedName("_id")
//    @Expose
//    private String id;
//    @SerializedName("create_date")
//    @Expose
//    private String createDate;
//    @SerializedName("cityName")
//    @Expose
//    private String cityName;
//    @SerializedName("email")
//    @Expose
//    private String email;
//    @SerializedName("phoneNumber")
//    @Expose
//    private Long phoneNumber;
//    @SerializedName("address")
//    @Expose
//    private String address;
//    @SerializedName("openingSaturday")
//    @Expose
//    private String openingSaturday;
//    @SerializedName("tillThursday")
//    @Expose
//    private String tillThursday;
//    @SerializedName("latitude")
//    @Expose
//    private Double latitude;
//    @SerializedName("longitude")
//    @Expose
//    private Double longitude;
//    @SerializedName("postCode")
//    @Expose
//    private Integer postCode;
//    @SerializedName("__v")
//    @Expose
//    private Integer v;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Long getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(Long phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getOpeningSaturday() {
//        return openingSaturday;
//    }
//
//    public void setOpeningSaturday(String openingSaturday) {
//        this.openingSaturday = openingSaturday;
//    }
//
//    public String getTillThursday() {
//        return tillThursday;
//    }
//
//    public void setTillThursday(String tillThursday) {
//        this.tillThursday = tillThursday;
//    }
//
//    public Double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(Double latitude) {
//        this.latitude = latitude;
//    }
//
//    public Double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }
//
//    public Integer getPostCode() {
//        return postCode;
//    }
//
//    public void setPostCode(Integer postCode) {
//        this.postCode = postCode;
//    }
//
//    public Integer getV() {
//        return v;
//    }
//
//    public void setV(Integer v) {
//        this.v = v;
//    }
//
//}