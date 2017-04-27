package io.github.densyakun.bve5routeeditor.common.linear;

import java.io.Serializable;

public class Vector2D implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final Vector2D ZERO = new Vector2D();
	public static final Vector2D LEFT = new Vector2D(-1.0, 0.0);
	public static final Vector2D RIGHT = new Vector2D(1.0, 0.0);
	public static final Vector2D UP = new Vector2D(0.0, -1.0);
	public static final Vector2D DOWN = new Vector2D(0.0, 1.0);

	public double x;
	public double y;

	public Vector2D() {
		x = 0.0;
		y = 0.0;
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Vector2D vec) {
		x = vec.x;
		y = vec.y;
	}

	public Vector2D add(Vector2D vec) {
		Vector2D v = new Vector2D(this);
		v.x += vec.x;
		v.y += vec.y;
		return v;
	}

	public Vector2D subtract(Vector2D vec) {
		Vector2D v = new Vector2D(this);
		v.x -= vec.x;
		v.y -= vec.y;
		return v;
	}

	public Vector2D multiply(double arg0) {
		Vector2D v = new Vector2D(this);
		v.x *= arg0;
		v.y *= arg0;
		return v;
	}

	public Vector2D divide(double arg0) {
		Vector2D v = new Vector2D(this);
		v.x /= arg0;
		v.y /= arg0;
		return v;
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2D normalize() {
		return divide(length());
	}

	public double rad() {
		return rad(this, RIGHT);
	}

	public Vector2D rotate(double rad) {
		Vector2D v = new Vector2D(this);
		double d = rad();
		double length = length();
		v.x = Math.cos(d + rad) * length;
		v.y = Math.sin(d + rad) * length;
		return v;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Vector2D(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vector2D && ((Vector2D) obj).x == x && ((Vector2D) obj).y == y;
	}

	public static double dot(Vector2D v1, Vector2D v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	public static double cross(Vector2D v1, Vector2D v2) {
		return v1.x * v2.y - v2.x * v1.y;
	}

	public static double rad(Vector2D v1, Vector2D v2) {
		return dot(v1, v2) / v1.length() * v2.length();
	}

}
