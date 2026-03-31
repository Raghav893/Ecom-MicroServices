package com.raghav.cartservice.common.security;

import java.util.UUID;

public record AuthenticatedUser(UUID userId, String username) {
}
