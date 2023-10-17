package com.lbcoding.ecommerce.service.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public class DTOValidator {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> Set<String> validateDTO(T dto) {
        return validator.validate(dto)
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toSet());
    }
}
