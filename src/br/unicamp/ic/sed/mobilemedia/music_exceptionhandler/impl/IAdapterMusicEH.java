//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music_exceptionhandler.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.req.IExceptionHandler;

public class IAdapterMusicEH implements IExceptionHandler {

	public void handle(Exception exception) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler handler = (br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
		handler.handle(exception);

	}

}
//#endif