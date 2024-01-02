package com.swtcalculator;

public class Validation {
	
	public boolean validateOperand(String input) {
		try {
			
			if (input.length() == 1 && input.startsWith("-")) {
				return true;
			} else {
				Double.parseDouble(input);
				return true;
			}

		} catch (NumberFormatException e) {
			return false;
		}
	}
}
