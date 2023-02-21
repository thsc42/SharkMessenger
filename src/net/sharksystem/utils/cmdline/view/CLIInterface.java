package net.sharksystem.utils.cmdline.view;

/**
 * Interface that allows the CLIController to inform the CLI over any changes
 */
public interface CLIInterface {

    /**
     * Prints an info for the user to the command line
     * @param information The message
     */
    void printInfo(String information);

    /**
     * Prints an error to the command line
     * @param error The error message
     */
    void printError(String error);

    /**
     *
     */
    void exceptionOccurred(Exception exception);
}