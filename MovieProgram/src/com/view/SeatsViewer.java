package com.view;

import java.util.ArrayList;

import com.persistence.SeatsDTO;

public class SeatsViewer {
	public void showSeats(ArrayList<SeatsDTO> dtos) {
		for (SeatsDTO dto : dtos) {
			System.out.println(dto.toString());
		}
	}
}
