package com.tech.denso.Models.InitialWarrantyFragment;

public class Item {

    private String name;
    private boolean isSelected = false;

    public Item(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        Item itemCompare = (Item) obj;
        if (itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}