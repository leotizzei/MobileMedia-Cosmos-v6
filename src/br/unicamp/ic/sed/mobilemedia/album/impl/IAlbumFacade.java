/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */ 
package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMobileResources;


class IAlbumFacade implements IAlbum{
	
	//#ifdef Album
	private AlbumListScreen albumPhoto;
	private AlbumController albumController;
	private BaseController imageRootController;
	//#endif
	
	//#ifdef includeMusic
	private AlbumListScreen albumMusic;
	private AlbumController albumMusicController;
	private BaseController musicRootController;
	//#endif
	

	public void reinitAlbumListScreen(){
		//#if Album && includeMusic
		if( ScreenSingleton.getInstance().getCurrentMediaType() != null ){
			if( ScreenSingleton.getInstance().getCurrentMediaType().equals(Constants.IMAGE_MEDIA) ){
				ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
				albumController.setCurrentScreen( imageRootController.getAlbumListScreen() );
			}
			else{
				ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
				albumController.setCurrentScreen( musicRootController.getAlbumListScreen() );
			}
		}else{
			albumController.setCurrentScreen( ScreenSingleton.getInstance().getMainMenu() );
		}
		//#elif Album
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		albumController.setCurrentScreen( imageRootController.getAlbumListScreen() );	
		//#elif includeMusic
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.ALBUMLIST_SCREEN );
		albumMusicController.setCurrentScreen( musicRootController.getAlbumListScreen() );
		//#endif
		
	}
	
	public void startUp() {

		IManager manager = ComponentFactory.createInstance();
		IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		MIDlet midlet = mobileResources.getMainMIDlet();
		
		// #ifdef Album
		// [NC] Added in the scenario 07
		
		albumPhoto = new AlbumListScreen();
		imageRootController = new BaseController( midlet , albumPhoto );
		
		//#if Album && includeMusic
		imageRootController.setTypeOfMedia( Constants.IMAGE_MEDIA );
		//#endif
		
		albumController = new AlbumController( midlet , albumPhoto );
		albumController.setNextController( imageRootController );
		albumPhoto.setCommandListener(albumController);
		//#endif
		
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		albumMusic = new AlbumListScreen();
		musicRootController = new BaseController( midlet , albumMusic );
		
		//#if Album && includeMusic
		musicRootController.setTypeOfMedia( Constants.MUSIC_MEDIA );
		//#endif
		
		albumMusicController = new AlbumController(midlet , albumMusic);
		albumMusicController.setNextController(musicRootController );
		albumMusic.setCommandListener(albumMusicController);
		//#endif
		
		// #if includeMusic && Album
		// [NC] Added in the scenario 07
		SelectMediaController selectcontroller = new SelectMediaController( midlet , albumPhoto , imageRootController,musicRootController);
		selectcontroller.setNextController(imageRootController);
		
		SelectTypeOfMedia mainscreen = new SelectTypeOfMedia();
		mainscreen.initMenu();
		mainscreen.setCommandListener(selectcontroller);
		
		Display.getDisplay( midlet ).setCurrent(mainscreen);
		ScreenSingleton.getInstance().setMainMenu(mainscreen);
		//#elif Album
		imageRootController.init( );
		//#elif includeMusic
		musicRootController.init( );
		//#endif
		
	}
}