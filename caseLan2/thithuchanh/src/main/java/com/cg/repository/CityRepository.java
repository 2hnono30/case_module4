package com.cg.repository;

import com.cg.model.City;
import com.cg.model.dto.CityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    @Query("SELECT NEW com.cg.model.dto.CityDTO (" +
            "c.id, " +
            "c.cityName ," +
            "c.area," +
            "c.population," +
            "c.gdp," +
            "c.description," +
            "c.country)" +
            "FROM City c ")
    List<CityDTO> getAllCityDTO();

    @Query("select new com.cg.model.dto.CityDTO(" +
            "c.id," +
            "c.cityName," +
            "c.area," +
            "c.population," +
            "c.gdp," +
            "c.description," +
            "c.country)" +
            "FROM City c where c.id = ?1")
    Optional<CityDTO>getCityDTOById(Long id);
}
