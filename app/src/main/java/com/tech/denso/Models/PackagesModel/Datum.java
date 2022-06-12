package com.tech.denso.Models.PackagesModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

@SerializedName("_id")
@Expose
private String id;
@SerializedName("create_date")
@Expose
private String createDate;
@SerializedName("serviceName")
@Expose
private String serviceName;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("price")
@Expose
private Integer price;
@SerializedName("offer")
@Expose
private String offer;
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

public String getServiceName() {
return serviceName;
}

public void setServiceName(String serviceName) {
this.serviceName = serviceName;
}

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

public String getOffer() {
return offer;
}

public void setOffer(String offer) {
this.offer = offer;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

}