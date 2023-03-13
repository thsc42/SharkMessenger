package net.sharksystem.utils.cmdline.control.commands.messenger;

import net.sharksystem.asap.ASAPHop;
import net.sharksystem.utils.cmdline.control.*;
import net.sharksystem.utils.cmdline.model.CLIModelInterface;
import net.sharksystem.utils.cmdline.view.CLIInterface;

import java.util.List;

public class CLICGetHopList extends CLICommand {

    private final CLICSharkPeerArgument peer;
    private final CLICChannelArgument channel;
    private final CLICIntegerArgument position;

    public CLICGetHopList(String identifier, boolean rememberCommand) {
        super(identifier, rememberCommand);
        this.peer = new CLICSharkPeerArgument();
        this.channel = new CLICChannelArgument(this.peer);
        this.position = new CLICIntegerArgument();
    }

    @Override
    public CLICQuestionnaire specifyCommandStructure() {
        return new CLICQuestionnaireBuilder().
                addQuestion("Please insert the peer name: ", peer).
                addQuestion("Please set the channel uri: ", channel).
                addQuestion("Please specify the position: ", position).
                build();
    }

    @Override
    public void execute(CLIInterface ui, CLIModelInterface model) throws Exception {
        int position = this.position.getValue();

        List<ASAPHop> hops = this.channel.getValue()
                        .getMessages()
                        .getSharkMessage(position, true)
                        .getASAPHopsList();


        StringBuilder sb = new StringBuilder();
        hops.forEach(hop -> {
            sb.append(hop.sender());
            if (hop != hops.get(hops.size() - 1)) {
                sb.append(" -> ");
            }
        });
        ui.printInfo(sb.toString());
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Returns the hops of a message.");
        return sb.toString();
    }

    @Override
    public String getDetailedDescription() {
        return this.getDescription();
    }
}