package com.view;

import java.util.ArrayList;

import com.persistence.TheatersDTO;

public class TheatersViewer {
	public void showTheaters(ArrayList<TheatersDTO> dtos) {
		for (TheatersDTO dto : dtos) {
			System.out.println(dto.toString());
		}

	}
	public static String[] bringTheaters(ArrayList<TheatersDTO> dtos) {
		String[] theatersArr = new String[dtos.size()];
		for(int i=0;i<dtos.size();i++)
		{
			theatersArr[i]=dtos.get(i).toString();
		}
		return theatersArr;
	}

}

