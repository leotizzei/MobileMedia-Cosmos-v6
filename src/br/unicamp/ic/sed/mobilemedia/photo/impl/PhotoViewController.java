/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */

//#if includeCopyPhoto && Album
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMedia;



/**
 * Added in MobileMedia-Cosmos-OO-v4
 */

class PhotoViewController extends AbstractController {

	private String imageName = "";
	private Displayable lastDisplay;
	
	
	public PhotoViewController(MIDlet midlet,  String imageName) {
		super( midlet );
		this.imageName = imageName;

	}

	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public boolean handleCommand(Command c) throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException{
		String label = c.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);
		
		/** Case: Copy photo to a different album */
		if (label.equals("Copy")) {
			this.initCopyPhotoToAlbum( );
			this.lastDisplay = this.getCurrentScreen();
			return true;
		}
		
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
	private void initCopyPhotoToAlbum() {
		IManager manager = ComponentFactory.createInstance();
		IMedia media = (IMedia)manager.getRequiredInterface("IMedia");
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (((PhotoViewScreen)this.getCurrentScreen()).isFromSMS()){
			
			Object mediaObj = ((PhotoViewScreen)this.getCurrentScreen()).getImage();
			byte[] mediaBytes = ((PhotoViewScreen)this.getCurrentScreen()).getImageBytes();
			
			media.initCopyPhotoToAlbum(imageName, mediaBytes, mediaObj, Constants.IMAGE_MEDIA );	
			return;
		}
		//#endif
		
		media.initCopyPhotoToAlbum(imageName, null, null, Constants.IMAGE_MEDIA );
	}
	// #endif
}
//#endif