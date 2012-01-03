//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;


import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IExceptionHandler;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobileResources;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.ISms;

public class PhotoController {
	private MIDlet midlet;
	private PhotoViewController photoViewController = null;
	private PhotoViewScreen photoViewScreen = null;
	
	private String imageName;
	private IManager manager = ComponentFactory.createInstance();
	
	public PhotoController(){
		IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = mobileResources.getMainMIDlet();
	}

	//#ifdef includeSmsFeature 
	public void initPhotoViewScreen(Image image , byte[] img ) {
		this.photoViewScreen = new PhotoViewScreen(image);
		this.photoViewScreen.setFromSMS( true );
		this.photoViewScreen.setImageBytes(img);
		this.photoViewController = new PhotoViewController( midlet , "noName" );
		this.photoViewScreen.setCommandListener(photoViewController);
		
		if( ScreenSingleton.getInstance().getCurrentScreenName() == null )
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		
		Display.getDisplay( midlet ).setCurrent( this.photoViewScreen );
	}
	//#endif

	public String getImageName() {
		return this.imageName;
	}

	public void showImage(String albumName, String imageName) {
		this.imageName = imageName;

		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		try {
			
			Image image = filesystem.getImageFromRecordStore(albumName, imageName);
			
			
			this.photoViewScreen = new PhotoViewScreen(image);
			this.photoViewController = new PhotoViewController( midlet , imageName );
			this.photoViewScreen.setCommandListener(photoViewController);
			
			//#ifdef includeSmsFeature
			IManager manager = ComponentFactory.createInstance();
			ISms sms = (ISms) manager.getRequiredInterface("ISms");
			this.photoViewController.setNextController( sms );
			this.photoViewScreen.setFromSMS( false );
			//#endif
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGE_SCREEN );
			
			Display.getDisplay( midlet ).setCurrent( this.photoViewScreen );
		
		} catch (ImageNotFoundException e) {
			IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
			handler.handle( e );
		} catch (PersistenceMechanismException e) {
			IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
			handler.handle( e );
		}
	}
	
	public void showLastImage(){
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGE_SCREEN );	
		Display.getDisplay( midlet ).setCurrent( this.photoViewScreen );
	}
}
//#endif