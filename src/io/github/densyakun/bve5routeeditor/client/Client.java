package io.github.densyakun.bve5routeeditor.client;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.github.densyakun.bve5routeeditor.common.Scenario;
import processing.core.PApplet;

public class Client {

	/**
	 * クライアントのバージョン。
	 */
	public static final String VERSION = "0.001alpha";

	public static MySketch _3d_view;
	public static Scenario scenario;

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

				System.out.println("RoutePath(Random): " + scenario.getRandomRoute());
				System.out.println("VehiclePath(Random): " + scenario.getRandomVehicle());
				System.out.println("Title: " + scenario.getTitle());
				System.out.println("Image: " + scenario.getImage());
				System.out.println("RouteTitle: " + scenario.getRouteTitle());
				System.out.println("VehicleTitle: " + scenario.getVehicleTitle());
				System.out.println("Author: " + scenario.getAuthor());
				System.out.println("Comment: " + scenario.getComment());
				System.out.println("CommentOut: " + scenario.getCommentOut());
				System.out.println("Comment_prefix: " + scenario.getComment_prefix());

				(filechooser = new JFileChooser()).setFileFilter(new FileNameExtensionFilter("txtファイル", "txt"));
				filechooser.setDialogTitle("シナリオファイルを保存");

				if ((selected = filechooser.showSaveDialog(_3d_view.frame)) == JFileChooser.APPROVE_OPTION){
					try {
						Scenario.write(scenario, filechooser.getSelectedFile());
						System.out.println("保存しました");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
