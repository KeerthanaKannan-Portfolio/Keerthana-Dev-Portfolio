package com.banking.exceptions;

import java.time.LocalDate;

/**
 * Thrown when withdrawal is attempted before fixed deposit maturity date.
 */
public class MaturityDateException extends RuntimeException {

    private final LocalDate maturityDate;

    public MaturityDateException(LocalDate maturityDate) {
        super(String.format(
            "Withdrawal not allowed. Account matures on: %s", maturityDate));
        this.maturityDate = maturityDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }
}