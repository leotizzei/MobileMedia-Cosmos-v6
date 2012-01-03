/*
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 * MobileMedia-Cosmos-OO-v4
 * 
 */

/*begin - added in MobileMedia-Cosmos-OO-v4*/
package br.unicamp.ic.sed.mobilemedia.media.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.media.spec.req.IExceptionHandler;

abstract class AbstractController implements CommandListener, ControllerInterface {
	
	private MIDlet midlet;
	
	//Define a successor to implement the Chain of Responsibility design pattern
	private ControllerInterface nextController;

	/**
	 * @param midlet
	 * @param nextController
	 * @param albumData
	 * @param albumListScreen
	 * @param currentScreenName
	 */
	public AbstractController(MIDlet midlet ) {
		this.midlet = midlet;
		// [EF] Senario 04: A singleton ScreenSingleton was created in order to all other access it. 
		// [EF] I think some data need to be unique (e.g. currentScreenName) to make them consistent for all controllers.
	}
	
	/* 
	 * Handle events. For now, this just passes control off to a 'wrapper'
	 * so we can ensure, in order to use it in the aspect advice
	 * (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		
			this.postCommand(c);
		
	}

	/**
	 * [EF] Scenario 04: Just forward method.
	 * @return .the currentStoreName
	 *//*
	public String getCurrentStoreName() {
		return ScreenSingleton.getInstance().getCurrentStoreName();
	}
*/
	


    /**
     * [EF] RENAMED in Scenario 04: remove "Name". Purpose: avoid method name conflict
	 * Get the current screen name that is displayed
	 */
	protected Displayable getCurrentScreen() {
        return Display.getDisplay(midlet).getCurrent();
    }
	
    protected MIDlet getMidlet() {
		return midlet;
	} 

    /**
	 * @return the nextController
	 */
    protected ControllerInterface getNextController() {
		return nextController;
	} 
    
    /* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#postCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
    public boolean postCommand(Command command) {
        System.out.println("AbstractController::postCommand - Current controller is: " + this.getClass().getName());
        System.out.println("Command label="+command.getLabel());
        
        IManager manager = ComponentFactory.createInstance();
        IExceptionHandler handler = (IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
        
        //If the current controller cannot handle the command, pass it to the next
        //controller in the chain.
        try{
	    	if (handleCommand(command) == false) {
				ControllerInterface next = this.getNextController();
			    if (next != null) {
			        System.out.println("Passing to next controller in chain: " + next.getClass().getName());
			        return next.postCommand(command);
			    } 
			    else {
			        System.out.println("AbstractController::postCommand - Reached top of chain. No more handlers for command: " + command.getLabel());   
			        return false;
			    }
	    	}
        } catch (UnavailablePhotoAlbumException e) {
			handler.handle( e );
		} catch (ImageNotFoundException e) {
			handler.handle( e );
		} catch (NullAlbumDataReference e) {
			handler.handle( e );
		} catch (PersistenceMechanismException e) {
			handler.handle( e );
		} catch (InvalidImageDataException e) {
			handler.handle( e );
		}
    	return false;
	} 

	
	
	

	/**
	 * Set the current screen for display, after alert
	 */
	protected void setCurrentScreen(Alert a, Displayable d) {
    	System.out.println("midlet = "+midlet);
        Display.getDisplay(midlet).setCurrent(a, d);
    }

	/**
	 * Set the current screen for display
	 */
	protected void setCurrentScreen(Displayable d) {
        System.out.println("[AbstractController.setCurrentScreen(..)] midlet = "+midlet);
    	Display.getDisplay(midlet).setCurrent(d);
    }

	protected void setMidlet(MIDlet midlet) {
		this.midlet = midlet;
	}

	/**
	 * @param nextController the nextController to set
	 */
	protected void setNextController(ControllerInterface nextController) {
		System.out.println("[AbstractController.setNextController] nextController="+nextController.getClass().getName());
		this.nextController = nextController;
	}
	
	protected String typeOfmedia = null;
	//#if Album && includeMusic
	
	public void setTypeOfMedia( String tMedia ){
		this.typeOfmedia = tMedia;
	}
	
	public String getTypeOfMedia( ){
		if( typeOfmedia == null )
			typeOfmedia = ScreenSingleton.getInstance().getCurrentMediaType();
		return this.typeOfmedia;
	}
	//#endif
}
/*end - added in MobileMedia-Cosmos-OO-v4*/