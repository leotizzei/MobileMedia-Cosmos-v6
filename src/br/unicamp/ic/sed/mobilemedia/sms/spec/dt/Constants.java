/*
 * Created on 31-Mar-2005
 *
 */

//#ifdef includeSmsFeature 
package br.unicamp.ic.sed.mobilemedia.sms.spec.dt;

/**
 * @author tyoung
 *
 * This class contains all constants used by the MobileMedia application.
 * It is the central location to store any static string data.
 */
public class Constants {

	public static final String ALBUMLIST_SCREEN = "AlbumListScreen";
	public static final String IMAGE_SCREEN = "ImageScreen";
	public static final String IMAGELIST_SCREEN = "ImageListScreen";
	
	public static final String NEWALBUM_SCREEN = "NewAlbumScreen";
	public static final String CONFIRMDELETEALBUM_SCREEN = "ConfirmDeleteAlbumScreen";
	public static final String ADDPHOTOTOALBUM_SCREEN = "AddMediaToAlbum";

	
	public static final String IMAGE_MEDIA = "ImageMedia";
	
	//#ifdef device_screen_176x205
    
    /** Screen Size*/
	public static final int SCREEN_WIDTH  = 176;
	public static final int SCREEN_HEIGHT = 205;
    
	//#endif

}
//#endif