package darkoverload.itzip.feature.resume.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

@Slf4j
public class ResumeValidatorCheck implements ConstraintValidator<ResumeConditional, Collection<?>> {


    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if(value== null || value.isEmpty()) {

            return true;
        }

        for(Object obj : value) {
            Method[] methods = obj.getClass().getMethods();

            for(Method method : methods) {
                if(method.getName().startsWith("get")) {
                    try {
                        if(method.getName().endsWith("Id")) continue;

                        Object field = method.invoke(obj);

                        if(field == null)  {
                            log.info("not found method field {}", method.getName());
                            context.disableDefaultConstraintViolation();
                            context.buildConstraintViolationWithTemplate("이력서 미입력한 내용이 있습니다.")
                                    .addConstraintViolation();
                            return false;
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

        return true;
    }

}
