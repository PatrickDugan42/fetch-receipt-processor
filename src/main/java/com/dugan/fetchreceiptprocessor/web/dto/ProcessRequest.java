package com.dugan.fetchreceiptprocessor.web.dto;




import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Description;

import java.math.BigDecimal;
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
        String purchaseDate,
        @NotNull
        @NotEmpty
        @Description("The time of the purchase printed on the receipt. 24-hour time expected.")
        String purchaseTime,
        @Size(min = 1)
        @Description("The purchased items.")
        List<ProcessItem> items,
        @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "The retailer must match the following regexp, ^\\d+\\.\\d{2}$")
        @NotNull
        @NotEmpty
        @Description("The total amount paid on the receipt.")
        String total) {
}
