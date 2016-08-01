package thegame.console.commands;

import thegame.console.ACommand;
import thegame.console.Command;

@Command(name = "blah ble bli blo blu", modName = "mod1")
public class klajsdlfkjawefaksdbf extends ACommand {
	
	public klajsdlfkjawefaksdbf() {
	}

	@Override
	public void run(String[] args) {
		System.out.println("Test command");
	}

}
