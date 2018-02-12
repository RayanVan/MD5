package dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;

import sun.security.provider.MD5;
import model.User;

public class UserDao {
	public static final String url = "jdbc:mysql://localhost:3306/user";  
    public static final String driver ="com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password1 = "root"; 
    
    public Connection conn = null;  
    public Statement st = null;  

    
    public void register(User user1) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException{
    	String psw_md5=toMD5(user1.getPassword());
    	System.out.println("加密后的密码："+psw_md5);
    	Class.forName(driver);//指定连接类型  
        conn = DriverManager.getConnection(url, user, password1);//获取连接  
        st = conn.createStatement();
        String sql="insert into user(name,password) values ('"+user1.getName()+"','"+psw_md5+"')";
        int rs=st.executeUpdate(sql);//准备执行语句 
    	if(rs<0){
    		System.out.println("注册失败");
    	}
        conn.close();
    	st.close();
    }
    //登陆判断
    public boolean isLogin(String old,String md5) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	boolean flag=false;
    	String md=toMD5(old);
    	if(md.equals(md5)){
    		flag=true;
    	}
    	return flag;
    }
    //根据用户名获取用户
    public List<User> getUsers(String name) throws ClassNotFoundException, SQLException{
    	Class.forName(driver);//指定连接类型  
        conn = DriverManager.getConnection(url, user, password1);//获取连接  
        st = conn.createStatement();
        String sql="select * from user where name ='"+name+"'";
    	ResultSet rs=st.executeQuery(sql);
    	List<User> users=new ArrayList<User>();
    	User user=new User();
    	 //4.处理数据库的返回结果(使用ResultSet类)
    	while(rs.next()){
    		user=getUser(Integer.valueOf(rs.getString("id")));
    		users.add(user);
    	}
    	return users;
    }
    //根据id获取用户
    public User getUser(int id) throws ClassNotFoundException, SQLException{
     	Class.forName(driver);//指定连接类型  
        conn = DriverManager.getConnection(url, user, password1);//获取连接  
        st = conn.createStatement();
        String sql="select * from user where id ="+id;
        ResultSet rs=st.executeQuery(sql);
        User user=new User();
        while(rs.next()){
        	  user.setId(id);
              user.setName(rs.getString("name"));
              user.setPassword(rs.getString("password"));
        }
        return user;
    }
    //md5加密
    public String toMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	MessageDigest md=MessageDigest.getInstance("MD5");
    	byte[] strByteArray=str.getBytes("utf-8");
    	byte[] mdByteArray=md.digest(strByteArray);
    	StringBuffer hexValue=new StringBuffer();
    	for(int i=0;i<mdByteArray.length;i++){
    		int val=((int)mdByteArray[i])&0xff;
    		if(val<16){
    			hexValue.append("0");
    		}
    		hexValue.append(Integer.toHexString(val));
    	}
    	return hexValue.toString();
    }

}
