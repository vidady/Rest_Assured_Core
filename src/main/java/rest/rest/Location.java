package rest.rest;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Location implements Serializable{
	 private double lat;
	 private double lng;


	 // Getter Methods 

	 public double getLat() {
	  return lat;
	 }

	 public double getLng() {
	  return lng;
	 }

	 // Setter Methods 

	 public void setLat(double d) {
	  this.lat = d;
	 }

	 public void setLng(double d) {
	  this.lng = d;
	 }
	}
