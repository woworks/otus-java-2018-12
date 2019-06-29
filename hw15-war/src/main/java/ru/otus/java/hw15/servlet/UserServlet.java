package ru.otus.java.hw15.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.java.hw15.base.DBService;
import ru.otus.java.hw15.datasets.AddressDataSet;
import ru.otus.java.hw15.datasets.PhoneDataSet;
import ru.otus.java.hw15.datasets.UserDataSet;
import ru.otus.java.hw15.freemarker.TemplateProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configurable
public class UserServlet extends HttpServlet {

    private static final String USER_INFO_ERROR = "/users?error=Incorrect%20User%20Info";
    private static final String TEXT_HTML = "text/html;charset=utf-8";
    private static final String USERS_PAGE_TEMPLATE = "users.html";
    @Autowired
    private DBService dbService;
    @Autowired
    private TemplateProcessor templateProcessor;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");

        Map<String, Object> pageVariables = getUserDataSetMap(id);

        checkForError(req, pageVariables);

        resp.setContentType(TEXT_HTML);
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> getUserDataSetMap(String id) {
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
        return pageVariables;
    }

    private void checkForError(HttpServletRequest req, Map<String, Object> pageVariables) {
        String errorMessage = req.getParameter("error");
        if (errorMessage != null && errorMessage.trim().length() > 0){
            pageVariables.put("errorMessage", errorMessage);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            saveUserDataSet(req);
        } catch (ServletDataException e) {
            resp.sendRedirect(req.getContextPath() + USER_INFO_ERROR);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users");

    }

    private void saveUserDataSet(HttpServletRequest req) throws ServletDataException {
        String name = req.getParameter("name");
        String phone1 = req.getParameter("phone1");
        String phone2 = req.getParameter("phone1");
        String address = req.getParameter("address");

        Integer age = ServletHelper.getIntFromRequest("age", req);

        if ((name == null || name.trim().length() == 0)) {
            throw new ServletDataException("Bad data: name");
        }

        UserDataSet userDataSet = new UserDataSet();
        userDataSet.setName(name);
        userDataSet.setAge(age);
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
    }

}
