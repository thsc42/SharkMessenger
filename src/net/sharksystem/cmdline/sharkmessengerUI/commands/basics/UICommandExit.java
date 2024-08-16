package net.sharksystem.cmdline.sharkmessengerUI.commands.basics;

import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerUI;

import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerApp;
import net.sharksystem.cmdline.sharkmessengerUI.commands.helper.AbstractCommandNoParameter;

/**
 * Command for terminating the messenger.
 */
public class UICommandExit extends AbstractCommandNoParameter {
    public UICommandExit(SharkMessengerApp sharkMessengerApp, SharkMessengerUI sharkMessengerUI,
                         String identifier, boolean rememberCommand) {
        super(sharkMessengerApp, sharkMessengerUI, identifier, rememberCommand);
    }

    @Override
    public void execute() throws Exception {
        //this.printTODOReimplement();
        System.exit(1);
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Terminates the messenger.");
        return sb.toString();
    }
}
