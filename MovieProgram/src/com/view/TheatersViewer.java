package com.view;
import java.util.ArrayList;

import com.persistence.TheatersDTO;

public class TheatersViewer {
	public void showTheaters(ArrayList<TheatersDTO> dtos) {
		for(TheatersDTO dto:dtos) {
			System.out.println(dto.toString());
		}
	}	
}
