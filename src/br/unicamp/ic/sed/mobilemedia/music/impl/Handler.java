//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IExceptionHandler;

class Handler {
	
	protected void handle(Exception e){
		IManager manager = ComponentFactory.createInstance();
		IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
		handler.handle(e);
	}

}
//#endif