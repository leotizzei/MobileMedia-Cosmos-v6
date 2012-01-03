package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IExceptionHandler;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IMobileResources;
import br.unicamp.ic.sed.mobilemedia.media.impl.ComponentFactory;
import br.unicamp.ic.sed.mobilemedia.media.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IPhoto;

//#ifdef includeCopyPhoto
class MediaMainController implements CommandListener{
//#else
//class MediaMainController{
//#endif
	
	private IManager manager = ComponentFactory.createInstance();
	//private MediaListScreen mediaListScreen;
	private MediaListController mediaListController;
	private MIDlet midlet;
	private String lastAlbumName;
	
	public void showMediaList( String albumName , String mediaType ){
		try {
			this.lastAlbumName = albumName;
			
			IMobileResources resources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
			this.midlet = resources.getMainMIDlet();
			
			mediaListController = new MediaListController( midlet );
			//#if Album && includeMusic
			if( mediaType != null )
				ScreenSingleton.getInstance().setCurrentMediaType( mediaType );
			//#endif
			
			ScreenSingleton.getInstance().setCurrentStoreName( albumName );
			mediaListController.showMediaList(albumName, false, false);
			
		} catch (UnavailablePhotoAlbumException e) {
			IExceptionHandler handler = (IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
			handler.handle(e);
		}
	}
	
	public void showLastMediaList(){
		if( this.lastAlbumName != null )
			this.showMediaList( this.lastAlbumName , null );
		else{
			IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");
			album.reinitAlbumListScreen();
		}
	}
	
	//#ifdef includeCopyPhoto
	private String mediaName;
	private AddMediaToAlbum addMediaToAlbum;
	
	public void initCopyPhotoToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType ) {
		
		String title = new String("Copy Photo to Album");
		String labelPhotoPath = new String("Copy to Album:");
		
		AddMediaToAlbum addMediaToAlbum = null;
		//#ifdef includeMusic
		addMediaToAlbum = new AddMediaToAlbum( title , mediaType.equals( Constants.MUSIC_MEDIA ) );
		//#else
		addMediaToAlbum = new AddMediaToAlbum( title , false );
		//#endif
		
		addMediaToAlbum.setPhotoName( mediaName );
		addMediaToAlbum.setLabelPhotoPath( labelPhotoPath );
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if ( media != null ){
			addMediaToAlbum.setImage( (Image)media );
			addMediaToAlbum.setImageBytes( mediaBytes );
		}
		//#endif	
			
		this.mediaName = mediaName;
		this.addMediaToAlbum = addMediaToAlbum ;
		
		//Get all required interfaces for this method
		IMobileResources resources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = resources.getMainMIDlet();
		
		//addMediaToAlbum.setCommandListener( this );
		Display.getDisplay( midlet ).setCurrent( addMediaToAlbum );
				
		addMediaToAlbum.setCommandListener(this);
	
	}
	
	private void savePhoto() throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException{
		IManager manager = ComponentFactory.createInstance();
		IMediaData mediaData = null;
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");


		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		Image img = this.addMediaToAlbum.getImage();
		
		if (img == null)
		//#endif
		{
		//#if Album && includeMusic
		mediaData = filesystem.getMediaInfo(mediaName , ScreenSingleton.getInstance().getCurrentMediaType() );
		//#else
		mediaData = filesystem.getMediaInfo(mediaName , null );
		//#endif
		}
		
		
		AddMediaToAlbum addMediaToAlbum = this.addMediaToAlbum;
		
		
		String photoName = addMediaToAlbum.getPhotoName(); 
		System.out.println("[PhotoViewController.savePhoto] photoName = "+photoName);
		
		String albumName = addMediaToAlbum.getPath();
		System.out.println("[PhotoViewController.savePhoto] albumName = "+albumName);
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (img != null){ 
			filesystem.addImageData(photoName, addMediaToAlbum.getImageBytes(), albumName);
		}
		// #endif 
		
		// #if includeCopyPhoto && includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (img == null)
		//#endif
			
		// #if includeCopyPhoto 
		/* [NC] Added in scenario 06 */
		filesystem.addMediaData( photoName, mediaData, albumName);
		// #endif
		
		ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
		this.showLastMediaList();
	}

	public void commandAction(Command command, Displayable arg1) {
		IExceptionHandler handler = (IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
		
		
		if( command.getLabel().equals("Save Item")){
			try {
				System.out.println("MediaMainController -> Save Item");
				this.savePhoto();
			} catch (ImageNotFoundException e) {
				handler.handle(e);
			} catch (NullAlbumDataReference e) {
				handler.handle(e);
			} catch (InvalidImageDataException e) {
				handler.handle(e);
			} catch (PersistenceMechanismException e) {
				handler.handle(e);
			} catch (UnavailablePhotoAlbumException e) {
				handler.handle(e);
			}
		}
		else if (command.getLabel().equals("Cancel")){
			IPhoto photo = (IPhoto)manager.getRequiredInterface("IPhoto");
			photo.showLastImage();
		}
	}
	//#endif
}
