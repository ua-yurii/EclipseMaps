package com.consoleplugin.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.consoleplugin.export.ConsolePlugin;
import com.consoleplugin.export.IConsole;


public class ClearConsoleHandler {
	@Execute
	public static void execute() {
		for(IConsole console : ConsolePlugin.getConsoleManager().getConsoles()){
			if(console.isSelected())
				console.clear();
		}
	}
}
