package com.dewitt.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * abstract class for all commands
 */
public abstract class Command implements Serializable{
    public abstract String execute(HttpServletRequest req,
                                   HttpServletResponse resp);
}
