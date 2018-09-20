package com.dewitt.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCmd extends Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return "error";
    }
}
