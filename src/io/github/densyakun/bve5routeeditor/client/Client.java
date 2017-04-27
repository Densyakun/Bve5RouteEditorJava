package io.github.densyakun.bve5routeeditor.client;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.densyakun.bve5routeeditor.common.RouteMap;
import io.github.densyakun.bve5routeeditor.common.RouteMapStatement;
import io.github.densyakun.bve5routeeditor.common.Scenario;
import processing.core.PApplet;

public class Client {

	/**
	 * クライアントのバージョン。
	 */
	public static final String VERSION = "0.001alpha";

	public static MySketch _3d_view;

	/**
	 * 開いているシナリオデータ。
	 */
	public static Scenario scenario;

	/**
	 * 開いているマップデータ。
	 */
	public static RouteMap map;

	public static void main(String[] args) {
		System.out.println("ClientVer: " + VERSION);

		PApplet.main(new String[] { "--location=100,100", MySketch.class.getName() });

		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
		filechooser.setDialogTitle("シナリオファイルを開く");

		int selected = filechooser.showOpenDialog(null);
		if (selected == JFileChooser.APPROVE_OPTION){
			try {
				scenario = Scenario.read(filechooser.getSelectedFile());

				System.out.println("ScenarioTitle: " + scenario.getTitle());
				File mapfile = scenario.getRandomRoute();
				if (mapfile != null) {
					map = RouteMap.read(mapfile);
					mapreload();
					int a = 0;
					Iterator<Double> b = map.getStatements().keySet().iterator();
					while (b.hasNext()) {
						Iterator<RouteMapStatement> c = map.getStatements().get(b.next()).iterator();
						while (c.hasNext()) {
							c.next();
							a++;
						}
					}
					System.out.println("Statements(" + a + "): " + map.getStatements());

					/*(filechooser = new JFileChooser()).setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
					filechooser.setDialogTitle("シナリオファイルを保存");

					if ((selected = filechooser.showSaveDialog(null)) == JFileChooser.APPROVE_OPTION) {
						try {
							Scenario.write(scenario, filechooser.getSelectedFile());
							System.out.println("保存しました");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}*/

					(filechooser = new JFileChooser()).setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
					filechooser.setDialogTitle("マップファイルを保存");

					if ((selected = filechooser.showSaveDialog(null)) == JFileChooser.APPROVE_OPTION) {
						try {
							RouteMap.write(map, filechooser.getSelectedFile());
							System.out.println("保存しました");
						} catch (IOException e) {
							e.printStackTrace();//TODO
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();//TODO
			}
		}
	}

	public static void mapreload() {
		_3d_view.mapreload();
	}

}
