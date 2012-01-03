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
import br.unicamp.ic.sed.mobilemedia.media.impl.ComponentFactory;
import br.unicamp.ic.sed.mobilemedia.media.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IFilesystem;

class MediaListController extends AbstractController {

	private MIDlet midlet;

	public MediaListController(MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;

	}

	// #ifdef includeSorting
	/**
	 * Sorts an int array using basic bubble sort
	 * 
	 * @param numbers the int array to sort
	 */
	private void bubbleSort(IMediaData[] images) {
		System.out.print("Sorting by BubbleSort...");		
		for (int end = images.length; end > 1; end --) {
			for (int current = 0; current < end - 1; current ++) {
				if (images[current].getNumberOfViews() > images[current+1].getNumberOfViews()) {
					exchange(images, current, current+1);
				}
			}
		}
		System.out.println("done.");
	}
	// #endif

	// #ifdef includeSorting
	/**
	 * @param images
	 * @param pos1
	 * @param pos2
	 */
	private void exchange(IMediaData[] images, int pos1, int pos2) {
		IMediaData tmp = images[pos1];
		images[pos1] = images[pos2];
		images[pos2] = tmp;
	}
	// #endif





	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(java.lang.String)
	 */
	public boolean handleCommand(Command command) throws UnavailablePhotoAlbumException, ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		if( label.equals("Back")){
			IManager manager = ComponentFactory.createInstance();
			IAlbum album = (IAlbum)manager.getRequiredInterface("IAlbum");
			album.reinitAlbumListScreen();
			return true;
		}

		return false;
	}

	

	/**
	 * Show the list of images in the record store
	 * TODO: Refactor - Move this to ImageList class
	 * @throws UnavailablePhotoAlbumException 
	 */
	protected void showMediaList(String recordName, boolean sort, boolean favorite) throws UnavailablePhotoAlbumException {
		
		//#if Album && includeMusic
		this.setTypeOfMedia( ScreenSingleton.getInstance().getCurrentMediaType() );
		//#endif
		
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		if (recordName == null)
			recordName = getCurrentStoreName();

		/*begin - modified in MobileMedia-Cosmos-OO-v4*/
		IManager manager = ComponentFactory.createInstance();

		MediaController mediaController = new MediaController( midlet );
		
		//#if Album && includeMusic
		mediaController.setTypeOfMedia( this.getTypeOfMedia() );
		//#endif
		
		mediaController.setAlbumName( recordName );
		mediaController.setNextController(this);
		
		MediaListScreen mediaListScreen;
		//#if Album && includeMusic
		if( this.getTypeOfMedia().equals(Constants.IMAGE_MEDIA))
			mediaListScreen = new MediaListScreen( MediaListScreen.SHOWPHOTO );
		else
			mediaListScreen = new MediaListScreen( MediaListScreen.PLAYMUSIC );
		//#elif Album
		mediaListScreen = new MediaListScreen( MediaListScreen.SHOWPHOTO );
		//#elif includeMusic
		mediaListScreen = new MediaListScreen( MediaListScreen.PLAYMUSIC );
		//#endif
		
		mediaListScreen.setCommandListener(mediaController);
		/*end - modified in MobileMedia-Cosmos-OO-v4*/


		//Command selectCommand = new Command("Open", Command.ITEM, 1);
		mediaListScreen.initMenu();


		IMediaData[] images = null;
		/*begin - modified in MobileMedia-Cosmos-OO-v4*/
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		images = filesystem.getMedias(recordName, this.typeOfmedia );

		/*end - modified in MobileMedia-Cosmos-OO-v4*/

		if (images==null) return;

		// #ifdef includeSorting
		// [EF] Check if sort is true (Add in the Scenario 02)
		if (sort) {
			this.bubbleSort(images);
		}
		// #endif

		//loop through array and add labels to list
		for (int i = 0; i < images.length; i++) {
			if (images[i] != null) {
				//Add album name to menu list

				String imageLabel = images[i].getMediaLabel();
				// #ifdef includeFavourites
				// [EF] Check if favorite is true (Add in the Scenario 03)
				if (favorite) {
					if (images[i].isFavorite())
						mediaListScreen.append(imageLabel, null);
				}
				else 
					// #endif
					mediaListScreen.append(imageLabel, null);

			}
		}
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		this.setCurrentScreen(mediaListScreen);
	}

	protected String getCurrentStoreName() {
		return ScreenSingleton.getInstance().getCurrentStoreName();
	}
}
