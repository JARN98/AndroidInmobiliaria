package com.example.viveinmobiliaria.Model;

public class Propiedad {
    private String title;
    private String description;
    private double price;
    private int rooms;
    private String address;
    private String zipcode;
    private String city;
    private String province;
    private String loc;
    private Owner ownerId;
    private String id;

    public Propiedad() {
    }

    public Propiedad(String title, String description, double price, int rooms, String address, String zipcode, String city, String province, String loc, Owner ownerId, String id) {
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
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
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

    public Owner getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Owner ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Propiedad propiedad = (Propiedad) o;

        if (Double.compare(propiedad.price, price) != 0) return false;
        if (rooms != propiedad.rooms) return false;
        if (title != null ? !title.equals(propiedad.title) : propiedad.title != null) return false;
        if (description != null ? !description.equals(propiedad.description) : propiedad.description != null)
            return false;
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
        return id != null ? id.equals(propiedad.id) : propiedad.id == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + rooms;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Propiedad{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", loc='" + loc + '\'' +
                ", ownerId=" + ownerId +
                ", id='" + id + '\'' +
                '}';
    }
}

class Owner {
    private String id;
    private String picture;
    private String name;
}
