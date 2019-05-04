package ru.otus.java.hw12.servlet;

import ru.otus.java.hw12.base.DBService;
import ru.otus.java.hw12.datasets.AddressDataSet;
import ru.otus.java.hw12.datasets.PhoneDataSet;
import ru.otus.java.hw12.datasets.UserDataSet;
import ru.otus.java.hw12.freemarker.TemplateProcessor;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserServlet extends HttpServlet {

    public static final String USER_INFO_ERROR = "/users?error=Incorrect%20User%20Info";
    private static final String TEXT_HTML = "text/html;charset=utf-8";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private final DBService dbService;
    private final TemplateProcessor templateProcessor;

    public UserServlet(DBService dbService) throws IOException {
        this.dbService = dbService;
        this.templateProcessor = new TemplateProcessor();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String errorMessage = req.getParameter("error");

        Map<String, Object> pageVariables = new HashMap<>();

        List<UserDataSet> users = new ArrayList<>();
        if (id == null || id.length() == 0){
            users = dbService.readAll();
        } else {
                try {
                    UserDataSet user = dbService.read(Long.valueOf(id));
                    if (user != null){
                        users.add(user);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Wrong id" + id);
                }
        }

        id = id == null? "" : id;
        pageVariables.put("id", id);
        pageVariables.put("users", users);
        if (errorMessage != null && errorMessage.trim().length() > 0){
            pageVariables.put("errorMessage", errorMessage);
        }

        resp.setContentType(TEXT_HTML);
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String phone1 = req.getParameter("phone1");
        String phone2 = req.getParameter("phone1");
        String address = req.getParameter("address");

        int ageNum;

        if ((name == null || name.trim().length() == 0 ||
                age == null || name.trim().length() == 0)) {
            resp.sendRedirect(req.getContextPath() + USER_INFO_ERROR);
            return;
        }
        try {
            ageNum = Integer.valueOf(age);
        }  catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + USER_INFO_ERROR);
            return;
        }


        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        userDataSet.setAge(ageNum);
        if (address != null && address.trim().length() > 0) {
            userDataSet.setAddress(new AddressDataSet(address));
        }
        List<PhoneDataSet> phones = new ArrayList<>();

        if (phone1 != null && phone1.trim().length() > 0) {
            phones.add(new PhoneDataSet(phone1));
        }
        if (phone2 != null && phone2.trim().length() > 0) {
            phones.add(new PhoneDataSet(phone2));
        }
        userDataSet.setPhones(phones);

        dbService.save(userDataSet);


        resp.sendRedirect(req.getContextPath() + "/users");

    }

}
