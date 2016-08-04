package thegame.console.commands;

import thegame.Main;
import thegame.block.BlockJME;
import thegame.console.ACommand;
import thegame.console.Command;


@Command(name = "place", modName = "base")
public class CommandPlace extends ACommand {

	@Override
	public void run(String[] args) {
		if (args.length == 4) {
			if (args[3].equals("air")) {
				Main.getWorld().destroyAt(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			} else {
				Main.getWorld().placeBlockAt(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Main.blockReg.get(args[3]).clone());
			}
		} else {
			Main.getConsole().print("Usage: /place x y z block");
		}
	}

}
