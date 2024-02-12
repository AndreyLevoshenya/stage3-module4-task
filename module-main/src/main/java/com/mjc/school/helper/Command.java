package com.mjc.school.helper;

import java.util.Map;

public record Command(int operation, Map<String, String> param, String body) {
    public static Command COMMAND_NOT_FOUND = new Command(-1, null, null);
    public static Command GET_ALL_NEWS_COMMAND = new Command(1, null, null);
    public static Command GET_ALL_AUTHORS_COMMAND = new Command(6, null, null);
    public static Command GET_ALL_TAGS_COMMAND = new Command(11, null, null);
    public static Command GET_ALL_COMMENTS_COMMAND = new Command(16, null, null);

}
