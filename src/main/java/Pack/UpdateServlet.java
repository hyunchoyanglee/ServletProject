package Pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. parameter로 전송된 id얻기.
		//String id=req.getParameter("id");
		String idx=req.getParameter("idx");
		// 2. id에 해당하는 정보를 db에서 조회해서 출력.
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<html>");
		pw.println("<head></head>");
		pw.println("<body>");

		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs=null;
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<style> "
				+ "* {"
				+ "   margin: 0;"
				+ "   padding: 0;"
				+ "   box-sizing: border-box;"
				+ "   /* font-family: \"Noto Sans KR\", sans-serif; */"
				+ "}"
				+ "h1 {color: #6667AB;"
				+ "	margin-bottom:10px;"
				+ "}"
				+ "input {width: 50%;"
				+ "   height: 50px;"
				+ "   border-radius: 30px;"
				+ "   padding: 0px 20px;"
				+ "   border: 1px solid lightgray;"
				+ "   outline: none;"
				+ "	  margin-bottom:3px;"
				+ "}"
				+ ".wrap {"
				+ "width: 100%;"
				+ "   height: 100%;"
				+ "   display: flex;"
				+ "   align-items: center;"
				+ "   justify-content: center;"
				+ "   background: rgba(0, 0, 0, 0);"
				+ "}"
				+ ".divtitle{"
				+ "margin:8px;"
				+ "}"
				+ ".login {"
				+ "   width: 500px;"
				+ "   height: 90%;"
				+ "   background: white;"
				+ "   border-radius: 20px;"
				+ "   display: flex;"
				+ "   justify-content: center;"
				+ "   align-items: center;"
				+ "   flex-direction: column;"
				+ "   margin-top: 30px;"
				+ "}"
				+ ".saveBtn{"
				+ "  background-color: #4CAF50;"
				+ "  border: none;"
				+ "  color: white;"
				+ "  padding: 10px 10px;"
				+ "  text-align: center;"
				+ "  text-decoration: none;"
				+ "  display: inline-block;"
				+ "  font-size: 16px;"
				+ "  margin-top:20px;"
				+ "  transition-duration: 0.4s;"
				+ "  cursor: pointer;"
				+ "    background-color: white; "
				+ "  color: black; "
				+ "  border: 2px solid #008CBA;"
				+ "  }"
				+ ".saveBtn:hover{"
				+ "  background-color: #008CBA;"
				+ "  color: white;"
				+ "}"
				+ ".select {"
				+ "	  width: 100%;"
				+ "	  text-align: center;"
				+ "}"
				+ "#region {"
				+ "	  width: 50%;"
				+ "	  height: 45px;"
				+ "	  border-radius: 30px;"
				+ "	  border: 1px solid lightgray;"
				+ "	  padding: 10px 20px 10px 20px;"
				+ "}"
				+ ".regiontitle{"
				+ "text-align:center"
				+ "}"
				+ "h6 {"
				+ "color: red;"
				+ "margin-bottom: 5px;"
				+ "}"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				);

		try{
			// 2. 전송된 값을 db에 저장.
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://13.125.251.30:3307/db01";
			con = DriverManager.getConnection(url, "lion", "1234");

			/*String sql = "select * from user where idx=?";*/
			String sql = "select u.idx, u.id, u.pwd, u.email, u.phone, u.sysdate, r.regionNm as region from user u"
					+ " inner join region r on u.region = r.regionPk"
					+ " where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(idx));

			//sql구문 실행하기
			rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");
			String idx2 = rs.getString("idx");
			String pwd = rs.getString("pwd");
			String email=rs.getString("email");
			String phone=rs.getString("phone");
			String region=rs.getString("region");
			System.out.println(id);


			pw.println("<div class=\"wrap\">");
			pw.println("<form method='post' action='updateok.do'>");
			pw.println("<div class=\"login\">");
			pw.println("<h1 class=\"title\">회원정보 수정</h1>");
			pw.println("<input type='hidden' name='idx' value='" + idx2 + "'/>");
			pw.println("<input type='hidden' name='id' value='" + id + "'/>");
			pw.println("<div class=\"divtitle\">아이디</div>"
					+ "<input type='text' name='id' value='" + id + "' disabled='disabled'/>");
			pw.println("<div class=\"divtitle\">비밀번호</div>"
					+ "<input type='password' name='pwd' value='" + pwd + "'/>");
			pw.println("<div class=\"divtitle\">Email</div>"
					+ "<input type='text' name='email' value='" + email + "'/>");
			pw.println("<div class=\"divtitle\">Phone</div>"
					+ "<input type='text' name='phone' value='" + phone + "'/><br/>"
					+ "<div class=\"divtitle\">"+"현재지역 : "+region+"</div>"
					+ "<h6>"+"지역을 선택한 후 저장해주세요."+"</h6>"
					+ "<div class=\"select\">"
					+ "	<select name=\"region\" id=\"region\">"
					+ "		<option disabled selected=\"selected\" value=\"0\">"+"선택해주세요"+"</option>"
					+ "		<option value=\"1\">Seoul</option>"
					+ "		<option value=\"2\">Busan</option>"
					+ " 	<option value=\"3\">Daegu</option>"
					+ "		<option value=\"4\">Ulsan</option>"
					+ "		<option value=\"5\">Gwangju</option>"
					+ "		<option value=\"6\">Daejeon</option>"
					+ "		<option value=\"7\">Jeju</option>"
					+ "	</select>"
					+ "</div>");
			pw.println("<input type='submit' value='저장' class=\"saveBtn\"/><br/>");
			pw.println("</div>");
			pw.println("</form>");
			pw.println("</div>");

		}catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}finally{
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}

		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}