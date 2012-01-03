package br.unicamp.ic.sed.mobilemedia.main;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


//Following are pre-processor statements to include the required
//classes for device specific features. They must be commented out
//if they aren't used, otherwise it will throw exceptions trying to
//load classes that aren't available for a given platform.


/* 
 * 
 *
 * This is the main Midlet class for the core J2ME application
 * It contains all the basic functionality that should be executable
 * in any standard J2ME device that supports MIDP 1.0 or higher. 
 * Any additional J2ME features for this application that are dependent
 * upon a particular device (ie. optional or proprietary library) are
 * de-coupled from the core application so they can be conditionally included
 * depending on the target platform 
 * 
 * This Application provides a basic Photo Album interface that allows a user to view
 * images on their mobile device. 
 * */
public class MainUIMidlet extends MIDlet implements IMobileResources {

	//components
	br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager album;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager filesystem;
	br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager exceptionHandler;
	br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager media;
	
	//#ifdef Album
	br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager photo;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.IManager mainMidlet_photo;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl.IManager filesystemmgr_photo;
	br.unicamp.ic.sed.mobilemedia.photo_exceptionhandler.impl.IManager photo_exceptionhandler; 
	br.unicamp.ic.sed.mobilemedia.media_photo.IManager media_photo;
	//#endif
	
	//#ifdef includeMusic
	br.unicamp.ic.sed.mobilemedia.music.spec.prov.IManager music;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_music.impl.IManager filesystem_music;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_music.impl.IManager mainMidlet_music;
	br.unicamp.ic.sed.mobilemedia.media_music.impl.IManager media_music;
	br.unicamp.ic.sed.mobilemedia.music_exceptionhandler.impl.IManager music_exceptionHandler;
	//#endif
	
	//connectors

	br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.IManager mainMidlet_album;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.IManager filesystemmgr_album;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.IManager mainMidlet_exceptionHandler;
	br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl.IManager album_exceptionhandler;
	
	br.unicamp.ic.sed.mobilemedia.media_album.impl.IManager album_media;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_media.impl.IManager filesystem_media;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_media.impl.IManager main_media;
	br.unicamp.ic.sed.mobilemedia.exceptionhandler_media.impl.IManager exception_media;  
	
	
	
	public MainUIMidlet() {
		//do nothing
	}

	/**
	 * Start the MIDlet by creating new model and controller classes, and
	 * initialize them as necessary
	 */
	public void startApp() throws MIDletStateChangeException {

		System.out.println("Starting MobileMediaOO - v1");
		
		// create all imanagers
		filesystem = br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ComponentFactory.createInstance();

		album = br.unicamp.ic.sed.mobilemedia.album.impl.ComponentFactory.createInstance();

		exceptionHandler = br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl.ComponentFactory.createInstance();
		
		mainMidlet_album = br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.ComponentFactory.createInstance();
		
		filesystemmgr_album = br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.ComponentFactory.createInstance();
		
		mainMidlet_exceptionHandler = br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.ComponentFactory.createInstance();
		
		album_exceptionhandler = br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl.ComponentFactory.createInstance();
		
		media = br.unicamp.ic.sed.mobilemedia.media.impl.ComponentFactory.createInstance();
		
		album_media = br.unicamp.ic.sed.mobilemedia.media_album.impl.ComponentFactory.createInstance();
		
		filesystem_media = br.unicamp.ic.sed.mobilemedia.filesystemmgr_media.impl.ComponentFactory.createInstance();
		
		main_media = br.unicamp.ic.sed.mobilemedia.mainuimidlet_media.impl.ComponentFactory.createInstance();
		
		exception_media = br.unicamp.ic.sed.mobilemedia.exceptionhandler_media.impl.ComponentFactory.createInstance();
		
		
		//#ifdef Album
		photo = br.unicamp.ic.sed.mobilemedia.photo.impl.ComponentFactory.createInstance();
		mainMidlet_photo = br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.ComponentFactory.createInstance();
		filesystemmgr_photo = br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl.ComponentFactory.createInstance();
		media_photo = br.unicamp.ic.sed.mobilemedia.media_photo.ComponentFactory.createInstance();
		photo_exceptionhandler = br.unicamp.ic.sed.mobilemedia.photo_exceptionhandler.impl.ComponentFactory.createInstance();
		//#endif
		
		//#ifdef includeMusic
		music = br.unicamp.ic.sed.mobilemedia.music.impl.ComponentFactory.createInstance();
		filesystem_music = br.unicamp.ic.sed.mobilemedia.filesystemmgr_music.impl.ComponentFactory.createInstance();
		mainMidlet_music = br.unicamp.ic.sed.mobilemedia.mainuimidlet_music.impl.ComponentFactory.createInstance();
		media_music = br.unicamp.ic.sed.mobilemedia.media_music.impl.ComponentFactory.createInstance();
		music_exceptionHandler = br.unicamp.ic.sed.mobilemedia.music_exceptionhandler.impl.ComponentFactory.createInstance();
		//#endif
		
		/*********************************************************************************************/
		album.setRequiredInterface("IMobileResources", mainMidlet_album.getProvidedInterface("IMobileResources") );

		album.setRequiredInterface("IFilesystem",filesystemmgr_album.getProvidedInterface("IFilesystem"));

		album.setRequiredInterface("IExceptionHandler", album_exceptionhandler.getProvidedInterface("IExceptionHandler"));
		
		//#ifdef Album
		photo_exceptionhandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler") );
		filesystemmgr_photo.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));
		media_photo.setRequiredInterface("IMedia", media.getProvidedInterface("IMedia"));
		mainMidlet_photo.setRequiredInterface("IMobileResources", this );
		
		photo.setRequiredInterface("IMobileResources", mainMidlet_photo.getProvidedInterface("IMobileResources") );
		photo.setRequiredInterface("IFilesystem", filesystemmgr_photo.getProvidedInterface("IFilesystem"));
		photo.setRequiredInterface("IExceptionHandler", photo_exceptionhandler.getProvidedInterface("IExceptionHandler"));
		photo.setRequiredInterface("IMedia", media_photo.getProvidedInterface("IMedia"));
		
		media_photo.setRequiredInterface( "IPhoto", photo.getProvidedInterface("IPhoto"));
		media.setRequiredInterface("IPhoto", media_photo.getProvidedInterface("IPhoto"));
		//#endif
		
		// connectors
		
		filesystemmgr_album.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));

		mainMidlet_album.setRequiredInterface("IMobileResources", this );

		mainMidlet_exceptionHandler.setRequiredInterface("IMobileResources",  this );
		
		exceptionHandler.setRequiredInterface("IMobileResources", mainMidlet_exceptionHandler.getProvidedInterface("IMobileResources"));
		
		album_exceptionhandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler") );
		
		album_media.setRequiredInterface( "IAlbum" , album.getProvidedInterface("IAlbum" ) );
		album_media.setRequiredInterface( "IMedia" , media.getProvidedInterface("IMedia" ) );
		media.setRequiredInterface( "IAlbum",  album_media.getProvidedInterface("IAlbum") );
		album.setRequiredInterface("IMedia", album_media.getProvidedInterface("IMedia") );
		
		filesystem_media.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem") );
		media.setRequiredInterface("IFilesystem", filesystem_media.getProvidedInterface("IFilesystem") );
		
		main_media.setRequiredInterface("IMobileResources", this );
		media.setRequiredInterface("IMobileResources", main_media.getProvidedInterface("IMobileResources") );
		
		exception_media.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler"));
		media.setRequiredInterface("IExceptionHandler", exception_media.getProvidedInterface("IExceptionHandler"));
		
		//#ifdef includeMusic
		filesystem_music.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));
		mainMidlet_music.setRequiredInterface("IMobileResources", this );
		media_music.setRequiredInterface("IMedia", media.getProvidedInterface("IMedia"));
		music_exceptionHandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler"));
		
		music.setRequiredInterface("IFilesystem", filesystem_music.getProvidedInterface("IFilesystem"));
		music.setRequiredInterface("IMobileResources", mainMidlet_music.getProvidedInterface("IMobileResources"));
		music.setRequiredInterface("IMedia", media_music.getProvidedInterface("IMedia"));
		music.setRequiredInterface("IExceptionHandler", music_exceptionHandler.getProvidedInterface("IExceptionHandler"));
	
		media_music.setRequiredInterface("IMusic", music.getProvidedInterface("IMusic"));
		media.setRequiredInterface("IMusic", media_music.getProvidedInterface("IMusic"));
		//#endif
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.IManager sms = br.unicamp.ic.sed.mobilemedia.sms.impl.ComponentFactory.createInstance();
		
		//Sms-MobileResources
		br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl.IManager mobile_sms = br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl.ComponentFactory.createInstance();
		mobile_sms.setRequiredInterface("IMobileResources", this);
		
		sms.setRequiredInterface("IMobileResources", mobile_sms.getProvidedInterface("IMobileResources"));
	
		//Photo-Sms
		br.unicamp.ic.sed.mobilemedia.sms_photo.impl.IManager photo_sms = br.unicamp.ic.sed.mobilemedia.sms_photo.impl.ComponentFactory.createInstance();
		
		photo_sms.setRequiredInterface("IPhoto", photo.getProvidedInterface("IPhoto"));
		photo_sms.setRequiredInterface("ISms", sms.getProvidedInterface("ISms"));
		photo.setRequiredInterface("ISms", photo_sms.getProvidedInterface("ISms"));
		sms.setRequiredInterface("IPhoto", photo_sms.getProvidedInterface("IPhoto"));
		
		//sms-filesystem
		br.unicamp.ic.sed.mobilemedia.sms_filesystem.impl.IManager sms_filesystem = br.unicamp.ic.sed.mobilemedia.sms_filesystem.impl.ComponentFactory.createInstance(); 
		
		sms_filesystem.setRequiredInterface("IFilesystem",filesystem.getProvidedInterface("IFilesystem"));
		sms.setRequiredInterface("IFilesystem", sms_filesystem.getProvidedInterface("IFilesystem" ));
		
		//sms-exceptionhandler
		br.unicamp.ic.sed.mobilemedia.sms_exceptionhandler.impl.IManager sms_exceptionHandler = br.unicamp.ic.sed.mobilemedia.sms_exceptionhandler.impl.ComponentFactory.createInstance();
		sms_exceptionHandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler"));
		sms.setRequiredInterface("IExceptionHandler", sms_exceptionHandler.getProvidedInterface("IExceptionHandler"));
		
		//sms-media
		br.unicamp.ic.sed.mobilemedia.sms_media.impl.IManager sms_media = br.unicamp.ic.sed.mobilemedia.sms_media.impl.ComponentFactory.createInstance();
		sms_media.setRequiredInterface("IMedia", media.getProvidedInterface("IMedia"));
		sms.setRequiredInterface("IMedia", sms_media.getProvidedInterface("IMedia"));		
		//#endif
		
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum startAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) album.getProvidedInterface("IAlbum");
		startAlbum.startUp();

	}

	/**
	 * Pause the MIDlet
	 * This method does nothing at the moment.
	 */
	public void pauseApp() {
		//do nothing
	}

	/**
	 * Destroy the MIDlet
	 */
	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}

	public MIDlet getMainMIDlet() {
		return this;
	}

	
}