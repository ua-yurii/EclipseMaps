package com.consoleplugin;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.consoleplugin.export.IConsole;
import com.consoleplugin.export.IConsoleManager;

public class ConsoleManager implements IConsoleManager {
	private Map<String, Console> consoles;
	private IConsoleUIFactory consoleUifactory;

	public ConsoleManager() {
		consoles = new HashMap<String, Console>();
	}

	@Override
	public List<IConsole> getConsoles() {
		return new ArrayList<IConsole>(consoles.values());
	}

	@Override
	public IConsole getConsole(String id) {
		return consoles.get(id);
	}

	@Override
	public Console addConsole(String id, String name) {
		Console console = null;

		if (consoles.containsKey(id))
			return consoles.get(id);

		console = new Console(id, name);

		if (consoleUifactory != null)
			console.setConsoleUi(consoleUifactory.createConsoleUI(console));

		consoles.put(id, console);
		return console;
	}

	@Override
	public void setConsoleUiFactory(IConsoleUIFactory consoleUifactory) {
		assert consoleUifactory == null;
		this.consoleUifactory = consoleUifactory;

		for (Console console : consoles.values()) {
			console.setConsoleUi(consoleUifactory.createConsoleUI(console));
		}
	}

	@Override
	public PrintStream getPrintStream(String id) {
		Console console = consoles.get(id);

		if (console == null)
			console = addConsole(id, id);

		return console.getStream();
	}
}
