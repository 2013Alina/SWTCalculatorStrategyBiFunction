package com.swtcalculator;

import java.util.ArrayList;
import java.util.List;

public class History {
	private List<String> calculations;

	public History() {
		calculations = new ArrayList<>();
	}

	public void addCalculation(String mathExpression) {
		calculations.add(mathExpression);
	}

	public List<String> getCalculations() {
		return calculations;
	}

}
