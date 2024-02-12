package com.mjc.school.helper;

public enum Operations {
    GET_ALL_NEWS(1, "Get all news."),
    GET_NEWS_BY_ID(2, "Get news by id."),
    CREATE_NEWS(3, "Create news."),
    UPDATE_NEWS(4, "Update news."),
    REMOVE_NEWS_BY_ID(5, "Remove news by id."),

    GET_ALL_AUTHORS(6, "Get all authors."),
    GET_AUTHOR_BY_ID(7, "Get author by id."),
    CREATE_AUTHOR(8, "Create author."),
    UPDATE_AUTHOR(9, "Update author."),
    REMOVE_AUTHOR_BY_ID(10, "Remove author by id."),

    GET_ALL_TAGS(11, "Get all tags."),
    GET_TAG_BY_ID(12, "Get tag by id."),
    CREATE_TAG(13, "Create tag."),
    UPDATE_TAG(14, "Update tag."),
    REMOVE_TAG_BY_ID(15, "Remove tag by id."),

    GET_ALL_COMMENTS(16, "Get all comments."),
    GET_COMMENT_BY_ID(17, "Get comment by id."),
    CREATE_COMMENT(18, "Create comment."),
    UPDATE_COMMENT(19, "Update comment."),
    REMOVE_COMMENT_BY_ID(20, "Remove comment by id."),


    GET_AUTHOR_BY_NEWS_ID(21, "Get author by news id."),
    GET_TAGS_BY_NEWS_ID(22, "Get tag by news id."),
    GET_COMMENTS_BY_NEWS_ID(23, "Get comment by news id."),
    GET_NEWS_BY_PARAMS(24, "Get news by params."),
    EXIT(0, "Exit.");

    public static final String OPERATION = "Operation: ";
    private final Integer operationNumber;
    private final String operation;

    Operations(int operationNumber, String operation) {
        this.operationNumber = operationNumber;
        this.operation = operation;
    }

    public String getOperation() {
        return OPERATION + operation;
    }

    public String getOperationWithNumber() {
        return operationNumber + ". " + operation;
    }

    public Integer getOperationNumber() {
        return operationNumber;
    }
}
