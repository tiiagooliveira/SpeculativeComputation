package cguide.db.entities;

import cguide.db.beans.PatientBean;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA.
 * Patient: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class Patient {
    private String idpatient;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String homephone;
    private String birthdate;
    private String type;
    private String nutente;

    public Patient() {
    }

    public static Patient fromBean(PatientBean bean){
        Patient patient = new Patient();
        patient.idpatient =String.valueOf(bean.getIdpatient());
        patient.name = bean.getName();
        patient.lastname = bean.getLastname();
        patient.email = bean.getEmail();
        patient.phone = bean.getPhone();
        patient.address=bean.getAddress();
        patient.homephone = bean.getHomephone();
        patient.type = bean.getType();
        patient.nutente = bean.getNutente();
        patient.birthdate= bean.getBirthdate();
        return patient;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Patient fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Patient.class);
    }


    public String toJsonListElement(){
        return "{\"idpatient\":\"" + idpatient + "\",\"name\":\"" + name + "\",\"name\":\"" + name + "\",\"type\":\"" + type + "\"}";
    }

    // getters and setters

    public void setIdpatient(String idpatient){
        this.idpatient=idpatient;
    }
    public String getIdpatient(){
        return this.idpatient;
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

    public String getNutente() {
        return nutente;
    }

    public void setNutente(String nutente) {
        this.nutente = nutente;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
