package com.consoleplugin.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.consoleplugin.ConsolePart;


public class ClearConsoleHandler {
	@Execute
	public static void execute(MPart part) {
		//TODO make console service, access to console uing service
		ConsolePart console = (ConsolePart) part.getObject();
		console.clear();
	}
}
