package com.tttm.Whear.App.utils.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandRequestDto {
    private String customerID;

    private String brandName;

    private String description;

    private String address;

    private String link;
}
