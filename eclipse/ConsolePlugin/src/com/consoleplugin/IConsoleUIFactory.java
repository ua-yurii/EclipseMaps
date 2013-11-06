package com.consoleplugin;

import com.consoleplugin.export.IConsole;

public interface IConsoleUIFactory {
	public IConsoleUI createConsoleUI(IConsole console);
}
