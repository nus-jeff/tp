package seedu.socket.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_LANGUAGE;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_PROFILE;
import static seedu.socket.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.socket.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.socket.commons.core.index.Index;
import seedu.socket.logic.commands.exceptions.CommandException;
import seedu.socket.model.Model;
import seedu.socket.model.Socket;
import seedu.socket.model.person.Person;
import seedu.socket.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.socket.testutil.EditPersonDescriptorBuilder;
import seedu.socket.testutil.RemovePersonDescriptorBuilder;
import seedu.socket.testutil.TypicalPersons;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_GITHUBPROFILE_AMY = "amy-bee";
    public static final String VALID_GITHUBPROFILE_BOB = "bob-choo";
    public static final String VALID_LANGUAGE_PYTHON = "Python";
    public static final String VALID_LANGUAGE_CPLUSPLUS = "C++";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PROFILE_DESC_AMY = " " + PREFIX_PROFILE + VALID_GITHUBPROFILE_AMY;
    public static final String PROFILE_DESC_BOB = " " + PREFIX_PROFILE + VALID_GITHUBPROFILE_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String LANGUAGE_DESC_PYTHON = " " + PREFIX_LANGUAGE + VALID_LANGUAGE_PYTHON;
    public static final String LANGUAGE_DESC_CPLUSPLUS = " " + PREFIX_LANGUAGE + VALID_LANGUAGE_CPLUSPLUS;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PROFILE_DESC = " " + PREFIX_PROFILE + "james^"; // '^' not allowed in profile
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + " "; // " " not allowed for addresses
    public static final String INVALID_LANGUAGE_DESC = " " + PREFIX_LANGUAGE + "C^"; // '^' not allowed in languages
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor EDIT_DESC_AMY;
    public static final EditCommand.EditPersonDescriptor EDIT_DESC_BOB;
    public static final RemoveCommand.RemovePersonDescriptor REMOVE_DESC_AMY;
    public static final RemoveCommand.RemovePersonDescriptor REMOVE_DESC_BOB;

    static {
        EDIT_DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withProfile(VALID_GITHUBPROFILE_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withLanguages(VALID_LANGUAGE_PYTHON)
                .withTags(VALID_TAG_FRIEND).build();
        EDIT_DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withProfile(VALID_GITHUBPROFILE_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withLanguages(VALID_LANGUAGE_CPLUSPLUS)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        REMOVE_DESC_AMY = new RemovePersonDescriptorBuilder()
                .withPerson(TypicalPersons.AMY)
                .withProfile(VALID_GITHUBPROFILE_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withLanguages(VALID_LANGUAGE_PYTHON)
                .withTags(VALID_TAG_FRIEND).build();
        REMOVE_DESC_BOB = new RemovePersonDescriptorBuilder()
                .withPerson(TypicalPersons.BOB)
                .withProfile(VALID_GITHUBPROFILE_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withLanguages(VALID_LANGUAGE_CPLUSPLUS)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the {@code Socket}, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Socket expectedSocket = new Socket(actualModel.getSocket());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedSocket, actualModel.getSocket());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s {@code Socket}.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Removes the person at index 1 in {@code model}'s filtered list from the {@code model}'s {@code Socket}.
     */
    public static void deletePersonIndexOne(Model model) {
        Index indexOne = Index.fromOneBased(1);
        Person person = model.getFilteredPersonList().get(indexOne.getZeroBased());
        model.deletePerson(person);
        model.commitSocket();
    }
}
