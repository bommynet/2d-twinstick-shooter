package de.pixlpommes.games.wipe.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Bullets {

	private List<Vector2> _bullets = new ArrayList<Vector2>();
	private List<Vector2> _bulletsSpeed = new ArrayList<Vector2>();
	
	public void update(float delta) {
		for(int i = 0; i < _bullets.size(); i++) {
			_bullets.get(i).add(_bulletsSpeed.get(i).cpy().scl(delta));
		}
	}
	
	public void draw(Batch batch) {
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Filled);
		
		sr.setColor(Color.GREEN);
		for(Vector2 b : _bullets) {
			sr.circle(b.x, b.y, 5);
		}
		
		sr.end();
		sr.dispose();
	}
	
	public void add(Vector2 pos, Vector2 speed) {
		_bullets.add(pos);
		_bulletsSpeed.add(speed);
	}
	
	public Vector2 get(int index) {
		return _bullets.get(index);
	}
	
	public int size() {
		return _bullets.size();
	}
}
