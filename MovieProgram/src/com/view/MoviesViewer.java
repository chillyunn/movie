package com.view;

import java.util.ArrayList;

import com.persistence.MoviesDTO;

public class MoviesViewer {
		public void showMovies(ArrayList<MoviesDTO> dtos) {
			for (MoviesDTO dto : dtos) {
				System.out.println(dto.toString());
			}
		}
	}

