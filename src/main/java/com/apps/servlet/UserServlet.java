package com.apps.servlet;

import com.apps.domain.Users;
import com.apps.service.UserService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int requestUserId = Integer.parseInt(req.getParameter("id"));
        Users users = userService.getUserById(requestUserId);
        req.setAttribute("user", users);
        getServletContext().getRequestDispatcher("WEB-INF/jsp/singleUser.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_login = req.getParameter("user_login");
        String user_password = req.getParameter("user_password");
        String email = req.getParameter("email");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        boolean result = userService.createUser(user_login, user_password, email, first_name, last_name);
        if (result) {
            getServletContext().getRequestDispatcher("WEB-INF/jsp/successfully.jsp").forward(req, resp);
        } getServletContext().getRequestDispatcher("WEB-INF/jsp/unsuccessfully.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String user_login = req.getParameter("user_login");
        String user_password = req.getParameter("user_password");
        String email = req.getParameter("email");
        String first_name= req.getParameter("first_name");
        String last_name= req.getParameter("last_name");
        boolean result = userService.updateUser(id, user_login, user_password, email, first_name, last_name);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
