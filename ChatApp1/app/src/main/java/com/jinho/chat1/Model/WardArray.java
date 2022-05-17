package com.jinho.chat1.Model;

import java.util.ArrayList;

public class WardArray {
    ArrayList<Ward> wards;
    public WardArray(ArrayList<Ward> wards) {
        this.wards = wards;
    }
    public Ward getWard(int index) {
        return wards.get(index);
    }
    public ArrayList<Ward> getWardArray() {
        return wards;
    }
}
