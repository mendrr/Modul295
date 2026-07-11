package ch.wiss.f1teammanager.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ein Team der Formel 1 mit seinen Fahrern.
 * Ein Team hat mehrere Fahrer (@OneToMany) und wird von JPA auf die
 * Tabelle "teams" abgebildet.
 */
@Entity
@Table(name ="teams")

public class Team {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String name;
 private String base;

 //mappedby zeigt auf das feld team in Driver (foreignkey team_ID
 //CASCADE: speichert und löscht auch die fahrer wenn team gelöscht wird
 //ORPHAN: nimmt auch den fahreraus der liste und DB
 @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Driver> drivers = new ArrayList<>();

 public void addDriver(Driver driver) {
  drivers.add(driver);
  driver.setTeam(this);
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getBase() {
  return base;
 }

 public void setBase(String base) {
  this.base = base;
 }

 public List<Driver> getDrivers() {
  return drivers;
 }

 public void setDrivers(List<Driver> drivers) {
  this.drivers = drivers;
 }
}
