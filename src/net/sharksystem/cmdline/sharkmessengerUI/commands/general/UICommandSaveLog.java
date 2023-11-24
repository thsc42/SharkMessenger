package net.sharksystem.cmdline.sharkmessengerUI.commands.general;

import com.sun.source.tree.PackageTree;
import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerUI;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sharksystem.cmdline.sharkmessengerUI.SharkMessengerApp;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandQuestionnaireBuilder;
import net.sharksystem.cmdline.sharkmessengerUI.UICommand;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandQuestionnaire;
import net.sharksystem.cmdline.sharkmessengerUI.UICommandStringArgument;

/**
 * Command for saving the log in a file.
 * Format: Command followed by parameters (space separated)
 */
public class UICommandSaveLog extends UICommand {

    private final UICommandStringArgument fileName;

    public UICommandSaveLog(SharkMessengerApp sharkMessengerApp, SharkMessengerUI sharkMessengerUI,
                            String identifier, boolean rememberCommand) {
        super(sharkMessengerApp, sharkMessengerUI, identifier, rememberCommand);
        this.fileName = new UICommandStringArgument(sharkMessengerApp);
    }

    @Override
    public UICommandQuestionnaire specifyCommandStructure() {
        return new UICommandQuestionnaireBuilder().
                addQuestion("File path: ", this.fileName).
                build();
    }

    @Override
    public void execute() throws Exception {
        File file = new File(this.fileName.getValue());
        if (file.createNewFile()) {
            try {
                List<String> commandHistory = this.getSharkMessengerUI().getCommandHistory();
                PrintWriter pw = new PrintWriter(file);
                for (String command : commandHistory) {
                    pw.println(command);
                }
                pw.close();
            } catch (IOException e) {
                getSharkMessengerUI().printError("Couldn't write to or create file" + fileName);
            }
        } else {
            getSharkMessengerUI().printError("Won't write to already existing file");
        }
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Saves the current log to a file.");
        return sb.toString();
    }

    /**
     * Arguments needed in this order: 
     * <p>
     * @param fileName as String
     */
    @Override
    protected boolean handleArguments(List<String> arguments) {
        if (arguments.size() < 1) {
            return false;
        }

        boolean isParsable = fileName.tryParse(arguments.get(0));

        return isParsable;
    }

}
