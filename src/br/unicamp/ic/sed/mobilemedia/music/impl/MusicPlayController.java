// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.music.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.music.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.music.impl.ComponentFactory;
import br.unicamp.ic.sed.mobilemedia.music.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IMedia;

public class MusicPlayController extends AbstractController{
	// #ifdef includeCopyPhoto
	// [NC] Added in the scenario 07
	private String mediaName;
	//#endif
	
	private PlayMediaScreen pmscreen;
	private Displayable lastDisplay;
	
	public MusicPlayController(MIDlet midlet, PlayMediaScreen pmscreen, String mediaName ) {
		super(midlet);
		this.pmscreen = pmscreen;
		// #ifdef includeCopyPhoto
		// [NC] Added in the scenario 07
		this.mediaName = mediaName;
		//#endif
	}

	public boolean handleCommand(Command command) {
		String label = command.getLabel();
		System.out.println( "<* MusicPlayController.handleCommand() *> " + label);

		/** Case: Copy photo to a different album */
		if (label.equals("Start")) {
			pmscreen.startPlay();
			return true;
		}else if (label.equals("Stop")) {
			pmscreen.pausePlay();
				return true;
		}else if ((label.equals("Back"))||(label.equals("Cancel"))){
			pmscreen.pausePlay();
			// [NC] Changed in the scenario 07: just the first line below to support generic AbstractController
			IManager manager = ComponentFactory.createInstance();
			IMedia media = (IMedia) manager.getRequiredInterface("IMedia");
			media.showLastMediaList();
			return true;
		}
		 // #ifdef includeCopyPhoto
	  	 // [NC] Added in the scenario 07
		/** Case: Copy photo to a different album */
		else if (label.equals("Copy")) {
			this.initCopyMediaToAlbum( );
			this.lastDisplay = this.getCurrentScreen();
			return true;
		}
		//#endif
		else if( label.equals("Back")){
			IManager manager = ComponentFactory.createInstance();
			IMedia media = (IMedia) manager.getRequiredInterface("IMedia");
			media.showLastMediaList();
			return true;
		}
		else if( label.equals("Cancel")){
			this.setCurrentScreen( this.lastDisplay );
			return true;
		}
		return false;
	}
	 // #ifdef includeCopyPhoto
	  // [NC] Added in the scenario 07
	private void initCopyMediaToAlbum() {
		IManager manager = ComponentFactory.createInstance();
		IMedia media = (IMedia)manager.getRequiredInterface("IMedia");
		
		media.initCopyPhotoToAlbum(mediaName, null, null, Constants.MUSIC_MEDIA );
	}
	//#endif

	
}
//#endif
