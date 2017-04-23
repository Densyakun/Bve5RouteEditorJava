package io.github.densyakun.bve5routeeditor.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * BveTs Map 2.02
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public class RouteMap implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 対応するマップファイルの最新バージョン
	 */
	public static final String VERSION = "2.02";
	public static final String HEADER = "BveTs Map " + VERSION;
	public static final String[] _COMMENT_PREFIX = new String[]{"#", "//"};
	public static final String SUFFIX = ";";

	//File mapfile;
	//boolean loaded = false;
	HashMap<Double, String> commentout = new HashMap<Double, String>();
	String comment_prefix = _COMMENT_PREFIX[0];

	/*public RouteMap(File mapfile) {
		setMapfile(mapfile);
	}*/

	/**
	 * マップファイルを取得します。
	 * @return マップファイル
	 */
	/*public File getMapfile() {
		return mapfile;
	}*/

	/**
	 * マップファイルを設定します。
	 * @param mapfile マップファイル
	 */
	/*public void setMapfile(File mapfile) {
		this.mapfile = mapfile;
	}*/

	/**
	 * マップファイル読み込み時に確認されたすべてのコメントアウト文字列を取得します。
	 * @return 距離程(キー)とコメントアウト文字列(値)
	 */
	public HashMap<Double, String> getCommentOut() {
		return commentout;
	}

	/**
	 * コメントアウト文字列を設定します。
	 * @param commentout 距離程(キー)とコメントアウト文字列(値)
	 */
	public void setCommentOut(HashMap<Double, String> commentout) {
		this.commentout = commentout;
	}

	/**
	 * コメントアウトの接頭辞を取得します。マップファイル読み込み時には自動的に一番使用された接頭辞が設定されています。
	 * @return 接頭辞の文字列
	 */
	public String getComment_prefix() {
		return comment_prefix;
	}

	/**
	 * コメントアウトの接頭辞を設定します。
	 * @param comment_prefix 接頭辞の文字列
	 */
	public void setComment_prefix(String comment_prefix) {
		this.comment_prefix = comment_prefix;
	}

	/**
	 * マップファイルを読み込みます。
	 * @param file ファイル
	 * @return マップデータ
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static RouteMap read(File file) throws IOException {
		//ファイルが読み込み可能か、使用可能なバージョンかは確認していない。



		Charset cs = Scenario.DEFAULT_CHARSET;

		FileInputStream fis = new FileInputStream(file);
		UniversalDetector detector = new UniversalDetector(null);

		byte[] buf = new byte[4096];
		int nread;
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}

		detector.dataEnd();

		String encoding = detector.getDetectedCharset();
		if (encoding != null) {
			cs = Charset.forName(encoding);
		}

		fis.close();



		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), cs));

		//RouteMap routemap = new RouteMap(file);
		RouteMap routemap = new RouteMap();//TODO map
		//HashMap<RouteMap, Double> routes = new HashMap<RouteMap, Double>();
		//HashMap<File, Double> vehicles = new HashMap<File, Double>();
		HashMap<Double, String> commentout = new HashMap<Double, String>();



		int[] a = new int[_COMMENT_PREFIX.length];
		for (int b = 0; b < a.length; b++) {
			a[b] = 0;
		}

		double distance = 0.0;//TODO

		String str;
		for (int b = 0; (str = br.readLine()) != null; b++) {
			if (!str.equals(BVE5RouteEditor.HEADER_BVE5RE)) {
				for (int c = 0; c < _COMMENT_PREFIX.length; c++) {
					int d = str.indexOf(_COMMENT_PREFIX[c]);
					if (d != -1) {
						a[c]++;
						String value = commentout.get(distance);
						if (value == null) {
							value = "";
						} else {
							value += System.getProperty("line.separator");
						}
						commentout.put(distance, value + str.substring(d + 1));
						str = str.substring(0, d).trim();
						break;
					}
				}

				if (b != 0) {
					String[] z = str.split(SUFFIX);//TODO c
					for (int d = 0; d < z.length; d++) {
						z[d] = z[d].trim();
						try {
							distance = Double.valueOf(z[d]);
						} catch (NumberFormatException e) {



							/*if (str.startsWith(KEY_ROUTE_TITLE)) {
								int c = str.indexOf('=', KEY_ROUTE_TITLE.length());
								if (c != -1) {
									routemap.setRouteTitle(str.substring(c + 1).trim());
								}
							} else if (str.startsWith(KEY_VEHICLE_TITLE)) {
								int c = str.indexOf('=', KEY_VEHICLE_TITLE.length());
								if (c != -1) {
									routemap.setVehicleTitle(str.substring(c + 1).trim());
								}
							} else if (str.startsWith(KEY_ROUTE)) {
								int c = str.indexOf('=', KEY_ROUTE.length());
								if (c != -1) {
									String[] d = str.substring(c + 1).split("[*|]");
									RouteMap e = null;
									for (int f = 0; f < d.length; f++) {
										try {
											double g = Double.valueOf(d[f].trim());
											if (e == null) {
												System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
											} else {
												routes.put(e, g);
											}
										} catch (NumberFormatException x) {
											(e = new RouteMap()).setMapfile(new File(file.getParent(), d[f].trim()));
											routes.put(e, 1.0);
										}
									}
								}
							} else if (str.startsWith(KEY_VEHICLE)) {
								int c = str.indexOf('=', KEY_VEHICLE.length());
								if (c != -1) {
									String[] d = str.substring(c + 1).split("[*|]");
									File e = null;
									for (int f = 0; f < d.length; f++) {
										try {
											double g = Double.valueOf(d[f].trim());
											if (e == null) {
												System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
											} else {
												vehicles.put(e, g);
											}
										} catch (NumberFormatException x) {
											vehicles.put(e = new File(file.getParent(), d[f].trim()), 1.0);
										}
									}
								}
							} else if (str.startsWith(KEY_TITLE)) {
								int c = str.indexOf('=', KEY_TITLE.length());
								if (c != -1) {
									routemap.setTitle(str.substring(c + 1).trim());
								}
							} else if (str.startsWith(KEY_COMMENT)) {
								int c = str.indexOf('=', KEY_COMMENT.length());
								if (c != -1) {
									routemap.setComment(str.substring(c + 1).trim());
								}
							} else */if (!z[d].isEmpty()) {
								System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
							}
						}
					}
				}
			}
		}
		br.close();

		int b = 0;
		for (int c = 0; c < a.length; c++) {
			if (a[b] < a[c]) {
				b = c;
			}
		}
		routemap.setComment_prefix(_COMMENT_PREFIX[b]);

		//routemap.setRoutes(routes);
		//routemap.setVehicles(vehicles);
		routemap.setCommentOut(commentout);
		return routemap;
	}

	/**TODO
	 * マップファイルに書き込みます。保存先はmap.setMapfile(File)で設定します。
	 * @param scenario シナリオデータ
	 * @param file ファイル
	 * @throws IOException 入出力エラーが発生した場合
	 * @deprecated
	 */
	public static void write(RouteMap map, File file) throws IOException {
		/*Charset cs = scenario.getCharset();
		if (cs == null) {
			cs = DEFAULT_CHARSET;
		}
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), cs));*/
		PrintWriter pw = new PrintWriter(file);

		pw.println(HEADER);

		pw.println(BVE5RouteEditor.HEADER_BVE5RE);

		/*String a = KEY_ROUTE + " = ";
		RouteMap[] routes = scenario.getRoutes().keySet().toArray(new RouteMap[0]);
		for (int b = 0; b < routes.length; b++) {
			if (b != 0) {
				a += " | ";
			}
			a += file.getParentFile().toPath().relativize(routes[b].getMapfile().toPath());
			double c = scenario.getRoutes().get(routes[b]);
			if (c != 1.0) {
				a += " * " + c;
			}
		}
		pw.println(a);

		a = KEY_VEHICLE + " = ";
		File[] vehiclefiles = scenario.getVehicles().keySet().toArray(new File[0]);
		for (int b = 0; b < vehiclefiles.length; b++) {
			if (b != 0) {
				a += " | ";
			}
			a += file.getParentFile().toPath().relativize(vehiclefiles[b].toPath());
			double c = scenario.getVehicles().get(vehiclefiles[b]);
			if (c != 1.0) {
				a += " * " + c;
			}
		}
		pw.println(a);

		pw.println(KEY_TITLE + " = " + scenario.getTitle());
		pw.println(KEY_IMAGE + " = " + scenario.getImage());
		pw.println(KEY_ROUTE_TITLE + " = " + scenario.getRouteTitle());
		pw.println(KEY_VEHICLE_TITLE + " = " + scenario.getVehicleTitle());
		pw.println(KEY_AUTHOR + " = " + scenario.getAuthor());
		pw.println(KEY_COMMENT + " = " + scenario.getComment());*/

		HashMap<Double, String> b = map.getCommentOut();
		Iterator<Double> c = b.keySet().iterator();
		while (c.hasNext()) {
			String d = b.get(c.next());
			if (!d.isEmpty()) {
				String[] e = d.split(System.getProperty("line.separator"));
				for (int f = 0; f < e.length; f++) {
					pw.println(map.getComment_prefix() + e[f]);
				}
			}
		}

		pw.close();
	}
}
