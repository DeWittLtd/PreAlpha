package com.dewitt.command;

import com.dewitt.db.User;
import com.dewitt.db.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetNearestPlayersCmd extends Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login"); // login of players
        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.get(login); // get users by login

        // TODO magic of checking for all users and distance;

        return "not supported"; // TODO return JSON with this object
    }
}
