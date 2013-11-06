package com.consoleplugin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.consoleplugin.export.IConsole;

public class TabbedConsoleUI implements IConsoleUI {

	@SuppressWarnings("unused")
	private TabItem tabItem;
	private OutputStream out;
	private PrintStream stream;
	private Text text;
	private IConsole console;
	private boolean isSelected;

	public TabbedConsoleUI(IConsole console, TabItem tabItem, Text text) {
		assert tabItem == null;
		assert text == null;
		assert console == null;

		this.console = console;
		this.tabItem = tabItem;
		this.text = text;

		out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				if (TabbedConsoleUI.this.text.isDisposed())
					return;
				TabbedConsoleUI.this.text.append(String.valueOf((char) b));
			}
		};

		stream = new PrintStream(out);
	}

	@Override
	public PrintStream getStream() {
		return stream;
	}

	public IConsole getConsole() {
		return console;
	}

	@Override
	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void clear() {
		text.setText("");
	}
}
