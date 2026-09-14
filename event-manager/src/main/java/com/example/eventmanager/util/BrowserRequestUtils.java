package com.example.eventmanager.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Distinguishes a browser address-bar navigation (e.g. someone pasting
 * /api/users into the URL bar) from a genuine API call (curl, Postman, or the
 * JS test console's fetch() calls).
 *
 * Browsers send an Accept header that prioritizes "text/html". fetch() calls
 * made without an explicit Accept header (as the test console does) default
 * to "*&#47;*", and typical API clients send "*&#47;*" or "application/json" —
 * neither of which contains "text/html". This keeps the console's own calls
 * working as JSON while a human browsing to the endpoint directly gets
 * redirected to the HTML page instead of a raw JSON dump.
 */
public final class BrowserRequestUtils {

    private BrowserRequestUtils() {
    }

    public static boolean prefersHtml(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.contains("text/html");
    }
}