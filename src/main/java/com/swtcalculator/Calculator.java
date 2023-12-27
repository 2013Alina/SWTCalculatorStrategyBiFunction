package com.swtcalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Calculator {

	private BiFunction<Double, Double, Double> operation;
	private final static Map<String, BiFunction<Double, Double, Double>> operationMap = new HashMap<>();

	public Calculator() {
		initOperationMap();
	}

	public void setOperation(String operation) {
		this.operation = operationMap.get(operation);
	}

	public BiFunction<Double, Double, Double> getOperation() {
		return operation;
	}

	public Double calculate(Double a, Double b) {
		if (operation != null) {
			return operation.apply(a, b);
		} else {
			return null;
		}

	}

	private void initOperationMap() {
		operationMap.put("+", (a, b) -> a + b);
		operationMap.put("-", (a, b) -> a - b);
		operationMap.put("*", (a, b) -> a * b);
		operationMap.put("/", (a, b) -> a / b);

	}
}
