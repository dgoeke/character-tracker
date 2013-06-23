package com.soru.charactertracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class NumericInputDlg extends Dialog implements android.view.View.OnClickListener {
	public enum modes {
		PLUS, MINUS, EQUALS
	};
	
	DialogInterface.OnClickListener onOk = null;
	DialogInterface.OnClickListener onCancel = null;
	TextView resultValue;
	String currentText = "";
	modes currentMode = modes.MINUS;
	
	public NumericInputDlg(Context context) {
		super(context);
	}
	
	public void onOkClicked(DialogInterface.OnClickListener listener) {
		onOk = listener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.numeric_input);
		
		findViewById(R.id.buttonCancel).setOnClickListener(this);
		findViewById(R.id.buttonOk).setOnClickListener(this);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
		findViewById(R.id.button6).setOnClickListener(this);
		findViewById(R.id.button7).setOnClickListener(this);
		findViewById(R.id.button8).setOnClickListener(this);
		findViewById(R.id.button9).setOnClickListener(this);
		findViewById(R.id.button0).setOnClickListener(this);
		findViewById(R.id.buttonPlus).setOnClickListener(this);
		findViewById(R.id.buttonMinus).setOnClickListener(this);
		findViewById(R.id.buttonBackspace).setOnClickListener(this);
	
		resultValue = (TextView)findViewById(R.id.txtResultValue);
		resultValue.setText("-0");
	}

	public String getResult() {
		return resultValue.getText().toString();
	}

	public modes getResultMode() {
		return currentMode;
	}
	
	public int getResultValue() {
		if (currentText.length() > 0)
			return Integer.parseInt(currentText);
		else
			return 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonCancel: cancel(); break;
			
		case R.id.buttonOk:
			if (onOk != null)
				onOk.onClick(this, v.getId());

			this.dismiss();
			break;
			
		case R.id.button1: textEntered("1"); break;
		case R.id.button2: textEntered("2"); break;
		case R.id.button3: textEntered("3"); break;
		case R.id.button4: textEntered("4"); break;
		case R.id.button5: textEntered("5"); break;
		case R.id.button6: textEntered("6"); break;
		case R.id.button7: textEntered("7"); break;
		case R.id.button8: textEntered("8"); break;
		case R.id.button9: textEntered("9"); break;
		case R.id.button0: textEntered("0"); break;
		case R.id.buttonPlus: modeChanged(modes.PLUS); break;
		case R.id.buttonMinus: modeChanged(modes.MINUS); break;
		case R.id.buttonBackspace: backspacePressed(); break;
		}
		
	}

	private void drawResult() {
		String result = "";
		
		switch (currentMode) {
		case PLUS: result = "+"; break;
		case MINUS: result = "-"; break;
		}
		
		result += currentText;
		resultValue.setText(result);
	}

	private void backspacePressed() {
		if (currentText.length() > 0) {
			currentText = currentText.substring(0, currentText.length() - 1);
		} else if (currentMode == modes.PLUS || currentMode == modes.MINUS) {
			currentMode = modes.EQUALS;
		}
		
		drawResult();
	}

	private void modeChanged(modes mode) {
		currentMode = mode;
		drawResult();
		
	}

	private void textEntered(String val) {
		currentText += val;
		drawResult();
	}
}
