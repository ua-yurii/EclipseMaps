package com.consoleplugin;

import org.osgi.framework.BundleContext;

import com.consoleplugin.export.IConsoleManager;
import com.consoleplugin.export.IConsoleService;

public class ConsoleService implements IConsoleService{
	private static IConsoleManager consoleManager;

	protected final void activate(BundleContext context) {
		consoleManager = new ConsoleManager();
	}

	protected final void deactivate(BundleContext context) {
		consoleManager = null;
	}

	@Override
	public IConsoleManager getConsoleManager() {
		return consoleManager;
	}
}