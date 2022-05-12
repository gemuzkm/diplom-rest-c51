package by.tms.diplomrestc51.validation;

import by.tms.diplomrestc51.exception.InvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdValidationTest {

    @Test
    void validateTrue() {
        assertTrue(IdValidation.validate(1L));
        assertTrue(IdValidation.validate(100L));
    }

    @Test
    void validateFalseNull() {
        Exception exception = assertThrows(InvalidException.class, () -> {
            IdValidation.validate(0);
        });

        String expectedMessage = "Invalid id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateFalseMinus() {
        Exception exception = assertThrows(InvalidException.class, () -> {
            IdValidation.validate(-1L);
        });

        String expectedMessage = "Invalid id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}