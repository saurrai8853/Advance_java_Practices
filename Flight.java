package com;

//Flight.java
public class Flight {
 private int id;
 private String airline;
 private String source;
 private String destination;
 private double ticketPrice;

 // Constructor
 public Flight(int id, String airline, String source, String destination, double ticketPrice) {
     this.id = id;
     this.airline = airline;
     this.source = source;
     this.destination = destination;
     this.ticketPrice = ticketPrice;
 }

 // Getters and Setters
 public int getId() {
     return id;
 }

 public void setId(int id) {
     this.id = id;
 }

 public String getAirline() {
     return airline;
 }

 public void setAirline(String airline) {
     this.airline = airline;
 }

 public String getSource() {
     return source;
 }

 public void setSource(String source) {
     this.source = source;
 }

 public String getDestination() {
     return destination;
 }

 public void setDestination(String destination) {
     this.destination = destination;
 }

 public double getTicketPrice() {
     return ticketPrice;
 }

 public void setTicketPrice(double ticketPrice) {
     this.ticketPrice = ticketPrice;
 }

 // Override toString (optional, for debugging purposes)
 @Override
 public String toString() {
     return "Flight [id=" + id + ", airline=" + airline + ", source=" + source
             + ", destination=" + destination + ", ticketPrice=" + ticketPrice + "]";
 }
}
