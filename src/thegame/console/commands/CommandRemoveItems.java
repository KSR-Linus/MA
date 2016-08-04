package thegame.console.commands;

import java.util.concurrent.ConcurrentHashMap;

import thegame.Main;
import thegame.console.ACommand;
import thegame.console.Command;
import thegame.item.Item;


@Command(name = "removeitem", modName = "base")
public class CommandRemoveItems extends ACommand {

	@Override
	public void run(String[] args) {
		ConcurrentHashMap<Item, Byte> map = Main.getInstance().items;
		for (Item i : map.keySet()) {
			i.destroy();
			Main.getInstance().items.remove(i);
		}
	}
	
}
