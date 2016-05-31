package thegame.item;

import thegame.Main;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Image;

public abstract class Item {
	
	Node geom;
	
	private long last = 0;
	
	public float x, y, z;
	
	public abstract void onCreated();
	
	public void onPlaced(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		geom.setLocalTranslation(x, y, z);
		Main.getInstance().getRootNode().attachChild(geom);
		Main.getInstance().addItem(this);
	}
	
	public void onPickedUp() {
		Main.getInstance().getRootNode().detachChild(geom);
	}
	
	public abstract void onDestroyed(float x, float y, float z);
	
	public static Node getGeometry(Image img) {
		Node tmp = new Node();
		int x = img.getWidth(), y = img.getHeight();
		float sx = 0.1f/x, sy = 0.1f/y;
		for (int a = 0; a < x; a++) {
			for (int b = 0; b < y; b++) {
				Geometry g = new Geometry("pixel", new Box(sx, sy, Math.min(sx, sy)));
				Material m = new Material(Main.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
				m.setColor("Color", new ColorRGBA((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random()));
				g.setMaterial(m);
				tmp.attachChild(g);
				g.setLocalTranslation(sx*a*2-sx*x, sy*b*2-sy*y, 0);
			}
		}
		return tmp;
	}
	
	public void update() {
		geom.rotate(0, 0.02f, 0);
	}
	
}
