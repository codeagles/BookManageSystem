package dao.impl;
import java.sql.*;

import vo.User;
import dao.UserDAO;
import dao.dbc.DBConnection;
public class UserDAOImpl implements UserDAO {
	private  Connection conn = null;
    private PreparedStatement pstmt=null;
    private ResultSet rs=null;
    boolean flag = false;
    DBConnection db=new DBConnection();

	/** 
     * @功能 用户登录（预编译方式—查询）
     * @参数 User
     * @返回值 boolean型值 
     */
	public boolean findLogin(User user) throws Exception {	
		try {
			conn=db.getConnection();
			String sql = "SELECT uname,limit FROM Users WHERE userid=? AND upwd=?" ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, user.getUserid()) ;
			pstmt.setString(2, user.getPwd()) ;
			rs = pstmt.executeQuery() ;
			if(rs.next()){
				flag = true ;
				user.setLimit(rs.getInt("limit"));
				user.setName(rs.getString("uname"));
				//测试
//				System.out.print(user.getUserid());
//			    System.out.print(user.getName());
//				System.out.print(user.getPwd());
			}
			 rs.close();
			 pstmt.close();
		} catch (Exception e) {	e.printStackTrace();			}
		finally {
			db.closed();
		}
		return flag;
	}

	@Override
	public boolean updatePwd(User user) throws Exception {
		try {
			conn=db.getConnection();
			String sql="update users set upwd=? where userid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getPwd());
			pstmt.setInt(2, user.getUserid());
			int count=pstmt.executeUpdate();
			if(count>0){
				flag=true;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
