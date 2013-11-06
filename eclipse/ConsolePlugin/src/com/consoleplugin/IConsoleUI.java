package com.consoleplugin;

import java.io.PrintStream;

public interface IConsoleUI {

	public PrintStream getStream();
	
	public void setSelected(boolean selected);
	
	public boolean isSelected();

	public void clear();
}
