package com.dewitt;

import com.dewitt.command.Command;
import com.dewitt.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }


    private void process(HttpServletRequest req, HttpServletResponse resp) {
        String cmdName = req.getParameter("command"); // getting cmd name

        Command cmd = CommandContainer.get(cmdName);

        String result = cmd.execute(req, resp);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

// update my current location
// request for nearest players

// request for players data (for battle start)
// request fo HP
// sending damage to players

// db
// id, login, latitude, longitude, peering