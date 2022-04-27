package by.tms.diplomrestc51.exception;

import javax.naming.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {
    public JWTAuthenticationException(String msg) {
        super(msg);
    }

    public JWTAuthenticationException() {
        super();
    }
}
