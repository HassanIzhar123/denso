package com.tech.denso.Models.InitialWarrantyFragment;

public class InitialWarrantyModel {
    String companyName;
    String street;
    String city;

    public String getNewPart() {
        return newPart;
    }

    public void setNewPart(String newPart) {
        this.newPart = newPart;
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

    String newPart  ;
    String newSerialNumber;
    String newPartName ;
    String newPartInvoice ;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String saleOrder,originalUnitDate,failedUnitDate,manufacturer,unitModelNumber,unitSerialNumber,message;
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
