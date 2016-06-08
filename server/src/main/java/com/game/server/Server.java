package com.game.server;

import com.game.server.userservice.UserService;

/**
 * Created by Lee on 2016-06-01.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        new UserService(6112).start();
    }
}
