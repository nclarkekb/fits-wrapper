package org.netarchivesuite.fitswrapper;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FitsRun {

	protected String fits_home;

	protected Method examine_method;

	protected Method output_method;

	protected Constructor<?> fits_constructor;

	protected Object fits_object;

	public static FitsRun getInstance(String fits_home, ClassLoader classloader) {
		FitsRun fr = null;
		try {
			Class<?>[] parameterTypes = new Class<?>[ 1 ];
			parameterTypes[ 0 ] = String.class;
			Class<?> fits_class = classloader.loadClass("edu.harvard.hul.ois.fits.Fits");
			Class<?> fitsOutput_class = classloader.loadClass("edu.harvard.hul.ois.fits.FitsOutput");
			Method examine_method = null;
			try {
				if ( fits_class != null ) {
					examine_method = fits_class.getMethod("examine", new Class[] {File.class});
				}
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			Method output_method = null;
			try {
				if ( fitsOutput_class != null ) {
					output_method = fitsOutput_class.getMethod("output", new Class[] {OutputStream.class});
				}
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			Constructor<?> fits_constructor = fits_class.getConstructor(parameterTypes);
			Object fits_object = fits_constructor.newInstance(new Object[] {fits_home});

			fr = new FitsRun();
			fr.fits_home = fits_home;
			fr.examine_method = examine_method;
			fr.output_method = output_method;
			fr.fits_constructor = fits_constructor;
			fr.fits_object = fits_object;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return fr;
	}

	public void close() {
		fits_object = null;
		fits_constructor = null;
		output_method = null;
		examine_method = null;
		fits_home = null;
	}

	public Exception examine(File file, OutputStream output) {
		Object fitsOutput_object;
		Exception exception = null;
		try {
			if ( fits_object != null && examine_method != null ) {
				try {
					fitsOutput_object = examine_method.invoke( fits_object, new Object[] {file} );
					if (fitsOutput_object != null && output_method != null) {
						output_method.invoke( fitsOutput_object, new Object[] {
								new FilterOutputStream(output) {
									@Override
									public void close() throws IOException {
									}
								}
						} );
					}
				}
				catch (IllegalAccessException e) {
					exception = e;
				}
				catch (InvocationTargetException e) {
					exception = e;
				}
			}
		}
		catch (SecurityException e) {
			exception = e;
		}
		catch (IllegalArgumentException e) {
			exception = e;
		}
		return exception;
	}

}
