package thegame.console;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	
	public String modName();
	public String name();
	
}
