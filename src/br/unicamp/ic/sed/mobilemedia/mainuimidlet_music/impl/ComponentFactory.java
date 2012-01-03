
//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.mainuimidlet_music.impl;


import br.unicamp.ic.sed.mobilemedia.mainuimidlet_music.impl.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}
//#endif


