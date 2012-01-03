
package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;

public class AddMediaToAlbum extends Form {
	
	TextField labeltxt = new TextField("Photo label", "", 15, TextField.ANY);
	TextField photopathtxt = new TextField("Path", "", 20, TextField.ANY);
	
	// #ifdef includeMusic
	// [NC] Added in the scenario 07
	TextField itemtype = new TextField("Type of media", "", 20, TextField.ANY);
	//#endif
	
	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	Image image = null;
	//#endif	
	
	Command ok;
	Command cancel;
	private byte[] imageBytes;

	public AddMediaToAlbum(String title, boolean addMusicMedia ) {
		super(title);
		this.addMusicMedia = addMusicMedia;
		
		this.append(labeltxt);
		this.append(photopathtxt);
	
		// #ifdef includeMusic
		// [NC] Added in the scenario 07
		if( addMusicMedia )
			this.append(itemtype);
		//#endif
		
		
		ok = new Command("Save Item", Command.SCREEN, 0);
		cancel = new Command("Cancel", Command.EXIT, 1);
		this.addCommand(ok);
		this.addCommand(cancel);
		System.out.println("AddMediaToAlbum created");
	}
	
	public String getPhotoName(){
		System.out.println("[AddMediaToAlbum.getPhotoName] "+labeltxt.getString());
		return labeltxt.getString();
	}
	
	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		labeltxt.setString( photoName );
	}
	
	public String getPath() {
		System.out.println("[AddMediaToAlbum.getPath] "+photopathtxt.getString());
		return photopathtxt.getString();
	}

	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	public void setLabelPhotoPath(String label) {
		photopathtxt.setLabel(label);
	}
	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	//#endif

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	
	public byte[] getImageBytes(){
		return this.imageBytes;
	}

	//#ifdef includeMusic
	public String getMediaType(){
		System.out.println("[AddMediaToAlbum.getMediaType] "+itemtype.getString() );
		return this.itemtype.getString();
	}

	public boolean isAddMusicMedia() {
		return addMusicMedia;
	}

	private boolean addMusicMedia = false;
	//#endif
}
