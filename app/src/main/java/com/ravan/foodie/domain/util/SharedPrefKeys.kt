package com.ravan.foodie.domain.util

enum class SharedPrefKeys(
    val key: String,
) {
    // Single-account keys from before multi-account support. Only read once, to migrate them
    // into Accounts.
    LegacyUsername("USERNAME"),
    LegacyPassword("PASSWORD"),
    LegacyAccessToken("ACCESS_TOKEN"),
    LegacyRefreshToken("REFRESH_TOKEN"),

    /** Username the legacy keys were migrated to; local databases migrate their rows to it. */
    LegacyMigratedAccount("LEGACY_MIGRATED_ACCOUNT"),

    Accounts("ACCOUNTS"),
    ActiveAccount("ACTIVE_ACCOUNT"),
    NotificationsEnabled("NOTIFICATIONS_ENABLED"),
}