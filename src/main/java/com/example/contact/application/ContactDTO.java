package com.example.contact.application;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record ContactDTO(
        UUID uuid,
        String name,
        @Nullable String email,
        @Nullable String phone,
        @Nullable String note) {
}
