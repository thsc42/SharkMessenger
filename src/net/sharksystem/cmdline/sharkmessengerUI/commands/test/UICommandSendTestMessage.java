package net.sharksystem.cmdline.sharkmessengerUI.commands.test;

import java.util.ArrayList;
import java.util.List;

import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerApp;
import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerUI;
import net.sharksystem.cmdline.sharkmessengerUI.UICommand;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandIntegerArgument;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandLongArgument;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandQuestionnaire;
import net.sharksystem.cmdline.sharkmessengerUI.commands.messenger.UICommandSendMessage;
import net.sharksystem.messenger.SharkMessengerComponent;

/**
 * This Command is for testing if messages were send correctly.
 * It sends out a specified amount of messages.
 * The content of these is an identifier and a timestamp.
 */
public class UICommandSendTestMessage extends UICommand {
    private final UICommandIntegerArgument amountMessages;
    private final UICommandLongArgument delayInMillis;

    private List<String> argsForSendMessage;

    public UICommandSendTestMessage(SharkMessengerApp sharkMessengerApp, SharkMessengerUI sharkMessengerUI,
            String identifier, boolean rememberCommand) {

        super(sharkMessengerApp, sharkMessengerUI, identifier, rememberCommand);

        this.amountMessages = new UICommandIntegerArgument(sharkMessengerApp);
        this.delayInMillis = new UICommandLongArgument(sharkMessengerApp);
    }

    /**
     * Put the needed parameters in a list in following order:
     * <p>
     * 
     * @param amountMessages as integer.
     *                       The amount of messages to send.
     *                       Negative values will equal to an amount of 0.
     * @param delayInMillis  as long.
     *                       The delay before sending the next message in
     *                       milliseconds.
     *                       Negative values will equal to a delay of 0.
     * @param channelIndex   as integer.
     *                       Index of the channel the message is send to.
     * @param sign           as boolean.
     *                       Determines, if the message will be signed.
     * @param encrypt        as boolean.
     *                       Determines, if the message will be encrypted.
     * @param message        as String.
     *                       The message to be send. 
     * @param receivers      as String.
     *                       The receivers of the message seperated by a comma ','.
     */
    @Override
    protected boolean handleArguments(List<String> arguments) {
        if (arguments.size() < 7) {
            return false;
        }

        boolean isParsable = amountMessages.tryParse(arguments.get(0))
                && delayInMillis.tryParse(arguments.get(1));

        this.argsForSendMessage = new ArrayList<>(arguments);
        this.argsForSendMessage.remove(0);
        this.argsForSendMessage.remove(0);

        if (amountMessages.getValue() < 0) {
            amountMessages.setValue(0);
        }

        if (delayInMillis.getValue() < 0) {
            delayInMillis.setValue(0L);
        }

        return isParsable;
    }

    @Override
    protected UICommandQuestionnaire specifyCommandStructure() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'specifyCommandStructure'");
    }

    @Override
    protected void execute() throws Exception {
        UICommand sendCommand = new UICommandSendMessage(getSharkMessengerApp(),
                getSharkMessengerUI(), getIdentifier(), false);

        SentMessageCounter tm = SentMessageCounter.getInstance();
        SharkMessengerComponent messenger = this.getSharkMessengerApp().getMessengerComponent();
        int channelID = Integer.parseInt(argsForSendMessage.get(0));
        String channelUri = messenger.getChannel(channelID).getURI().toString();
        String content = this.argsForSendMessage.get(3);
        String receivers = this.argsForSendMessage.get(4);
        for (int i = 0; i < this.amountMessages.getValue(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(tm.sendNextMessage(receivers,channelUri,content));
            sb.append(",");
            sb.append(System.currentTimeMillis());
            sb.append(",");
            sb.append(content);
            this.argsForSendMessage.set(3, sb.toString()); 

            sendCommand.initializeExecution(this.argsForSendMessage);
            Thread.sleep(this.delayInMillis.getValue());
        }
    }

    @Override
    public String getDescription() {
        return "Send a specified amount of messages";
    }

}
