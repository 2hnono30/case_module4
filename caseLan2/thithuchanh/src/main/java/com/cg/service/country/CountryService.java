package com.cg.service.country;

import com.cg.model.Country;
import com.cg.model.dto.CountryDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface CountryService extends IGeneralService<Country> {
    List<CountryDTO> getAllCountryDTO();

}
