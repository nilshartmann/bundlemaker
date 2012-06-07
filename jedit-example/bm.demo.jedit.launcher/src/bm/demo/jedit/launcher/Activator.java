package bm.demo.jedit.launcher;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.proto.jeditresource.Handler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * Starts the OSGi-fied jedit
 * @author nils
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		System.out.println("Register jeditresource URL handler...");
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put(URLConstants.URL_HANDLER_PROTOCOL,
				"jeditresource");
		context.registerService(URLStreamHandlerService.class.getName(),
				new JEditResourceHandlerService(), properties);

		System.out.println("Starting JEdit...");
		jEdit.main(new String[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	private static class JEditResourceHandlerService extends
			AbstractURLStreamHandlerService {
		private Handler jEditResourceHandler = new Handler();

		public URLConnection openConnection(URL url) throws IOException {
			return jEditResourceHandler.openConnection(url);
		}
	}

}
