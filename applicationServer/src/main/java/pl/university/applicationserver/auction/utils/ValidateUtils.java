package pl.university.applicationserver.auction.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.StringJoiner;

public class ValidateUtils {
    public static String validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringJoiner sj = new StringJoiner(", ");
            for (FieldError error : bindingResult.getFieldErrors())
                sj.add(error.getField() + ": " + error.getDefaultMessage());
            return sj.toString();
        }
        return null;
    }
}
