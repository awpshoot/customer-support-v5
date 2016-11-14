package com.wrox;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class JdbcUtil {
	private static Properties props;
	
	/** 
	 * 静态构造器 
	 */
	static{
		InputStream inputStream = new JdbcUtil().getClass().getClassLoader()
				.getResourceAsStream("com/wrox/commonConfig.properties");
		props = new Properties();
		try
		{
			props.load(inputStream);
			inputStream.close();
		} catch (IOException e){
			
		}
	}
	
	public static  Connection getConnectionForMySQL() {
	    String url = "";  				//连接url
	    String user = "";  				//用户对象
	    String password = "";           //数据库密码
	    String driver="";				//数据库驱动
		Connection con=null;
		try {
			url = props.getProperty("jdbc.mysql.url");
			user =props.getProperty("jdbc.mysql.user");
			password =props.getProperty("jdbc.mysql.password");
			driver =props.getProperty("jdbc.mysql.driver");
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			System.out.println("connSqlserver:"+e.toString());
			return null;
		}
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("connSqlserver:"+e.toString());
			return null;
		}
		return con;
	}
	
	/**
	 * 关闭数据库连接 并且释放资源
	 * @param rs
	 * @param statement
	 * @param con
	 */
    public static void closeConnection(ResultSet rs, Statement statement, Connection con) {
        try {
            if (rs != null) {
                rs.close();
                rs=null;
            }
        } catch (SQLException e) {
        	System.out.println("close error:"+e.toString()); 
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    statement=null;
                }
            } catch (Exception e) {
            	System.out.println("close error:"+e.toString()); 
            } finally {
                try {
                    if (con != null) {
                        con.close();
                        con=null;
                    }
                } catch (SQLException e) {
                	System.out.println("close error:"+e.toString()); 
                }
            }
        }
    }
    
    public static void main(String[] args){
    	Connection tempCon = getConnectionForMySQL();
    	System.out.println(tempCon);
    	try {
			tempCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
