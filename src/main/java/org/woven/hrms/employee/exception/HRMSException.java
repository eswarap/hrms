package org.woven.hrms.employee.exception;

import java.io.Serial;

public class HRMSException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public HRMSException(final String message) {
        super(message);
    }

    public HRMSException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HRMSException(final Throwable cause) {
        super(cause);
    }
}
