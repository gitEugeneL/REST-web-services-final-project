package pl.university.applicationserver.auction.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import pl.university.applicationserver.auction.exception.ApiRequestException;

@Component
public class DtoValidator {
    public void validate(BindingResult bindingResult) {
        String validateErrors = ValidateUtils.validate(bindingResult);
        if (validateErrors != null)
            throw new ApiRequestException(validateErrors);
    }
}
