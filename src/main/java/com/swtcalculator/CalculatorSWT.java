package com.swtcalculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class CalculatorSWT {

	private Display display;
	private Shell shell;
	private FillLayout layout;
	private TabFolder tabFolder;
	private GridLayout gridLayout;
	private GridData gridData;
	private TabItem itemCalculator;
	private Composite compositeCalculator;
	private Composite compositeHistory;
	private StyledText textOperandFirst;
	private Combo operationCombo;
	private StyledText textOperandSecond;
	private Button checkButton;
	private Button calculateButton;
	private Label label;
	private Text textResult;
	private TabItem itemHistory;
	private Calculator calculator;
	private Validation validation;
	private History history;
	private org.eclipse.swt.widgets.List historyList;
	private Color color1, color2, color3;

	public void createContents() {

		display = new Display();
		shell = new Shell(display);
		shell.setText("Calculator");
		layout = new FillLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		shell.setLayout(layout);
		color1 = new Color(204, 102, 255);
		shell.setBackground(color1);

		calculator = new Calculator();
		validation = new Validation();
		history = new History();

		tabFolder = new TabFolder(shell, SWT.BORDER);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		tabFolder.setLayout(gridLayout);

		itemCalculator = new TabItem(tabFolder, SWT.NONE);
		itemCalculator.setText("Calculator");

		compositeCalculator = new Composite(tabFolder, SWT.NONE);
		itemCalculator.setControl(compositeCalculator);
		compositeCalculator.setLayout(new GridLayout(3, true));
		color2 = new Color(255, 204, 255);
		compositeCalculator.setBackground(color2);

		textOperandFirst = new StyledText(compositeCalculator, SWT.BORDER);
		textOperandFirst.setSize(100, 20);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData.heightHint = 24;
		textOperandFirst.setLayoutData(gridData);
		textOperandFirst.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				String input = textOperandFirst.getText().substring(0, event.start) + event.text
						+ textOperandFirst.getText().substring(event.end);
				event.doit = validation.validateOperand(input);
			}
		});
		textOperandFirst.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				if (!calculateButton.isEnabled()) {
					count();
				}
			}
		});

		operationCombo = new Combo(compositeCalculator, SWT.NONE);
		String[] mathOperations = new String[] { "+", "-", "*", "/" };
		operationCombo.setItems(mathOperations);
		operationCombo.setSize(100, 20);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		operationCombo.setLayoutData(gridData);
		operationCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String operationText = operationCombo.getText();
				calculator.setOperation(operationText);
			}
		});

		textOperandSecond = new StyledText(compositeCalculator, SWT.BORDER);
		textOperandSecond.setSize(100, 20);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData.heightHint = 24;
		textOperandSecond.setLayoutData(gridData);
		textOperandSecond.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				String input = textOperandSecond.getText().substring(0, event.start) + event.text
						+ textOperandSecond.getText().substring(event.end);
				event.doit = validation.validateOperand(input);
			}
		});
		textOperandSecond.addModifyListener(new ModifyListener() {
		    @Override
		    public void modifyText(ModifyEvent e) {
		    	if (!calculateButton.isEnabled()) {
		    		count();
				}  
		    }
		});

		checkButton = new Button(compositeCalculator, SWT.CHECK);
		checkButton.setText("Calculator on the fly");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gridData.verticalIndent = 250;
		checkButton.setLayoutData(gridData);
		checkButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        boolean isChecked = checkButton.getSelection();
		        calculateButton.setEnabled(!isChecked);
		    }
		});

		calculateButton = new Button(compositeCalculator, SWT.PUSH);
		calculateButton.setText("Calculate");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData.verticalIndent = 250;
		calculateButton.setLayoutData(gridData);
		calculateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				count();
			}

		});

		label = new Label(compositeCalculator, SWT.NONE);
		label.setText("Result");
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gridData.verticalIndent = 20;
		label.setLayoutData(gridData);

		textResult = new Text(compositeCalculator, SWT.BORDER);
		textResult.setSize(100, 20);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gridData.verticalIndent = 20;
		textResult.setLayoutData(gridData);

		itemHistory = new TabItem(tabFolder, SWT.NONE);
		itemHistory.setText("History");
		compositeHistory = new Composite(tabFolder, SWT.NONE);
		itemHistory.setControl(compositeHistory);
		compositeHistory.setLayout(new GridLayout(1, true));
		
		color3 = new Color(204, 255, 255);
		compositeHistory.setBackground(color3);
		
		historyList = new List(compositeHistory, SWT.BORDER | SWT.V_SCROLL);
        gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1);
        gridData.heightHint = 200;
        historyList.setLayoutData(gridData);
        
        java.util.List<String> calculations = history.getCalculations();
        historyList.setItems(calculations.toArray(new String[0])); 
		
        shell.addDisposeListener(e -> disposeResources());
		shell.pack();
		shell.setSize(400, 500);
	}
	
	private void disposeResources() {
		color1.dispose();
		color2.dispose();
		color3.dispose();
	}

	public void open() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void count() {
		String operandFirst = textOperandFirst.getText();
		Double doubleFirstOperand = Double.parseDouble(operandFirst);
		String operandSecond = textOperandSecond.getText();
		Double doubleSecondOperand = Double.parseDouble(operandSecond);
		String operationText = operationCombo.getText();

		Double result = calculator.calculate(doubleFirstOperand, doubleSecondOperand);
		String resultString = String.valueOf(result);
		textResult.setText(resultString);

		String mathExpression = operandFirst + " " + operationText + " " + operandSecond + " = "
				+ resultString + " ;\n";
		addToHistory(mathExpression);
	}

	private void addToHistory(String mathExpression) {
		history.addCalculation(mathExpression);
		java.util.List<String> calculations = history.getCalculations();
		historyList.setItems(calculations.toArray(new String[0]));
	}

}

