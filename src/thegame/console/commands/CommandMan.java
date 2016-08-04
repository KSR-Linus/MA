package thegame.console.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import thegame.Main;
import thegame.console.ACommand;
import thegame.console.Command;

@Command(name = "man", modName = "base")
public class CommandMan extends ACommand {

	@Override
	public void run(String[] args) {
		if (args.length != 1) {
			Main.getConsole().print("Usage: /man commandname");
			return;
		}
		try {
			BufferedReader r = new BufferedReader(new FileReader(new File("assets/man/" + args[0] + ".man")));
			String s;
			while ((s = r.readLine()) != null) {
				Main.getConsole().print(s);
			}
			r.close();
		} catch (IOException e) {
			Main.getConsole().print("No manual entry for '" + args[0] + "' found.");
		}
	}
	
}
