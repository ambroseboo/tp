package sweebook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import sweebook.commons.core.Messages;
import sweebook.commons.core.index.Index;
import sweebook.logic.commands.exceptions.CommandException;
import sweebook.model.Model;
import sweebook.model.task.Task;

public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "deleteTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes task at specified index. "
        + "Parameters: "
        + "INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " "
        + "1";

    public static final String MESSAGE_SUCCESS = "Noted! I've removed this task:\n\t %1$s\n\t";

    private final Index targetIndex;

    /**
     * Creates an DeleteTaskCommand to add the specified {@code Task}
     */
    public DeleteTaskCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> taskList = model.getTasks();

        if (targetIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }

        Task toDelete = taskList.get(targetIndex.getZeroBased());
        model.deleteTask(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.toString()));
    }
}