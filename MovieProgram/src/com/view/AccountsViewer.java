package com.view;

import java.util.ArrayList;

import com.persistence.AccountsDTO;

public class AccountsViewer {
	public void showAccounts(ArrayList<AccountsDTO> dtos) {
		for(AccountsDTO dto:dtos) {
			System.out.println(dto.toString());
		}
	}	
}

