package com.oracle;

import com.persistence.*;
import com.subprogram.SubProgram;
import java.sql.*;
import com.view.*;

public class Main {
	public static void main(String args[]) throws SQLException {

		//TheatersDAO.insert("태수투극장","경북 포항시 북구 중앙상가길 16(대흥동)");
		//MoviesDAO.insert("테슬라","마이클 알메레이다","에단 호크","드라마","니콜라 테슬라는 교류 전류 전송 시스템으로에디슨과의 경쟁에서 승리를 거\n머쥔다. 하지만 작은 승리로 만족할 수 없었던 테슬라는 세상의 패러다임을 바\n꿀 위대한 발명에 착수한다. 그의 발명은 다름아닌 빛, 에너지 정보를 전 세계\n에 무선으로 전송하는 혁신적인 기술.\n막대한 연구비가 필요한 그는 최고의 자본가인J. P. 모건의 도움을 구하게 되\n고 콜로라도의 연구소에서 하늘로 번개를 쏘아 올리는 연구를 시작하게 되는데…","2020-10-28","114","102","19","naver");
		//AdminsDAO.insert("테수투관리자","123","테수투극장");
		//GuestsDAO.insert("TEST","TESTPW","테수트이름","323","남","01012333223");
		//AccountsDAO.insert("TEST","TESTPW","토스","11-22222222-1112","10000");
		//ReviewsDAO.insert("10", "TEST", "테슬라", "2020-11-13", "재미가있다.","4.5");
        
        //ScreensDAO.insert("1", "태수투극장", "2D", "프리미엄");
        //SeatsDAO.insert("A1", "태수투극장", "1");
        //TimeTablesDAO.insert("7", "테슬라", "태수투극장", "1", "2020-11-14 17:00");
        //TicketsDAO.insert("6A5", "1", "태수투극장", "A1", "TEST", "7","테슬라");
        
        //ReviewsDAO.update("TEST","테슬라", "너무재밌다.", "5.0");
		//TheatersDAO.update("태수투극장","경북 포항시 대흥동");
		
		//SubProgram subprogram = new SubProgram();
		// subprogram.Statistics("구미공단");
		//subprogram.Booking("1BUN","대구광장","1","테넷","6","C3");
		// subprogram.UnBooking("1BUN","6C3");

	}
}