package apifestivos.apifestivos.interfaces;

import java.util.Date;
import java.util.List;

import apifestivos.apifestivos.dtos.HolidayDto;

public interface IHolidayService {

  public List<HolidayDto> getHolidaysByYear(int year);

  public boolean isHoliday(Date date);

  public boolean isValidDate(String stringDate);
}
