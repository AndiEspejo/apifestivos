package apifestivos.apifestivos.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HolidayDto {
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date date;

  private String holiday;

  // Contructors
  public HolidayDto() {
  }

  public HolidayDto(String holiday, Date date) {
    this.date = date;
    this.holiday = holiday;
  }

  // Getters and Setters
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getHoliday() {
    return holiday;
  }

  public void setHoliday(String holiday) {
    this.holiday = holiday;
  }
}
