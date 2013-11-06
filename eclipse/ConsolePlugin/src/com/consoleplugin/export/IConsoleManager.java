package com.consoleplugin.export;

import java.io.PrintStream;
import java.util.List;

import com.consoleplugin.IConsoleUIFactory;

public interface IConsoleManager {

	public abstract List<IConsole> getConsoles();

	public abstract IConsole getConsole(String id);

	public abstract IConsole addConsole(String id, String name);
	
	public abstract void setConsoleUiFactory(IConsoleUIFactory consoleUifactory);

	public abstract PrintStream getPrintStream(String id);
}