package com.company.jmixpm.validation;


import com.vaadin.shared.ui.dnd.criteria.Payload;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidDatesProjectValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDatesProject {

    String message() default "Start date cannot be later than end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}


