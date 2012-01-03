//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.prov.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}



//#endif