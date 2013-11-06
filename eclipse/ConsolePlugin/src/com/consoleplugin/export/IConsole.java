package com.consoleplugin.export;

import java.io.PrintStream;

public interface IConsole {
	public PrintStream getStream();
	
	public String getId();
	
	public void setName(String name);
	
	public String getName();
	
	public boolean isSelected();

	public void clear();
}
