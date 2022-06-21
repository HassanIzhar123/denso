
package com.tech.denso.Models.WarrantyClaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("cityStateZip")
    @Expose
    private String cityStateZip;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("homeOwnerName")
    @Expose
    private String homeOwnerName;
    @SerializedName("homeOwnerStreet")
    @Expose
    private String homeOwnerStreet;
    @SerializedName("homeOwnerCityStateZip")
    @Expose
    private String homeOwnerCityStateZip;
    @SerializedName("homeOwnerPhoneNumber")
    @Expose
    private String homeOwnerPhoneNumber;
    @SerializedName("salesOrderOrInvoiceNumber")
    @Expose
    private String salesOrderOrInvoiceNumber;
    @SerializedName("originalUnitInstallDate")
    @Expose
    private String originalUnitInstallDate;
    @SerializedName("failedDate")
    @Expose
    private String failedDate;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("unitModelNumber")
    @Expose
    private String unitModelNumber;
    @SerializedName("unitSerialNumber")
    @Expose
    private String unitSerialNumber;
    @SerializedName("unitPartNumber")
    @Expose
    private String unitPartNumber;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("failureReason")
    @Expose
    private String failureReason;
    @SerializedName("newPartNumber")
    @Expose
    private String newPartNumber;
    @SerializedName("newPartName")
    @Expose
    private String newPartName;
    @SerializedName("newSerialNumber")
    @Expose
    private String newSerialNumber;
    @SerializedName("newPartInvoiceNumber")
    @Expose
    private String newPartInvoiceNumber;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("__v")
    @Expose
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityStateZip() {
        return cityStateZip;
    }

    public void setCityStateZip(String cityStateZip) {
        this.cityStateZip = cityStateZip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeOwnerName() {
        return homeOwnerName;
    }

    public void setHomeOwnerName(String homeOwnerName) {
        this.homeOwnerName = homeOwnerName;
    }

    public String getHomeOwnerStreet() {
        return homeOwnerStreet;
    }

    public void setHomeOwnerStreet(String homeOwnerStreet) {
        this.homeOwnerStreet = homeOwnerStreet;
    }

    public String getHomeOwnerCityStateZip() {
        return homeOwnerCityStateZip;
    }

    public void setHomeOwnerCityStateZip(String homeOwnerCityStateZip) {
        this.homeOwnerCityStateZip = homeOwnerCityStateZip;
    }

    public String getHomeOwnerPhoneNumber() {
        return homeOwnerPhoneNumber;
    }

    public void setHomeOwnerPhoneNumber(String homeOwnerPhoneNumber) {
        this.homeOwnerPhoneNumber = homeOwnerPhoneNumber;
    }

    public String getSalesOrderOrInvoiceNumber() {
        return salesOrderOrInvoiceNumber;
    }

    public void setSalesOrderOrInvoiceNumber(String salesOrderOrInvoiceNumber) {
        this.salesOrderOrInvoiceNumber = salesOrderOrInvoiceNumber;
    }

    public String getOriginalUnitInstallDate() {
        return originalUnitInstallDate;
    }

    public void setOriginalUnitInstallDate(String originalUnitInstallDate) {
        this.originalUnitInstallDate = originalUnitInstallDate;
    }

    public String getFailedDate() {
        return failedDate;
    }

    public void setFailedDate(String failedDate) {
        this.failedDate = failedDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUnitModelNumber() {
        return unitModelNumber;
    }

    public void setUnitModelNumber(String unitModelNumber) {
        this.unitModelNumber = unitModelNumber;
    }

    public String getUnitSerialNumber() {
        return unitSerialNumber;
    }

    public void setUnitSerialNumber(String unitSerialNumber) {
        this.unitSerialNumber = unitSerialNumber;
    }

    public String getUnitPartNumber() {
        return unitPartNumber;
    }

    public void setUnitPartNumber(String unitPartNumber) {
        this.unitPartNumber = unitPartNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getNewPartNumber() {
        return newPartNumber;
    }

    public void setNewPartNumber(String newPartNumber) {
        this.newPartNumber = newPartNumber;
    }

    public String getNewPartName() {
        return newPartName;
    }

    public void setNewPartName(String newPartName) {
        this.newPartName = newPartName;
    }

    public String getNewSerialNumber() {
        return newSerialNumber;
    }

    public void setNewSerialNumber(String newSerialNumber) {
        this.newSerialNumber = newSerialNumber;
    }

    public String getNewPartInvoiceNumber() {
        return newPartInvoiceNumber;
    }

    public void setNewPartInvoiceNumber(String newPartInvoiceNumber) {
        this.newPartInvoiceNumber = newPartInvoiceNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}