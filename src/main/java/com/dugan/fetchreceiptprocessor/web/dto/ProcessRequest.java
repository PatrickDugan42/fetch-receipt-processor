package com.dugan.fetchreceiptprocessor.web.dto;

import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Description;

import java.util.List;

public record ProcessRequest(
        @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "The retailer must match the following regexp, ^[\\w\\s\\-&]+$")
        @Size(max = 255, message = "Retailer name must be under 256 characters.")
        @NotNull
        @NotEmpty
        @Description("The name of the retailer or store the receipt is from.")
        String retailer,
        @NotNull
        @NotEmpty
        @Description("The date of the purchase printed on the receipt.")
        @Pattern(regexp = "^(?:\\d{4})-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$", message = "The purchaseDate must follow format of YYYY-mm-dd.")
        String purchaseDate,
        @NotNull
        @NotEmpty
        @Description("The time of the purchase printed on the receipt. 24-hour time expected.")
        @Pattern(regexp = "(?:[0-9]|[01][0-9]|2[0-3]):[0-5][0-9]$", message = "The purchaseTime must follow the format of H:mm.")
        String purchaseTime,
        @Size(min = 1)
        @Description("The purchased items.")
        List<ProcessItem> items,
        @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "The total cost must match the following regexp, ^\\d+\\.\\d{2}$")
        @NotNull
        @NotEmpty
        @Description("The total amount paid on the receipt.")
        String total) {
}
