package de.pixlpommes.libgdx.gamepad.mappings;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;

/**
 * <p>Gamepad mappings for XBox Controllers.</p>
 * 
 * <p> * It seems there are different versions of gamepads with
 * different ID Strings. Therefore its better to check it via
 * <code>isCorrectMappingFor(Controller)</code>.</p>
 * 
 * <p>Supported controllers:
 * <ul>
 * <li>Controller (Gamepad for Xbox 360)</li>
 * <li>Controller (XBOX 360 For Windows)</li>
 * <li>Controller (Xbox 360 Wireless Receiver for Windows)</li>
 * <li>Controller (Xbox wireless receiver for windows)</li>
 * <li>XBOX 360 For Windows (Controller)</li>
 * <li>Xbox 360 Wireless Receiver</li>
 * <li>Xbox Receiver for Windows (Wireless Controller)</li>
 * <li>Xbox wireless receiver for windows (Controller)</li>
 * </ul>
 * 
 * @author <a href="http://www.java-gaming.org/index.php?PHPSESSID=p7vb6po0qi6ni0d6atka4aiep4&topic=29223.msg268004#msg268004">Cero</a>
 */
public class XBox360Pad {
	public static final int BUTTON_X = 2;
	public static final int BUTTON_Y = 3;
	public static final int BUTTON_A = 0;
	public static final int BUTTON_B = 1;
	public static final int BUTTON_BACK = 6;
	public static final int BUTTON_START = 7;
	public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
	public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
	public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
	public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
	public static final int BUTTON_LB = 4;
	public static final int BUTTON_L3 = 8;
	public static final int BUTTON_RB = 5;
	public static final int BUTTON_R3 = 9;
	public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
	public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
	public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
	public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
	public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
	public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
	
	/**
	 * @param controller
	 * @return
	 */
	public static boolean isCorrectMappingFor(Controller controller) {
		return controller.getName().toLowerCase().contains("xbox")
				&& controller.getName().contains("360");
	}
}