/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;

//#ifdef includeMusic
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IMusic;
//#endif

//#ifdef Album
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IPhoto;
//#endif

class MediaController extends MediaListController {

	private IMediaData image;

	private MIDlet midlet;

	private NewLabelScreen screen;
	
	private String currentSelectedImageName;
	private String albumName;
	
	public MediaController (MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;
	}

	private void editLabel() throws ImageNotFoundException, NullAlbumDataReference{
		//TODO print error message on the screen
		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		String selectedImageName = getSelectedImageName();
		
		image = filesystem.getMediaInfo(selectedImageName, this.typeOfmedia);
		
		NewLabelScreen newLabelScreen = new NewLabelScreen("Edit Label Photo", NewLabelScreen.LABEL_PHOTO);
		newLabelScreen.setCommandListener(this);
		this.setScreen( newLabelScreen );
		setCurrentScreen( newLabelScreen );
		newLabelScreen = null;
		
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.NEWLABEL_SCREEN );

	}



	/**
	 * @return the image
	 */
	private IMediaData getImage() {
		return image;
	}

	private NewLabelScreen getScreen() {
		return screen;
	}

	/**
	 * Get the last selected image from the Photo List screen.
	 * TODO: This really only gets the selected List item. 
	 * So it is only an image name if you are on the PhotoList screen...
	 * Need to fix thisnextController
	 */
	private String getSelectedImageName() {
		List selected = (List) Display.getDisplay(midlet).getCurrent();
		String name = selected.getString(selected.getSelectedIndex());
		this.currentSelectedImageName = name;
		return name;
	}

	/**
	 * TODO [EF] update this method or merge with method of super class.
	 * Go to the previous screen
	 * @throws UnavailablePhotoAlbumException 
	 */
	private boolean goToPreviousScreen() throws UnavailablePhotoAlbumException {
		System.out.println("<* MediaController.goToPreviousScreen() *>");
		String currentScreenName = ScreenSingleton.getInstance().getCurrentScreenName();

		if (currentScreenName.equals(Constants.ALBUMLIST_SCREEN)) {
			System.out.println("Can't go back here...Should never reach this spot");
		} else if (currentScreenName.equals(Constants.IMAGE_SCREEN)) {		    
			//Go to the image list here, not the main screen...

			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		else if (currentScreenName.equals(Constants.ADDPHOTOTOALBUM_SCREEN)) {

			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}else if(currentScreenName.equals(Constants.IMAGELIST_SCREEN)){
			/*IManager manager = ComponentFactory.createInstance();
			IMobilePhone phone = (IMobilePhone) manager.getRequiredInterface("IMobilePhone");
			phone.goToPreviousScreen();
			return true;
			*/
		}else if(currentScreenName.equals(Constants.NEWLABEL_SCREEN )){
			this.showMediaList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		System.out.println("[MediaController could not handle either BACK or CANCEL: goToPreviousScreen() returns false]");
		return false;
	}

	/**
	 * modified in MobileMedia-Cosmos-OO-v4
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException 
	 * @throws InvalidImageDataException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException 
	 * @throws UnavailablePhotoAlbumException 
	 */
	public boolean handleCommand(Command command) throws ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException, UnavailablePhotoAlbumException  {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		//#if Album && includeMusic
		this.setTypeOfMedia( ScreenSingleton.getInstance().getCurrentMediaType() );
		//#endif
		
		//#ifdef Album
		if (label.equals("View")) {
			String selectedImageName = getSelectedImageName();
			
			//#ifdef includeSorting
			IMediaData photoData = filesystem.getMediaInfo( selectedImageName , Constants.IMAGE_MEDIA );
			photoData.increaseNumberOfViews();
			filesystem.updateMediaInfo(photoData, photoData);
			//#endif
			
			showImage(selectedImageName);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGE_SCREEN);

			return true;
		}else
		//#endif
			
		//#ifdef includeMusic
		if( label.equals("Play")){
			IMusic music = (IMusic) manager.getRequiredInterface("IMusic");
			//#ifdef includeSorting
			IMediaData media = filesystem.getMediaInfo( this.getSelectedImageName() ,Constants.MUSIC_MEDIA);
			media.increaseNumberOfViews();
			filesystem.updateMediaInfo( media , media );
			//#endif
			music.PlayMusic( this.albumName , this.getSelectedImageName() );
		
			return true;
		} else	
		//#endif
			/** Case: Add photo * */
		 if (label.equals("Add")) {
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ADDPHOTOTOALBUM_SCREEN);
			String storeName = this.getCurrentStoreName();
			this.initAddPhotoToAlbum( storeName );
			return true;

			/** Case: Save Add photo * */
		} else if (label.equals("Save Item")) {
			//TODO print message error on the screen
			
				AddMediaToAlbum addMediaToAlbum = (AddMediaToAlbum) this.getCurrentScreen();
				String addedPhotoName = addMediaToAlbum.getPhotoName();
				String addedPhotoPath = addMediaToAlbum.getPath();
				
				filesystem.addNewMediaToAlbum( this.typeOfmedia , addedPhotoName , addedPhotoPath , getCurrentStoreName() );
				
				//#ifdef includeMusic
				if( addMediaToAlbum.isAddMusicMedia() )
					filesystem.setMusicType( addedPhotoName , addMediaToAlbum.getMediaType() );
				//#endif
				
			return goToPreviousScreen();
			/** Case: Delete selected Photo from recordstore * */
		} else if (label.equals("Delete")) {
			String selectedImageName = getSelectedImageName();
			
			filesystem.deleteMedia( this.typeOfmedia ,selectedImageName, getCurrentStoreName());
			
			showMediaList(getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;

			/** Case: Edit photo label
			 *  [EF] Added in the scenario 02 */
		} else if (label.equals("Edit Label")) {
			this.editLabel();
			return true;

			// #ifdef includeSorting
			/**
			 * Case: Sort photos by number of views 
			 * [EF] Added in the scenario 02 */
		} else if (label.equals("Sort by Views")) {
			showMediaList(getCurrentStoreName(), true, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

			return true;
			// #endif

			// #ifdef includeFavourites
			/**
			 * Case: Set photo as favorite
			 * [EF] Added in the scenario 03 */
		} else if (label.equals("Set Favorite")) {
			String selectedImageName = getSelectedImageName();
			
				//MediaData image = getAlbumData().getImageInfo(selectedImageName);
				IMediaData image = filesystem.getMediaInfo(selectedImageName , this.typeOfmedia );
				image.toggleFavorite();
				updateImage(image);
				System.out.println("<* BaseController.handleCommand() *> Image = "+ selectedImageName + "; Favorite = " + image.isFavorite());
			
			return true;

			/**
			 * Case: View favorite photos 
			 * [EF] Added in the scenario 03 */
		} else if (label.equals("View Favorites")) {
			showMediaList(getCurrentStoreName(), false, true);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

			return true;
			// #endif

			/** Case: Save new Photo Label */
		} else if (label.equals("Save new label")) {
			NewLabelScreen newLabelScreen = this.getScreen();
			String labelName = newLabelScreen.getLabelName();
			System.out.println("<* MediaController.handleCommand() *> Save Photo Label = "+ labelName);
			this.getImage().setMediaLabel( labelName );
			this.updateImage(image);
			return this.goToPreviousScreen();

			/** Case: Go to the Previous or Fallback screen * */
		} else if (label.equals("Back")) {
			System.out.println("[MediaController.handleCommand()] Back");
			return this.goToPreviousScreen();

			/** Case: Cancel the current screen and go back one* */
		} else if (label.equals("Cancel")) {
			return this.goToPreviousScreen();

		}
		
		return false;
	} 

	private void initAddPhotoToAlbum(String albumName) {

		//Get all required interfaces for this method
		/*IManager manager = (IManager)ComponentFactory.createInstance();
		IMobileResources iMobileResources = (IMobileResources)manager.getRequiredInterface("IMobileResources");*/
		MIDlet midlet = this.getMidlet();

		if( albumName == null){
			System.err.println("Image name is null");
			return;
		}
		else{
			AddMediaToAlbum addMediaToAlbum = null;
			//#ifdef includeMusic
			addMediaToAlbum  = new AddMediaToAlbum( albumName , ScreenSingleton.getInstance().getCurrentMediaType().equals( Constants.MUSIC_MEDIA ) );
			//#else
			addMediaToAlbum  = new AddMediaToAlbum( albumName , false );
			//#endif
			
			addMediaToAlbum.setCommandListener(this);
			Display.getDisplay( midlet ).setCurrent( addMediaToAlbum );

		}
	}

	/**
	 * @param image the image to set
	 */
	/*public void setImage(IMediaData image) {
		this.image = image;
	}*/



	private void setScreen(NewLabelScreen screen) {
		this.screen = screen;
	} 


	/**
	 * Show the current image that is selected
	 * Modified in MobileMedia-Cosmos-OO-v4
	 * @throws PersistenceMechanismException 
	 * @throws ImageNotFoundException 
	 */
	//#if Album
	private void showImage(String name) throws ImageNotFoundException, PersistenceMechanismException {
		// [EF] Instead of replicating this code, I change to use the method "getSelectedImageName()". 		

		IManager manager = ComponentFactory.createInstance();
		IPhoto photo = (IPhoto) manager.getRequiredInterface( "IPhoto" );
		photo.showPhoto( this.getAlbumName() , name );

	}
	//#endif

	private void updateImage(IMediaData image) throws InvalidImageDataException, PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		filesystem.updateMediaInfo(image, image);

	}

	public String getCurrentlySelectedImageName() {
		return currentSelectedImageName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getAlbumName() {
		return albumName;
	}

}
