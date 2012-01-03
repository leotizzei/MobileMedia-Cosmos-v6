/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMedia;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMobileResources;

/**
 * @author tyoung
 *
 * This is the base controller class used in the MVC architecture.
 * It controls the flow of screens for the MobilePhoto application.
 * Commands handled by this class should only be for the core application
 * that runs on any MIDP platform. Each device or class of devices that supports
 * optional features will extend this class to handle feature specific commands.
 *  
 */
class BaseController extends AbstractController {

	// [EF] Attributes albumController and photoController were commented because 
	// I'm not sure which one is the best solution: 
	// [EF] (i) Declare controllers here and have only one instance or
	// [EF] (ii) create controllerns when needed (current solution)
	//	private AlbumController albumController;
	//	private MediaController photoController;

	/**
	 * Pass a handle to the main Midlet for this controller
	 * @param midlet
	 */

	public BaseController(MIDlet midlet, AlbumListScreen albumListScreen ) {
		super( midlet , albumListScreen );
	}

	/**
	 * Initialize the controller
	 */
	protected void init( ) {
		IManager manager = ComponentFactory.createInstance();

		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		String[] albumNames = null;
		
		//#if Album && includeMusic
		if( ScreenSingleton.getInstance().getCurrentMediaType().equals( Constants.IMAGE_MEDIA ) )
			albumNames = filesystem.getAlbumNames( Constants.IMAGE_MEDIA );
		else
			albumNames = filesystem.getAlbumNames( Constants.MUSIC_MEDIA );
		//#else
		albumNames = filesystem.getAlbumNames( null );
		//#endif
		
		this.albumListScreen.deleteAll();
		for (int i = 0; i < albumNames.length; i++) {
			if (albumNames[i] != null) {
				this.albumListScreen.append(albumNames[i], null);
			}
		}

		this.albumListScreen.initMenu();
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		this.setCurrentScreen( this.albumListScreen );
	}

	/* 
	 * TODO [EF] Why this method receives Displayable and never uses?
	 */
	public boolean handleCommand(Command command) {


		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		//Can this controller handle the desired action?
		//If yes, handleCommand will return true, and we're done
		//If no, handleCommand will return false, and postCommand
		//will pass the request to the next controller in the chain if one exists.

		IManager manager = ComponentFactory.createInstance();
		
		
		/** Case: Select album**/
		if( label.equals("Select")){
			List down = (List) this.getCurrentScreen();
			String albumName = down.getString(down.getSelectedIndex());
			ScreenSingleton.getInstance().setCurrentStoreName( albumName );
			
			IMedia media = (IMedia)manager.getRequiredInterface( "IMedia" );
			
			//#if Album && includeMusic
			this.setTypeOfMedia( ScreenSingleton.getInstance().getCurrentMediaType() );
			//#endif
			
			media.showMediaList( albumName , this.typeOfmedia );
			return true;
		}
		
		/** Case: Exit Application **/
		else if (label.equals("Exit")) {
			IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
			System.out.println("mobileResources="+mobileResources);
			mobileResources.destroyApp(true);
			return true;

			/** Case: Go to the Previous or Fallback screen * */
		} else if (label.equals("Back")) {
			return goToPreviousScreen();

			/** Case: Cancel the current screen and go back one* */
		} else if (label.equals("Cancel")) {
			return goToPreviousScreen();

		}

		//If we couldn't handle the current command, return false
		return false;
	}

	/**
	 * BaseController handles actions in the IMAGELIST_SCREEN
	 * @return
	 */
	public boolean goToPreviousScreen() {
		System.out.println("<* BaseController.goToPreviousScreen() *>");
		//#if Album && includeMusic
		if( ScreenSingleton.getInstance().getCurrentScreenName().equals( Constants.ALBUMLIST_SCREEN )){
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.SELECT_TYPE_OF_MEDIA );
			this.setCurrentScreen( ScreenSingleton.getInstance().getMainMenu() );
			return true;
		}else
		//#endif
			if( ScreenSingleton.getInstance().getCurrentScreenName().equals( Constants.NEWALBUM_SCREEN )){
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
			this.init();
			return true;
		}
		
		return false;
	}
}
