package seedu.siasa.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.siasa.logic.commands.Command;
import seedu.siasa.logic.commands.CommandResult;
import seedu.siasa.model.Model;
import seedu.siasa.model.contact.Contact;

/**
 * Sorts the contact list.
 */
public class SortContactCommand extends Command {

    public static final String COMMAND_WORD = "sortcontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list alphabetically by the order specified.\n"
            + "Parameters: ORDER (asc, dsc)\n"
            + "Example: " + COMMAND_WORD + " dsc";

    public static final String MESSAGE_SUCCESS = "Sorted contacts";

    private final Comparator<Contact> comparator;

    public SortContactCommand(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
