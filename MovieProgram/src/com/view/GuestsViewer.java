package com.view;

import java.util.ArrayList;

import com.persistence.GuestsDTO;

public class GuestsViewer {
	public void showGuests(ArrayList<GuestsDTO> dtos) {
		for(GuestsDTO dto:dtos) {
			System.out.println(dto.toString());
		}
	}	
}

