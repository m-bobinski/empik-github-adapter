package com.empik.githubadapter.constants;

public final class SqlConstants {

    public static final String UPDATE_QUERY = "UPDATE REQUEST_COUNTERS c SET c.REQUEST_COUNT = c.REQUEST_COUNT + 1 WHERE c.LOGIN = :login";

    private SqlConstants() {
    }
}
