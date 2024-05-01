package apifestivos.apifestivos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import apifestivos.apifestivos.entities.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>{
  
}
