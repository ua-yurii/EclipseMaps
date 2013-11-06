package com.consoleplugin;

import java.io.PrintStream;

import com.consoleplugin.export.IConsole;

public class Console implements IConsole {
	private String id;
	private String name;
	private IConsoleUI consoleUi;

	public Console(String id, String name) {
		this.id = id;
		this.name = name;
		this.consoleUi = DefaultConsoleUI.instance;
	}

	@Override
	public PrintStream getStream() {
		return consoleUi.getStream();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getName() {
		return name;
	}

	public void setConsoleUi(IConsoleUI consoleUi) {
		assert consoleUi == null;
		this.consoleUi = consoleUi;
	}

	@Override
	public boolean isSelected() {
		return consoleUi.isSelected();
	}

	@Override
	public void clear() {
		consoleUi.clear();
	}

}
