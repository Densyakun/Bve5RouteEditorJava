package io.github.densyakun.bve5routeeditor.client;

import java.awt.Color;

import javax.swing.JOptionPane;

import processing.core.PApplet;

public class MySketch extends PApplet {

	Color bg = Color.red;
	boolean stopping = false;
	float rx = 0f;
	float ry = 0f;
	float min_distance = 0.0f;

	public MySketch() {
		Client._3d_view = this;
	}

	public void settings() {
		size(400, 300, P3D);
	}

	public void setup() {
		surface.setResizable(false);
	}

	public void draw() {
		float[] a = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), new float[3]);
		bg = Color.getHSBColor(a[0] + 1.0f / 360 / 4, a[1], a[2]);

		background(bg.getRed(), bg.getGreen(), bg.getBlue());

		//rect(24, 96, 24, 96);

		lights();
		//translate(width / 2, height / 2, -100);
		translate(width / 2, height / 2, -100);
		float angle = (float) frameCount / 360;
		rotateX(radians(-60));
		rotateY(-angle);

		//stroke(1);
		//strokeWeight(0);
		//box(100);

		stroke(128, 128);
		strokeWeight(0.5f);
		line(0, 0, 0, -Integer.MAX_VALUE, 0, 0);
		line(0, 0, 0, 0, Integer.MAX_VALUE, 0);
		line(0, 0, 0, 0, 0, Integer.MAX_VALUE);
		strokeWeight(1f);
		line(0, 0, 0, Integer.MAX_VALUE, 0, 0);
		line(0, 0, 0, 0, -Integer.MAX_VALUE, 0);
		line(0, 0, 0, 0, 0, -Integer.MAX_VALUE);

		stroke(0);
		strokeWeight(2f);
		line(0, 0, -min_distance, 0, 0, -Integer.MAX_VALUE);

		strokeWeight(3);
		point(0, 0, 0);

		//rect(24, 96, 24, 96);
	}

	public void keyPressed() {
		super.keyPressed();
		if (key == ESC) {
			if (stopping) {
				key = 0;
			} else {
				int a = JOptionPane.showConfirmDialog(null, "保存されていない情報は破棄されます。", "確認", JOptionPane.OK_CANCEL_OPTION);
				if (a == JOptionPane.OK_OPTION) {
					stopping = true;
				} else if (a == JOptionPane.CANCEL_OPTION) {
					key = 0;
				}
			}
		}
	}

	public void mapreload() {
		min_distance = 0.0f;
		if (Client.map != null) {
			//TODO
			//for (int a = 0; a < Client.map)
		}
	}

}
