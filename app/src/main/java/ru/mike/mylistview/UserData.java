package ru.mike.mylistview;

import java.io.Serializable;

public class UserData implements Serializable {
    private String fio;
    private String mail;
    private String birth;
    private String imageUrl;

    public UserData(String fio, String mail, String birth, String imageUrl) {
        this.fio = fio;
        this.mail = mail;
        this.birth = birth;
        this.imageUrl = imageUrl;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirthDate(String birth) {
        this.birth = birth;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
