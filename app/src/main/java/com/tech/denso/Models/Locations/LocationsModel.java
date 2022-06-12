
package com.tech.denso.Models.Locations;

import java.util.List;

public class LocationsModel {

    //@SerializedName("status")

    private String status;
    //@SerializedName("message")

    private String message;
    //@SerializedName("data")

    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
