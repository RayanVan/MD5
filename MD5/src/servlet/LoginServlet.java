package servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.UserDao;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		UserDao userdao=new UserDao();
		String name=req.getParameter("name");
		String pass=req.getParameter("password");
		List<User> users=new ArrayList<User>();
		try {
			users=userdao.getUsers(name);
			for(int i=0;i<users.size();i++){
				String md5=users.get(i).getPassword();
				if(!userdao.isLogin(pass, md5)){
					System.out.println("登陆失败");
					req.getRequestDispatcher("/register.jsp").forward(req, resp);
				}
				else{
					System.out.println("登陆成功");
					req.getRequestDispatcher("/success.jsp").forward(req, resp);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
