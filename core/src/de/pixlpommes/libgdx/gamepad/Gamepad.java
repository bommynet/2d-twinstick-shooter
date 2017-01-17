package de.pixlpommes.libgdx.gamepad;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.pixlpommes.libgdx.gamepad.mappings.LogitechPad;

/**
 * @author Thomas Borck
 *
 */
public class Gamepad implements ControllerListener {
	
	private final Controller _controller;
	
	private static final float SENSITIVITY = 0.05f;
	
	private float _leftAxisX = 0f,
	              _leftAxisY = 0f;
	
	private float _rightAxisX = 0f,
	              _rightAxisY = 0f;
	
	private boolean _buttonLT = false,
	                _buttonRT = false;
	
	public Gamepad(Controller controller) {
		_controller = controller;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#connected(com.badlogic.gdx.controllers.Controller)
	 */
	@Override
	public void connected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#disconnected(com.badlogic.gdx.controllers.Controller)
	 */
	@Override
	public void disconnected(Controller controller) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#buttonDown(com.badlogic.gdx.controllers.Controller, int)
	 */
	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {// only update data if this is the current players controller
		if(_controller != controller) return false;
		
		// update all values TODO: do it better
		_buttonLT = controller.getButton(LogitechPad.BUTTON_LB);
		_buttonRT = controller.getButton(LogitechPad.BUTTON_RB);
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#buttonUp(com.badlogic.gdx.controllers.Controller, int)
	 */
	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#axisMoved(com.badlogic.gdx.controllers.Controller, int, float)
	 */
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// only update data if this is the current players controller
		if(_controller != controller) return false;
		
		// get changed value...
		float newValue = controller.getAxis(axisCode);
		
		// ...check if change is noticeable...
		if(Math.abs(newValue) < SENSITIVITY) newValue = 0;
		
		// ...and map it to the correct axis data
		switch(axisCode) {
			case LogitechPad.AXIS_LEFT_X:
				_leftAxisX = newValue;
				break;
	
			case LogitechPad.AXIS_LEFT_Y:
				_leftAxisY = newValue * -1;
				break;
	
			case LogitechPad.AXIS_RIGHT_X:
				_rightAxisX = newValue;
				break;
	
			case LogitechPad.AXIS_RIGHT_Y:
				_rightAxisY = newValue * -1;
				break;
			
			default:
				// nothing to do
				break;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#povMoved(com.badlogic.gdx.controllers.Controller, int, com.badlogic.gdx.controllers.PovDirection)
	 */
	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#xSliderMoved(com.badlogic.gdx.controllers.Controller, int, boolean)
	 */
	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#ySliderMoved(com.badlogic.gdx.controllers.Controller, int, boolean)
	 */
	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.controllers.ControllerListener#accelerometerMoved(com.badlogic.gdx.controllers.Controller, int, com.badlogic.gdx.math.Vector3)
	 */
	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vector2 getLeftStick() {
		return new Vector2(_leftAxisX, _leftAxisY);
	}

	public Vector2 getRightStick() {
		return new Vector2(_rightAxisX, _rightAxisY);
	}
	
	public boolean getLT() {
		return _buttonLT;
	}
	
	public boolean getRT() {
		return _buttonRT;
	}
}
