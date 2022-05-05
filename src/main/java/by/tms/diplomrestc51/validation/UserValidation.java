package by.tms.diplomrestc51.validation;

import by.tms.diplomrestc51.entity.user.User;
import by.tms.diplomrestc51.exception.InvalidException;

public class UserValidation {
    public static boolean isActive(User user) {
        if (user.getStatus().equals("ACTIVE")) {
            return true;
        } else {
            throw new InvalidException("User is not active");
        }
    }
}
