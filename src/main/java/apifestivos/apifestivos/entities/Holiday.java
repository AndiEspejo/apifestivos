package apifestivos.apifestivos.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "festivo")
public class Holiday {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

   @Column(name = "nombre", nullable = false, length = 100)
  private String name;

  @Column(name = "dia", nullable = false)
  private int day;

  @Column(name = "mes", nullable = false)
  private int month;

  @Column(name = "diaspascua", nullable = false)
  private int easterDays;

  @ManyToOne
  @JoinColumn(name = "idtipo", nullable = false)
  private Type type;

  private Date date;

  // Contructors
  public Holiday() {
  }

  public Holiday(Long id, String name, int day, int month, int easterDays, Type type) {
    this.id = id;
    this.name = name;
    this.day = day;
    this.month = month;
    this.easterDays = easterDays;
    this.type = type;
  }

  // Getters and Setters

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

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getEasterDays() {
    return easterDays;
  }

  public void setEasterDays(int easterDays) {
    this.easterDays = easterDays;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
