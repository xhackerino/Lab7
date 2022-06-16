package commands.base;

public final class CommandResult {
    private final String[] input;
    private final String result;

    public CommandResult(String result, String ... input) {
        this.input = input;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String[] getInput() {
        return input;
    }

}
