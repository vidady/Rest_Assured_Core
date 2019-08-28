package rest.rest;

import java.io.Serializable;

public class WheaterPost implements Serializable {
 Location LocationObject;
 private float accuracy;
 private String name;
 private String phone_number;
 private String address;
 private String[] types;
 private String website;
 private String language;


 // Getter Methods 
 
 public String[] getTypes ()
 {
     return types;
 }

 public void setTypes (String[] types)
 {
     this.types = types;
 }
 

 public Location getLocation() {
  return LocationObject;
 }

 public float getAccuracy() {
  return accuracy;
 }

 public String getName() {
  return name;
 }

 public String getPhone_number() {
  return phone_number;
 }

 public String getAddress() {
  return address;
 }

 public String getWebsite() {
  return website;
 }

 public String getLanguage() {
  return language;
 }

 // Setter Methods 

 public void setLocation(Location locationObject) {
  this.LocationObject = locationObject;
 }

 public void setAccuracy(float accuracy) {
  this.accuracy = accuracy;
 }

 public void setName(String name) {
  this.name = name;
 }

 public void setPhone_number(String phone_number) {
  this.phone_number = phone_number;
 }

 public void setAddress(String address) {
  this.address = address;
 }

 public void setWebsite(String website) {
  this.website = website;
 }

 public void setLanguage(String language) {
  this.language = language;
 }
}
