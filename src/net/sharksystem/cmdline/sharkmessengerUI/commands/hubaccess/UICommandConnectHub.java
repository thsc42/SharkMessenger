package net.sharksystem.cmdline.sharkmessengerUI.commands.hubaccess;

import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerApp;
import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerUI;
import net.sharksystem.cmdline.sharkmessengerUI.UICommand;
import net.sharksystem.cmdline.sharkmessengerUI.commandarguments.*;
import net.sharksystem.hub.peerside.HubConnectorDescription;
import net.sharksystem.hub.peerside.TCPHubConnectorDescriptionImpl;

import java.util.List;

public class UICommandConnectHub extends UICommand {
    private UICommandStringArgument hubHost;
    private UICommandIntegerArgument hubPortNumber;

    public UICommandConnectHub(SharkMessengerApp sharkMessengerApp, SharkMessengerUI sharkMessengerUI,
                               String identifier, boolean rememberCommand) {
        super(sharkMessengerApp, sharkMessengerUI, identifier, rememberCommand);

        this.hubHost = new UICommandStringArgument(sharkMessengerApp);
        this.hubPortNumber = new UICommandIntegerArgument(sharkMessengerApp);
    }

    @Override
    protected boolean handleArguments(List<String> arguments) {
        if (arguments.size() < 2) {
            this.getSharkMessengerApp().tellUIError("hostname and port number expected");
            return false;
        }

        boolean failed = false;
        if(!hubHost.tryParse(arguments.get(0))) {
            this.getSharkMessengerApp().tellUIError("could not parse hostname:" + arguments.get(0));
            failed = true;
        }

        if(!hubPortNumber.tryParse(arguments.get(1))) {
            this.getSharkMessengerApp().tellUIError("could not parse port number:" + arguments.get(1));
            failed = true;
        }

        return !failed;
    }

    @Override
    protected UICommandQuestionnaire specifyCommandStructure() {
        return null;
    }

    @Override
    protected void execute() throws Exception {
        HubConnectorDescription hubDescription = new TCPHubConnectorDescriptionImpl(
                this.hubHost.getValue(),
                this.hubPortNumber.getValue(),
                true);

        this.getSharkMessengerApp().tellUI("try to connect to hub: " + hubDescription);

        this.getSharkMessengerApp().getHubConnectionManager().connectHub(hubDescription);
    }

    @Override
    public String getDescription() {
        return "";
    }
}
