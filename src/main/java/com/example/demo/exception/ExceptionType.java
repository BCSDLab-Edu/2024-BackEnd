package com.example.demo.exception;

public enum ExceptionType {

    Member_NotNullName(400, "'name' 값이 Null이 될 수 없습니다."),
    Member_NotNullEmail(400, "'email' 값이 Null이 될 수 없습니다."),
    Member_NotNullPassWord(400, "'password' 값이 Null이 될 수 없습니다."),
    Member_OverLapEmail(409, "'email' 값은 중복 될 수 없습니다."),
    Member_CantDelete(400, "유저가 작성한 게시물이 존재하기 때문에 삭제 할 수 없습니다."),
    Member_IsNull(400, "유저가 존재하지 않습니다."),
    Board_NotNullName(400, "'name' 값이 Null이 될 수 없습니다."),
    Board_CantDelete(400, "게시판에 작성된 게시물이 존재하기 때문에 삭제 할 수 없습니다."),
    Board_IsNull(400, "게시판이 존재하지 않습니다."),
    Article_NotNullAuthorId(400, "'authorId' 값이 Null이 될 수 없습니다."),
    Article_NotNullBoardId(400, "'boardId' 값이 Null이 될 수 없습니다."),
    Article_NotNullTitle(400, "'title' 값이 Null이 될 수 없습니다."),
    Article_NotNullDescription(400, "'description' 값이 Null이 될 수 없습니다."),
    ;

    private final int httpstate;
    private final String message;

    ExceptionType(int httpstate, String message) {
        this.httpstate = httpstate;
        this.message = message;
    }

    public int getHttpstate() {
        return httpstate;
    }

    public String getMessage() {
        return message;
    }

}
