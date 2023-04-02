package classes.commands;

import classes.NamedCommand;
import interfaces.Commandable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Help extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tвывести справку по доступным командам";
    }

    @Override
    public String execute(String... args) {
        Reflections reflections = new Reflections("classes.commands");
        Set<Class<? extends Commandable>> allCommands = reflections.getSubTypesOf(Commandable.class);
        String output = "";
        for (Class<? extends Commandable> command : allCommands) {
            try {
                output += command.getDeclaredConstructor().newInstance().getInfo() + "\n";
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                     NoSuchMethodException e) {
                return new RuntimeException(e).getMessage();
            }
        }
        return output;
    }


}
