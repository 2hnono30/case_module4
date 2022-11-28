package com.cg.model;

import com.cg.model.dto.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name")
    private String nameCountry;

    @OneToMany(mappedBy = "country")
    private Set<City> cities;

    public CountryDTO toCountryDTO() {
        return new CountryDTO()
                .setId(id.toString())
                .setNameCountry(nameCountry);
    }

}

