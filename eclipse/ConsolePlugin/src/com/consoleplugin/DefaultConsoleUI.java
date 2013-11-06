package com.consoleplugin;

import java.io.PrintStream;

public class DefaultConsoleUI implements IConsoleUI {
	public static final DefaultConsoleUI instance = new DefaultConsoleUI();

	private DefaultConsoleUI() {
	}

	@Override
	public PrintStream getStream() {
		return System.out;
	}

	@Override
	public void setSelected(boolean selected) {
		
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void clear() {
		
	}

}
