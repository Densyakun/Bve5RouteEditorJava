package io.github.densyakun.bve5routeeditor.client;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.densyakun.bve5routeeditor.common.RouteMap;
import io.github.densyakun.bve5routeeditor.common.Scenario;
import processing.core.PApplet;

public class Client {

	/**
	 * クライアントのバージョン。
	 */
	public static final String VERSION = "0.001alpha";

	public static MySketch _3d_view;
	public static Scenario scenario;
	public static RouteMap map;

	public static void main(String[] args) {
		System.out.println("ClientVer: " + VERSION);

		PApplet.main(new String[] { "--location=100,100", MySketch.class.getName() });

		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
		filechooser.setDialogTitle("シナリオファイルを開く");

		int selected = filechooser.showOpenDialog(_3d_view.frame);
		if (selected == JFileChooser.APPROVE_OPTION){
			try {
				scenario = Scenario.read(filechooser.getSelectedFile());

				System.out.println("ScenarioTitle: " + scenario.getTitle());
				map = RouteMap.read(scenario.getRandomRoute());
				System.out.println("Comment_prefix: " + map.getComment_prefix());
				System.out.println("CommentOut: " + map.getCommentOut());

				/*(filechooser = new JFileChooser()).setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
				filechooser.setDialogTitle("シナリオファイルを保存");

				if ((selected = filechooser.showSaveDialog(_3d_view.frame)) == JFileChooser.APPROVE_OPTION){
					try {
						Scenario.write(scenario, filechooser.getSelectedFile());
						System.out.println("保存しました");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
