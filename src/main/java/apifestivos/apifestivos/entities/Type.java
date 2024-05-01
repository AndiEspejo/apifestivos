package apifestivos.apifestivos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo")
public class Type {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tipo", nullable = false, length = 100)
  private String type;

  // Contructors
  public Type() {
  }

  public Type(long id, String tipo) {
    this.id = id;
    this.type = tipo;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipo() {
    return type;
  }

  public void setTipo(String type) {
    this.type = type;
  }
}
