package thegame.item;

import thegame.Main;

import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;

public class ItemJME extends Item {

	public ItemJME() {
		geom = Item.getGeometry(new Image(Format.Depth, 16, 16, BufferUtils.createByteBuffer(16*16*4)));
	}
	
	@Override
	public void onCreated() {
		Main.getInstance().getRootNode().attachChild(geom);
		geom.setLocalTranslation(x, y, z);
	}
	
	@Override
	public void onPickedUp() {
		super.onPickedUp();
		System.out.println("You picked me up at: " + x + ", " + y + ", " + z + ".");
	}

	@Override
	public void onDestroyed(float x, float y, float z) {
		if (Main.getInstance().getRootNode().hasChild(geom))
			Main.getInstance().getRootNode().detachChild(geom);
	}

}
