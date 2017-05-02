package io.github.densyakun.bve5routeeditor.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import io.github.densyakun.bve5routeeditor.common.linear.Linear;
import io.github.densyakun.bve5routeeditor.common.linear.Vector2D;
import processing.core.PApplet;

public class MySketch extends PApplet {

	static final float zs = 200f;

	float x = 0.0f;
	float y = 0.0f;
	float zoom = 0.0f;
	float fov = 90f;
	Color bg = Color.red;
	boolean stopping = false;
	float rx = radians(-30);
	float ry = 0.0f;
	float min_distance = 0.0f;
	float max_distance = 0.0f;
	List<Linear> linears = new ArrayList<Linear>();

	//-注意-
	//YとZが反転している。
	public MySketch() {
		Client._3d_view = this;
	}

	public void settings() {
		size(640, 480, P3D);
	}

	public void setup() {
		surface.setResizable(false);
		//TODO ウィンドウを最大化出来ないようにする
	}

	//TODO 距離程目盛り
	public void draw() {
		float[] a = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), new float[3]);
		bg = Color.getHSBColor(a[0] + 1.0f / 360 / 4, a[1], a[2]);

		background(bg.getRed(), bg.getGreen(), bg.getBlue());

		/*stroke(255);
		strokeWeight(0f);
		sphere(Integer.MAX_VALUE);
		rect(24, 96, 24, 96);*/

		/*if (mousePressed) {
			lights();
		}*/

		pushMatrix();

		perspective(fov * PI / 180f, width / height, 3200.0f, 0.0f);
		translate(width / 2 - x, height / 2 - y, zoom);
		//ry += (float) frameCount / 360;
		rotateX(-radians(rx));
		rotateY(-radians(ry));

		//stroke(1);
		//strokeWeight(0);
		//box(100);

		strokeWeight(0.8f);
		stroke(255, 0, 0);
		line(0, 0, 0, Integer.MIN_VALUE, 0, 0);
		stroke(0, 255, 0);
		line(0, 0, 0, 0, Integer.MIN_VALUE, 0);
		stroke(0, 0, 255);
		line(0, 0, 0, 0, 0, Integer.MIN_VALUE);
		strokeWeight(0.5f);
		stroke(128, 0, 0);
		line(0, 0, 0, Integer.MAX_VALUE, 0, 0);
		stroke(0, 128, 0);
		line(0, 0, 0, 0, Integer.MAX_VALUE, 0);
		stroke(0, 0, 128);
		line(0, 0, 0, 0, 0, Integer.MAX_VALUE);

		stroke(0);
		strokeWeight(1f);

		for (int b = 0; b < linears.size(); b++) {
			Linear c = linears.get(b);
			HashMap<Double, Double> d = c.getCurvePoints();
			if (d.size() == 0) {
				line(0, 0, Integer.MAX_VALUE, 0, 0, Integer.MIN_VALUE);
			} else {
				Double[] e = d.keySet().toArray(new Double[0]);
				Vector2D h = c.getPosition(e[0]);
				line(0, 0, Integer.MAX_VALUE, 0, 0, -new Float(Math.max(min_distance, h.y)));
				Vector2D k = new Vector2D();
				for (int i = 1; i < e.length; i++) {
					k = c.getPosition(e[i]);
					line(new Float(h.x), 0, -new Float(h.y), new Float(k.x), 0, -new Float(k.y));
					h = k;
				}
				Vector2D l = new Vector2D(0.0, 100000.0).rotate(k.rad()).add(h);
				line(new Float(k.x), 0, -new Float(k.y), new Float(l.x), 0, -new Float(l.y));
			}
		}

		/*Vector2D zzz = new Vector2D(0, 10).add(new Vector2D(0, 10).rotate(PI * frameCount / 60));
		line(0, 0, -10, new Float(zzz.x), 0, -new Float(zzz.y));*/

		strokeWeight(3f);
		point(0, 0, 0);

		//noFill();

		/*beginShape();
		vertex(-100f, 0f, 0f);
		vertex(0f, 0f, -100f);
		vertex(100f, 0f, 0f);
		endShape();*/

		popMatrix();

		stroke(0);
		textSize(24f);
		textAlign(CENTER);
		text("zoom: " + zoom, width / 2, height - 2);//TODO 3D要素よりも手前に表示させたい
	}

	public void keyPressed() {
		if (keyCode == 98) {//F2
			String a;
			for (int b = 0; sketchFile(a = "screenshot/" + b + ".png").exists(); b++);
			saveFrame(a);
		} else if (key == ESC) {
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
		} else if (keyCode == UP) {
			zoom = Math.min(0, zs * Math.min(-1, zoom / 2) / zs);
		} else if (keyCode == DOWN) {
			zoom -= zs * Math.max(1, abs(zoom) / zs);
		}
	}

	public void mouseDragged() {
		if (key == SHIFT) {
			x += mouseX - dmouseX;
			y += mouseY - dmouseY;
		} else {
			rx += mouseY - dmouseY;
			ry -= mouseX - dmouseX;
		}
	}

	public void mapreload() {
		min_distance = Float.MAX_VALUE;
		max_distance = 0.0f;
		linears.clear();
		if (Client.map != null) {
			linears.add(Client.map.getOwn_Linear());
			for (int a = 0; a < linears.size(); a++) {
				HashMap<Double, Double> b = linears.get(a).getCurvePoints();
				Double[] c = b.keySet().toArray(new Double[0]);
				for (int d = 0; d < c.length; d++) {
					if (c[d] < min_distance) {
						min_distance = new Float(c[d]);
					}
					if (c[d] > max_distance) {
						max_distance = new Float(c[d]);
					}
				}
			}
		}
		min_distance += Math.min(0.0f, min_distance);
		System.out.println("min: " + min_distance);
		System.out.println("max: " + max_distance);
	}

}
