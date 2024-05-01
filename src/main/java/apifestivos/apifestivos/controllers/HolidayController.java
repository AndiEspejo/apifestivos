package apifestivos.apifestivos.controllers;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apifestivos.apifestivos.dtos.HolidayDto;
import apifestivos.apifestivos.services.HolidayService;
import java.util.Calendar;

@RestController
@RequestMapping("/festivos")
public class HolidayController {
  private HolidayService holidayService;

  public HolidayController(HolidayService holidayService) {
    this.holidayService = holidayService;
  }

  @GetMapping("/verificar/{año}/{mes}/{dia}")
  public String holidayVerification(@PathVariable int año, @PathVariable int mes, @PathVariable int dia) {
    if (holidayService.isValidDate(String.valueOf(año) + "-" + String.valueOf(mes) + "-" + String.valueOf(dia))) {
      Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
      calendar.set(año, mes - 1, dia);
      Date date = calendar.getTime();
      return holidayService.isHoliday(date) ? "Es festivo" : "No es festivo";
    } else {
      return "La fecha no es valida";
    }
  }

  @GetMapping("/obtener/{año}")
  public List<HolidayDto> getHolidays(@PathVariable int año) {
    return holidayService.getHolidaysByYear(año);
  }
}
