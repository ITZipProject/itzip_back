package darkoverload.itzip.feature.resume.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = ResumeValidatorCheck.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ResumeConditional {

    String message() default "this field is required";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
