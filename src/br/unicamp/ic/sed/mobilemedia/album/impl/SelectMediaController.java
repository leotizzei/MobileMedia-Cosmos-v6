//#if includeMusic && Album
// [NC] Added in the scenario 07

package br.unicamp.ic.sed.mobilemedia.album.impl;


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;



public class SelectMediaController extends AbstractController {
	private BaseController basePhotoController;
	private BaseController baseMusicController;

	public SelectMediaController(MIDlet midlet, AlbumListScreen albumListScreen , BaseController photo , BaseController music ) {
		super(midlet , albumListScreen );
		
		this.basePhotoController = photo;
		this.baseMusicController = music;
	}
	public boolean handleCommand(Command command) {
		String label = command.getLabel();
      	System.out.println( "<* SelectMediaController.handleCommand() *>: " + label);
		
      	if (label.equals("Select")) {
 			List down = (List) Display.getDisplay(this.getMidlet()).getCurrent();
 			if (down.getString(down.getSelectedIndex()).equals("Photos")){
 				ScreenSingleton.getInstance().setCurrentMediaType( Constants.IMAGE_MEDIA );
 				basePhotoController.init();
 			}else{
 				ScreenSingleton.getInstance().setCurrentMediaType( Constants.MUSIC_MEDIA );
 				baseMusicController.init();
 			}
			return true;	
      	}
      	return false;
	}

}
//#endif