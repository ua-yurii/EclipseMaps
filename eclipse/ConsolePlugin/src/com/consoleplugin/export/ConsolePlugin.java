package com.consoleplugin.export;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ConsolePlugin {

	public static IConsoleManager getConsoleManager() {
		IConsoleManager manager = null;
		BundleContext bundleContext = FrameworkUtil.getBundle(
				ConsolePlugin.class).getBundleContext();

		ServiceReference<?> sr = bundleContext
				.getServiceReference(IConsoleService.class.getName());

		if (sr != null) {
			IConsoleService service = (IConsoleService) bundleContext
					.getService(sr);

			if (service != null) {
				manager = service.getConsoleManager();
				return manager;
			}
		}

		return null;
	}
}
