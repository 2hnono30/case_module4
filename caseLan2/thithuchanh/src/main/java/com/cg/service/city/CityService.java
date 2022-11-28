package com.cg.service.city;

import com.cg.model.City;
import com.cg.model.dto.CityDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface CityService extends IGeneralService<City> {
    List<CityDTO> getAllCityDTO();
    Optional<CityDTO> getCityDTOById(Long id);
    void deleteById(Long id);
}
