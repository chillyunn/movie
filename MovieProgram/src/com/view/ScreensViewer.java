package com.view;

import java.util.ArrayList;

import com.persistence.ScreensDTO;

public class ScreensViewer {
	public void showScreens(ArrayList<ScreensDTO> dtos) {
		for (ScreensDTO dto : dtos) {
			System.out.println(dto.toString());
		}
	}
}
