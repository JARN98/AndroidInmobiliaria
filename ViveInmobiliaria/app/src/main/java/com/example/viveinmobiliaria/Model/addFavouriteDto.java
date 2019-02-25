package com.example.viveinmobiliaria.Model;

import java.util.Arrays;

public class addFavouriteDto {
    private String role;
    private String[] favs;
    private String[] keywords;
    private String _id;
    private String picture;
    private String name;
    private String email;
    private String password;

    public addFavouriteDto(String role, String[] favs, String[] keywords, String _id, String picture, String name, String email, String password) {
        this.role = role;
        this.favs = favs;
        this.keywords = keywords;
        this._id = _id;
        this.picture = picture;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public addFavouriteDto() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getFavs() {
        return favs;
    }

    public void setFavs(String[] favs) {
        this.favs = favs;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        addFavouriteDto that = (addFavouriteDto) o;

        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(favs, that.favs)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(keywords, that.keywords)) return false;
        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (picture != null ? !picture.equals(that.picture) : that.picture != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(favs);
        result = 31 * result + Arrays.hashCode(keywords);
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "addFavouriteDto{" +
                "role='" + role + '\'' +
                ", favs=" + Arrays.toString(favs) +
                ", keywords=" + Arrays.toString(keywords) +
                ", _id='" + _id + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
