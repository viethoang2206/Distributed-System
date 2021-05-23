package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {
    private boolean makeQueryToDB(String username, String password) {
        String databaseUrl = "jdbc:mysql://localhost:5042/distributed?user=" + "user1" + "&password=" + "123456789";
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(databaseUrl);
            System.out.println("Connected to database");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM login WHERE username= ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet res = st.executeQuery();
            if (res.next()) 
                return true;
            else 
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Run POST");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        if (makeQueryToDB(username, password)) {
        	 resp.sendRedirect(req.getContextPath() + "/Calculator.jsp");
        } else {
        	resp.setContentType("text/html");
        	PrintWriter out = resp.getWriter();
        	out.println("<h1>" + "Login Failed" + "</h1>");
        }
    }
}