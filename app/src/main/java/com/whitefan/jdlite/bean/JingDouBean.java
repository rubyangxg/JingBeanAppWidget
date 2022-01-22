package com.whitefan.jdlite.bean;

import java.util.ArrayList;

public class JingDouBean {

    private String code;
    private ArrayList<DetailListDTO> detailList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DetailListDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(ArrayList<DetailListDTO> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListDTO {
        private String date;
        private int amount = 0;
        private String eventMassage;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getEventMassage() {
            return eventMassage;
        }

        public void setEventMassage(String eventMassage) {
            this.eventMassage = eventMassage;
        }
    }
}
