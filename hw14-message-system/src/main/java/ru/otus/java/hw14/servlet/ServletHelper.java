package ru.otus.java.hw14.servlet;

import javax.servlet.http.HttpServletRequest;

class ServletHelper {
    private ServletHelper() {}

    static Integer getIntFromRequest(String name, HttpServletRequest req) throws ServletDataException {
        try {
            return Integer.valueOf(req.getParameter(name));
        }  catch (NumberFormatException e) {
            throw new ServletDataException("Bad format data");
        }
    }

}
