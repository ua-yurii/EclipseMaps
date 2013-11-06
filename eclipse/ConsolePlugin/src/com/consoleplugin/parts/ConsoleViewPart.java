package com.consoleplugin.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.consoleplugin.IConsoleUI;
import com.consoleplugin.IConsoleUIFactory;
import com.consoleplugin.TabbedConsoleUI;
import com.consoleplugin.export.ConsolePlugin;
import com.consoleplugin.export.IConsole;
import com.consoleplugin.export.IConsoleManager;

public class ConsoleViewPart implements IConsoleUIFactory {
	private TabFolder tabConsoles;

	@PostConstruct
	public void createControls(Composite parent, MPart part) {

		tabConsoles = new TabFolder(parent, SWT.BOTTOM);

		tabConsoles.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				select();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				select();
			}

			private void select() {
				for (TabItem item : tabConsoles.getItems()) {
					assert item.getData() instanceof IConsoleUI;

					IConsoleUI ui = (IConsoleUI) item.getData();

					ui.setSelected(tabConsoles.getSelection()[0] == item);
				}
			}
		});

		IConsoleManager manager = ConsolePlugin.getConsoleManager();
		manager.setConsoleUiFactory(this);

		IConsole console = manager.addConsole(
				"com.consoleplugin.consoles.system", "System");

		System.setOut(console.getStream());
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		tabConsoles.setFocus();
	}

	@Override
	public IConsoleUI createConsoleUI(final IConsole console) {
		TabItem tabItem = new TabItem(tabConsoles, SWT.NONE);
		tabItem.setText(console.getName());
		tabItem.setToolTipText(console.getId());

		Text text = new Text(tabConsoles, SWT.READ_ONLY | SWT.MULTI
				| SWT.V_SCROLL);

		tabItem.setControl(text);

		IConsoleUI ui = new TabbedConsoleUI(console, tabItem, text);

		tabItem.setData(ui);

		return ui;
	}

}
