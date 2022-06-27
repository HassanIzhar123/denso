package com.tech.denso.Models.InitialWarrantyFragment;

import java.io.Serializable;

public class InitialWarrantyModel implements Serializable {

    public String getUnitPartNumber() {
        return unitPartNumber;
    }

    public void setUnitPartNumber(String unitPartNumber) {
        this.unitPartNumber = unitPartNumber;
    }

    String unitPartNumber;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    String vehicleName,vehicleModel;

    String companyName;
    String street;
    String city;

    public String getNewPartNumber() {
        return newPartNumber;
    }

    public void setNewPartNumber(String newPartNumber) {
        this.newPartNumber = newPartNumber;
    }

    public String getNewSerialNumber() {
        return newSerialNumber;
    }

    public void setNewSerialNumber(String newSerialNumber) {
        this.newSerialNumber = newSerialNumber;
    }

    public String getNewPartName() {
        return newPartName;
    }

    public void setNewPartName(String newPartName) {
        this.newPartName = newPartName;
    }

    public String getNewPartInvoice() {
        return newPartInvoice;
    }

    public void setNewPartInvoice(String newPartInvoice) {
        this.newPartInvoice = newPartInvoice;
    }

    String newPartNumber;
    String newSerialNumber;
    String newPartName;
    String newPartInvoice;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    String comments;

    public String getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(String saleOrder) {
        this.saleOrder = saleOrder;
    }

    public String getOriginalUnitDate() {
        return originalUnitDate;
    }

    public void setOriginalUnitDate(String originalUnitDate) {
        this.originalUnitDate = originalUnitDate;
    }

    public String getFailedUnitDate() {
        return failedUnitDate;
    }

    public void setFailedUnitDate(String failedUnitDate) {
        this.failedUnitDate = failedUnitDate;
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

    public String getFailureReasonMessage() {
        return failureReasonMessage;
    }

    public void setFailureReasonMessage(String failureReasonMessage) {
        this.failureReasonMessage = failureReasonMessage;
    }

    String saleOrder, originalUnitDate, failedUnitDate, manufacturer, unitModelNumber, unitSerialNumber, failureReasonMessage;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getHomeOwnerCity() {
        return homeOwnerCity;
    }

    public void setHomeOwnerCity(String homeOwnerCity) {
        this.homeOwnerCity = homeOwnerCity;
    }

    public String getHomeOwnerPhoneNumber() {
        return homeOwnerPhoneNumber;
    }

    public void setHomeOwnerPhoneNumber(String homeOwnerPhoneNumber) {
        this.homeOwnerPhoneNumber = homeOwnerPhoneNumber;
    }

    String phoneNumber;
    String homeOwnerName;
    String homeOwnerStreet;
    String homeOwnerCity;
    String homeOwnerPhoneNumber;
}
