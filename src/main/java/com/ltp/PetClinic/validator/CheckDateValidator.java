package com.ltp.PetClinic.validator;

import java.util.Date;

import jakarta.validation.ConstraintValidator;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Date> {
    public boolean isValid(Date value, jakarta.validation.ConstraintValidatorContext context) {

        long todayMilliSeconds = new Date().getTime();
        long thatDayMilliSeconds = value.getTime();

        return thatDayMilliSeconds <= todayMilliSeconds;

    };
}
