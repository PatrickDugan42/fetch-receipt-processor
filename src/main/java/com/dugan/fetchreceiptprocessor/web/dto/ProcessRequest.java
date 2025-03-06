package com.dugan.fetchreceiptprocessor.web.dto;




import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record ProcessRequest(
        @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "The retailer must match the following regexp, ^[\\w\\s\\-&]+$")
        @Size(max = 255, message = "Retailer name must be under 256 characters.")
        @NotNull
        @NotEmpty
        String retailer,
        @NotNull
        @NotEmpty
        String purchaseDate,
        @NotNull
        @NotEmpty
        String purchaseTime,
        @Size(min = 1)
        List<ProcessItem> items,
        @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "The retailer must match the following regexp, ^\\d+\\.\\d{2}$")
        @NotNull
        @NotEmpty
        BigDecimal total) {
}
