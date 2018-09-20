package com.dewitt.command;

import com.dewitt.db.User;
import com.dewitt.db.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateLocationCmd extends Command {

    // example of get request
    // http://localhost:8080/controller?command=updateloc&login=nananna&latitude=3232&longitude=343&bearing=23

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");

        float latitude = Float.parseFloat(req.getParameter("latitude"));
        float longitude = Float.parseFloat(req.getParameter("longitude"));
        int bearing = Integer.parseInt(req.getParameter("bearing"));

        UserDAO userDAO = UserDAO.getInstance();
        User user = userDAO.get(login);

        if (user == null){ // if there is no user
            user = new User(login);
            userDAO.create(user);
        }

        user.setLatitude(latitude);
        user.setLongitude(longitude);
        user.setBearing(bearing);
        userDAO.update(user);

        return "done";
    }

}
