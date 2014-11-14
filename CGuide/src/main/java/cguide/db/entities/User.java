package cguide.db.entities;

import cguide.db.beans.UserBean;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA.
 * User: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class User {
    private String iduser;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String homephone;
    private String birthdate;
    private String type;
    private String photo;
    private String password;

    public User() {
    }

    public static User fromBean(UserBean bean){
        User user = new User();
        user.iduser =String.valueOf(bean.getIduser());
        user.username = bean.getUsername();
        user.name = bean.getName();
        user.lastname = bean.getLastname();
        user.email = bean.getEmail();
        user.phone = bean.getPhone();
        user.address=bean.getAddress();
        user.homephone = bean.getHomephone();
        user.type = bean.getType();
        user.photo = bean.getPhoto();
        user.address = bean.getAddress();
        user.birthdate= bean.getBirthdate();
        return user;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static User fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }


    public String toJsonListElement(){
        return "{\"iduser\":\"" + iduser + "\",\"username\":\"" + username + "\",\"name\":\"" + name + "\",\"type\":\"" + type + "\"}";
    }

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIduser(String iduser){
        this.iduser=iduser;
    }
    public String getIduser(){
        return this.iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomePhone() {
        return homephone;
    }

    public void setHomePhone(String homephone) {
        this.homephone = homephone;
    }

    public String getType() {
        return type;
    }

    public void setTipo(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
