package thegame.console;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.reflections.Reflections;

import thegame.console.commands.CommandFill;

public class Console {
	
	public ConcurrentHashMap<String, ACommand> cmds = new ConcurrentHashMap<String, ACommand>();
	
	public Console() {
		Reflections  ref = new Reflections("thegame.console.commands");
		Set<Class<?>> classes = ref.getTypesAnnotatedWith(Command.class);
		for (Class<?> c : classes) {
			try {
				ACommand cmd = (ACommand) c.newInstance();
				cmds.put(c.getAnnotation(Command.class).name(), cmd);
				System.out.println("Registered Command: " + c.getAnnotation(Command.class).name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void print(String str) {
		System.out.println(str);
	}
	
	public void executeCommand(String in) {
		if (in.startsWith("/")) {
			in = in.split("/")[1];
			if (cmds.containsKey(in.split(" ")[0])) {
				cmds.get(in.split(" ")[0]).run(Arrays.copyOfRange(in.split(" "), 1, in.split(" ").length));
			} else {
				print("Unknown Command: " + in.split(" ")[0]);
			}
		}
	}
	
}
