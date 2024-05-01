package apifestivos.apifestivos.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import apifestivos.apifestivos.dtos.HolidayDto;
import apifestivos.apifestivos.entities.Holiday;
import apifestivos.apifestivos.interfaces.IHolidayService;
import apifestivos.apifestivos.repositories.HolidayRepository;

@Service
public class HolidayService implements IHolidayService {
  private HolidayRepository holidayRepository;

  public HolidayService(HolidayRepository holidayRepository) {
    this.holidayRepository = holidayRepository;
  }

  private Date getEasterSunday(int year) {
    int month, day, A, B, C, D;

    A = year % 19;
    B = year % 4;
    C = year % 7;
    D = (19 * A + 24) % 30;

    int days = D + (2 * B + 4 * C + 6 * D + 5) % 7;

    if (days > 9) {
      // Abril
      day = days - 9;
      month = 4;
    } else {
      // Marzo
      day = days + 22;
      month = 3;
    }

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(year, month - 1, day);
    return calendar.getTime();
  }

  private Date getNextMonday(Date date) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(date);

    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      calendar.add(Calendar.DATE, 1);
    }

    return calendar.getTime();
  }

  private Date agregateDays(Date date, int days) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(date);
    calendar.add(Calendar.DATE, days);
    return calendar.getTime();
  }

  private List<Holiday> normalizeHolidays(List<Holiday> holidays, int year) {
    if (holidays != null) {
      Date easterSunday = getEasterSunday(year);
      int i = 0;

      for (Holiday holiday : holidays) {

        // Caso 1. Festivo inamovible
        if (holiday.getType().getId() == 1) {
          Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
          calendar.set(year, holiday.getMonth() - 1, holiday.getDay());
          holiday.setDate(calendar.getTime());
        } else if (holiday.getType().getId() == 2) {
          // Caso 2. Festivo movible al siguiente lunes
          Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
          calendar.set(year, holiday.getMonth() - 1, holiday.getDay());
          holiday.setDate(getNextMonday(calendar.getTime()));
        } else if (holiday.getType().getId() == 3) {
          // Caso 3. Festivos que dependen de la fecha de Pascua
          holiday.setDate(agregateDays(easterSunday, holiday.getEasterDays()));
        } else if (holiday.getType().getId() == 4) {
          // Caso 4. Festivos que dependen de la fecha de Pascua y se mueven al siguiente
          // lunes
          holiday.setDate(getNextMonday(agregateDays(easterSunday, holiday.getEasterDays())));
        }

        holidays.set(i, holiday);
        i++;
      }
    }
    return holidays;
  }

  private boolean fechasIguales(Date fecha1, Date fecha2) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String fecha1String = dateFormat.format(fecha1);
    String fecha2String = dateFormat.format(fecha2);
    return fecha1String.equals(fecha2String);
  }

  private boolean isHolidayValidation(List<Holiday> holidays, Date date) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.setTime(date);
    if (holidays != null) {
      holidays = normalizeHolidays(holidays, calendar.get(Calendar.YEAR));

      for (Holiday holiday : holidays) {
        if (fechasIguales(holiday.getDate(), date))
          return true;
      }
    }
    return false;

  }

  @Override
  public boolean isHoliday(Date date) {
    List<Holiday> holidays = holidayRepository.findAll();
    return isHolidayValidation(holidays, date);
  }

  @Override
  public boolean isValidDate(String stringDate) {
    try {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      dateFormat.setLenient(false);
      dateFormat.parse(stringDate);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public List<HolidayDto> getHolidaysByYear(int year) {
    List<Holiday> normalizedHolidays = normalizeHolidays(holidayRepository.findAll(), year);

    List<HolidayDto> dates = new java.util.ArrayList<HolidayDto>();

    for (Holiday holiday : normalizedHolidays) {
      dates.add(new HolidayDto(holiday.getName(), holiday.getDate()));
    }

    return dates;
  }

}
