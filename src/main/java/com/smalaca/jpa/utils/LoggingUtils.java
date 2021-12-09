package com.smalaca.jpa.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggingUtils {
    public static void nextContext() {
        System.out.println("-----------------------------");
        System.out.println("-------- NEW CONTEXT --------");
        System.out.println("-----------------------------");
    }
}
