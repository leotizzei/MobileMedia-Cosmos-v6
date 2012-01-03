//#ifdef includeSmsFeature
package br.unicamp.ic.sed.mobilemedia.sms_media.impl;



public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}
//#endif


