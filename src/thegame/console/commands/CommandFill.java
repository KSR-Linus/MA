package thegame.console.commands;

import thegame.console.ACommand;
import thegame.console.Command;
import thegame.console.Console;
import thegame.Main;


@Command(modName = "base", name = "fill")
public class CommandFill extends ACommand {

	Console c = Main.getConsole();
	
	public CommandFill() {
		
	}
	
	@Override
	public void run(String[] args) {
		switch (args.length) {
		case 3:
			String[] p1 = args[0].split(",");
			String[] p2 = args[1].split(",");
			if (p1.length != 3 || p2.length != 3) {
				c.print("Coordinates invalid");
				break;
			}
			int[] pos1 = new int[3];
			int[] pos2 = new int[3];
			try {
				pos1[0] = Integer.parseInt(p1[0]);
				pos1[1] = Integer.parseInt(p1[1]);
				pos1[2] = Integer.parseInt(p1[2]);
				
				pos2[0] = Integer.parseInt(p2[0]);
				pos2[1] = Integer.parseInt(p2[1]);
				pos2[2] = Integer.parseInt(p2[2]);
			} catch (NumberFormatException e) {
				c.print("Coordinates must be numbers");
			}
			break;
		case 4:
			
			break;
		default: 
			if (c != null) c.print("Usage: /fill x1,y1,z1 x2,y2,z2 block | -b x,y,z width,length,height block");
			else System.out.println("Usage: /fill x1,y1,z1 x2,y2,z2 block | -b x,y,z width,length,height block");
		}
	}
	
}
