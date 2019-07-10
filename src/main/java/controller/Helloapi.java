package controller;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import entity.Student;
import util.Stringutil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Helloapi extends HttpServlet {
    static {
        ObjectifyService.register(Student.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(new Gson().toJson(ofy().load().type(Student.class).list()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = Stringutil.convertInputStreamToString(req.getInputStream());
        Student student = new Gson().fromJson(content,Student.class);
        Key<Student> studentKey = ofy().save().entity(student).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print(new Gson().toJson(studentKey));
    }
}
