package io.github.densyakun.bve5routeeditor.client;

import java.awt.Color;

import processing.core.PApplet;

public class MySketch extends PApplet {

	Color bg = Color.red;

	public MySketch() {
		Client._3d_view = this;
	}

	public void settings() {
		size(400, 300, P3D);
	}

	public void setup() {
	}

	public void draw() {
		float[] a = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), new float[3]);
		bg = Color.getHSBColor(a[0] + 1.0f / 360 / 4, a[1], a[2]);

		background(bg.getRed(), bg.getGreen(), bg.getBlue());

		translate(width / 2, height / 2, -100);
		float angle = (float) frameCount / 360;
		rotateX(angle);
		rotateY(angle);
		box(200);
	}

}
