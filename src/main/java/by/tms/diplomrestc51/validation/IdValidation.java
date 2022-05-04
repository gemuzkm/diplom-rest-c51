package by.tms.diplomrestc51.validation;

import by.tms.diplomrestc51.exception.InvalidException;
import org.springframework.stereotype.Component;

@Component
public class IdValidation {

    public void validate(long id) {
        if (id < 1) {
            throw new InvalidException("Invalid id");
        }
    }
}
