package com.example.giay_story.model.dto;

import com.example.giay_story.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryDTO implements Validator {
    private String id;
    private String title;

    public Category toCategory() {
        return new Category()
                .setId(Long.parseLong(id))
                .setTitle(title);
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
