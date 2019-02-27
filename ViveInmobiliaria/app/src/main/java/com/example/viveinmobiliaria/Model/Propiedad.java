package com.example.viveinmobiliaria.Model;

import java.util.Arrays;
import java.util.List;

public class Propiedad {
    public String title;
    public String description;
    public Integer price;
    public Integer rooms;
    public String address;
    public String zipcode;
    public String city;
    public String province;
    public String loc;
    public OwnerId ownerId;
    public String createdAt;
    public String updatedAt;
    public Integer v;
    public String id;
    public String[] photos = null;
    public boolean isFav;

    public Propiedad(String title, String description, Integer price, Integer rooms, String address, String zipcode, String city, String province, String loc, OwnerId ownerId, String createdAt, String updatedAt, Integer v, String id, String[] photos, boolean isFav) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.v = v;
        this.id = id;
        this.photos = photos;
        this.isFav = isFav;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Propiedad propiedad = (Propiedad) o;

        if (isFav != propiedad.isFav) return false;
        if (title != null ? !title.equals(propiedad.title) : propiedad.title != null) return false;
        if (description != null ? !description.equals(propiedad.description) : propiedad.description != null)
            return false;
        if (price != null ? !price.equals(propiedad.price) : propiedad.price != null) return false;
        if (rooms != null ? !rooms.equals(propiedad.rooms) : propiedad.rooms != null) return false;
        if (address != null ? !address.equals(propiedad.address) : propiedad.address != null)
            return false;
        if (zipcode != null ? !zipcode.equals(propiedad.zipcode) : propiedad.zipcode != null)
            return false;
        if (city != null ? !city.equals(propiedad.city) : propiedad.city != null) return false;
        if (province != null ? !province.equals(propiedad.province) : propiedad.province != null)
            return false;
        if (loc != null ? !loc.equals(propiedad.loc) : propiedad.loc != null) return false;
        if (ownerId != null ? !ownerId.equals(propiedad.ownerId) : propiedad.ownerId != null)
            return false;
        if (createdAt != null ? !createdAt.equals(propiedad.createdAt) : propiedad.createdAt != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(propiedad.updatedAt) : propiedad.updatedAt != null)
            return false;
        if (v != null ? !v.equals(propiedad.v) : propiedad.v != null) return false;
        if (id != null ? !id.equals(propiedad.id) : propiedad.id != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(photos, propiedad.photos);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (v != null ? v.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photos);
        result = 31 * result + (isFav ? 1 : 0);
        return result;
    }
}

class OwnerId {
    private String id;
    private String picture;
    private String name;

    public OwnerId(String id, String picture, String name) {
        this.id = id;
        this.picture = picture;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
