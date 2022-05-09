package by.tms.diplomrestc51.validation;

import by.tms.diplomrestc51.exception.InvalidException;
import org.springframework.stereotype.Component;

public class IdValidation {

    public static boolean validate(long id) {
        if (id < 1) {
            throw new InvalidException("Invalid id");
        } else {
            return true;
        }
    }
}
