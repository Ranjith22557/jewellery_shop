package com.rtech.jewellery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SalesUpdateDTO (

    @NotNull(message = "Sales Id must not be null")
    Long id,

    @NotNull(message = "Pending amount must not null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Pending amount must be zero or positive")
    BigDecimal pendingAmount
){}
