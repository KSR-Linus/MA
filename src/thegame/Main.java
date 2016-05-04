package thegame;

import thegame.util.DeltaTime;
import thegame.util.State;
import thegame.world.World;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;

public class Main extends SimpleApplication {

	private static Main instance;
	private static World world;

	private long nextChunkUpdate = 0;

	public static State state = State.STARTUP;

	public static boolean isRunning = true;

	public Main() {
		start();
	}

	@Override
	public void simpleInitApp() {
		world = new World();
		world.generate();
		flyCam.setMoveSpeed(25f);
		getCamera().setLocation(new Vector3f(0, 5, 0));
		inputManager.addMapping("leftmouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("rightmouse", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
		inputManager.addListener(l, "leftmouse");
		initCrossHairs();
	}

	protected void initCrossHairs() {
		setDisplayStatView(false);
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(
		settings.getWidth() / 2 - ch.getLineWidth() / 2,
		settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}

	@Override
	public void update() {
		super.update();
		if (System.currentTimeMillis() >= nextChunkUpdate) chunkUpdate();
		world.updateBlockSelection2(getCamera(), rootNode);
	}

	ActionListener l = new ActionListener() {

		@Override
		public void onAction(String name, boolean arg1, float arg2) {
			switch (name) {
			case "leftmouse":
				world.destroySelected();
				break;
			case "rightmouse":
				world.interactWithSelected();
				break;
			}
		}

	};

	private void chunkUpdate() {
		nextChunkUpdate = DeltaTime.getTargetTime(500);
		world.update();
	}

	public static void main(String[] args) {
		instance = new Main();
	}

	public static Main getInstance() {
		return instance;
	}

	public static World getWorld() {
		return world;
	}
}
