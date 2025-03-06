package com.dugan.fetchreceiptprocessor.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ProcessItem (
        @Pattern(regexp = "^[\\w\\s\\-]+$", message = "The item short description must match the following regex, ^[\\w\\s\\-]+$")
        @NotNull
        @NotEmpty
        String shortDescription,
        @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "The item price must match the following regex, ^\\d+\\.\\d{2}$")
        @NotNull
        @NotEmpty
        BigDecimal price)
{}
