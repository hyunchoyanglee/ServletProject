package Pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateOkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String idx=req.getParameter("idx");
		String id=req.getParameter("id");
		String pwd=req.getParameter("pwd");
		String email=req.getParameter("email");
		String phone=req.getParameter("phone");
		String region=req.getParameter("region");
		System.out.println(idx +" " + id);
		PreparedStatement pstmt = null;
		Connection con = null;
		int n=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://13.125.251.30:3307/db01";
			con = DriverManager.getConnection(url, "lion", "1234");
			String sql = "update user set pwd=?,email=?,phone=?, region=? where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setInt(4, Integer.parseInt(region));
			pstmt.setInt(5, Integer.parseInt(idx));

			n = pstmt.executeUpdate();

			if(n>0){
				resp.sendRedirect("list.do");
			}else{
				PrintWriter pw = resp.getWriter();
				pw.println("<html><head></head>");
				pw.println("<body>�떎�뙣</body>");
				pw.println("</heal>");
				pw.close();
			}

		}catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}
}