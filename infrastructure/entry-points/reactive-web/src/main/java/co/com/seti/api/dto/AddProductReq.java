package co.com.seti.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AddProductReq(
        @NotBlank
        String name,
        int stock) {
}

