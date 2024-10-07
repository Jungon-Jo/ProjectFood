package Food;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FoodAdmin {
	// foodDTO 객체를 하나만 생성하고 동일 객체를 반복 사용한다.
	FoodDTO foodDTO = new FoodDTO();
	// 연결을 위한 sql 객체 생성
	Connection conn = null;
	// 입력을 위한 sql 객체 생성
	PreparedStatement pstmt = null;
	// 출력을 위한 sql 객체 생성
	Statement stmt = null;
	// select 명령의 검색 결과값을 저장하기위한 참조변수
	ResultSet rs = null;
	FoodAdmin() {
		init();
		menu();
	}
	private void menu() {
		Scanner in = new Scanner(System.in);
		while(true) {
			menuList();
			System.out.println("원하는 메뉴의 번호를 입력하세요.");
			System.out.println("입력 번호 >> ");
			int selNum = in.nextInt();
			in.nextLine();
			
			
			if(selNum == 1) {
				list();
			} else if(selNum == 2) {
				insert();
			} else if(selNum == 3) {
//				delete();
			} else if(selNum == 4) {
//				edit();
			} else {
				System.out.println("유효하지 않은 값입니다. 다시 입력해주세요.");
			}
		}
		
	}
	private void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void menuList() {
		System.out.println("=== Food List ===");
		System.out.println("1. Food List");
		System.out.println("2. Food Add");
		System.out.println("3. Food Delete");
		System.out.println("4. Food Edit");
	}
	
	private void list() {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			String sql = "select * from foodlist";
			stmt = conn.createStatement();
			// executeQuery : ResultSet 을 리턴해준다.
			rs = stmt.executeQuery(sql);
//			if(rs.next()) {
//				rs.beforeFirst();
				System.out.println("===========================================");
				while(rs.next()) {
					String foodName = rs.getString("name");
					String foodColor = rs.getString("color");
					String foodMainIngredient = rs.getString("mainingredient");
					String foodOriginCountry = rs.getString("origincountry");
					int foodAvgCost = rs.getInt("avgcost");
					System.out.println("음식명 = " + foodName);
					System.out.println("음식색 = " + foodColor);
					System.out.println("음식메인재료 = " + foodMainIngredient);
					System.out.println("음식원조국 = " + foodOriginCountry);
					System.out.println("음식평균단가 = " + foodAvgCost);
					System.out.println("===========================================");
				}
//			} else {
//				System.out.println("저장된 음식 정보가 없습니다.");
//			}
			
		} catch (SQLException e) {
			System.out.println("SQL 오류 = " + e.getMessage());
		} finally {
			if(stmt != null) {
				try {
					if(rs != null) {
						rs.close();
					}
					if(stmt != null) {
						stmt.close();
					}
					if(conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					
				}
			}
		}
	}
	
	// 한번 실행할 때마다 DB에 저장되며, 실행을 초기화 시켜도 DB에는 저장되어 있다.
	private void insert() {
		Scanner in = new Scanner(System.in);
		System.out.println("음식 이름을 입력하시오.");
		foodDTO.setFoodName(in.nextLine());
		System.out.println("음식 색을 입력하시오.");
		foodDTO.setFoodColor(in.nextLine());
		System.out.println("음식 메인 재료를 입력하시오.");
		foodDTO.setFoodMainIngredient(in.nextLine());
		System.out.println("음식 발생지를 입력하시오.");
		foodDTO.setFoodOriginCountry(in.nextLine());
		System.out.println("음식 평균 단가를 입력하시오.");
		foodDTO.setFoodAvgCost(in.nextInt());

		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			System.out.println("커넥션 자원 획득 성공");
			String sql = "insert into foodlist values(?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, foodDTO.getFoodName());
			pstmt.setString(2, foodDTO.getFoodColor());
			pstmt.setString(3, foodDTO.getFoodMainIngredient());
			pstmt.setString(4, foodDTO.getFoodOriginCountry());
			pstmt.setInt(5, foodDTO.getFoodAvgCost());

			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			} else {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close(); // 자원 반납
				} catch(Exception e2) {
					
				}
			}
		}
	}
}
