package net.sharksystem.cmdline.sharkmessengerUI.commands.messenger;

import net.sharksystem.SharkException;
import net.sharksystem.cmdline.sharkmessengerUI.*;
import net.sharksystem.messenger.SharkMessageList;

import java.io.IOException;
import java.util.List;

public class UICommandListMessages extends UICommandProduceChannelListBefore {
    private final UICommandIntegerArgument channelIndex;

    public UICommandListMessages(SharkMessengerApp sharkMessengerApp, SharkMessengerUI sharkMessengerUI,
                                 String identifier, boolean rememberCommand) {
        super(sharkMessengerApp, sharkMessengerUI, identifier, rememberCommand);
        this.channelIndex = new UICommandIntegerArgument(sharkMessengerApp);
    }

    @Override
    public UICommandQuestionnaire specifyCommandStructure() {
        return new UICommandQuestionnaireBuilder()
                .addQuestion("Index target channel (0..n): ", this.channelIndex)
                .build();
    }

    @Override
    public void execute() throws Exception {
        try {
            SharkMessageList messages =
                this.getSharkMessengerApp().getMessengerComponent().getChannel(
                        this.channelIndex.getValue()).getMessages();

            ChannelPrinter.printMessages(this.getPrintStream(), messages);
        } catch (SharkException | IOException e) {
            this.printErrorMessage(e.getLocalizedMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Returns one or more messages a peer received.";
    }

    /**
     * Arguments needed in this order: 
     * <p>
     * channelIndex as UICommandIntegerArgument
     * <p>
     */
    @Override
    protected boolean handleArguments(List<String> arguments) {
        if(arguments.size() < 1) {
            return false;
        }
        boolean isParsable = channelIndex.tryParse(arguments.get(0));
        return isParsable;
    }
}