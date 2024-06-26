package com.tech.denso.Models.WarrantyClaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarrantyClaim {

@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Data data;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}