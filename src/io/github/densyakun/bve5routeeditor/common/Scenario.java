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
import java.util.Random;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * BveTs Scenario 2.00
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public class Scenario implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 対応するシナリオファイルの最新バージョン
	 */
	public static final String VERSION = "2.00";
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	public static final String HEADER = "BveTs Scenario " + VERSION;
	public static final String KEY_ROUTE = "Route";
	public static final String KEY_VEHICLE = "Vehicle";
	public static final String KEY_TITLE = "Title";
	public static final String KEY_IMAGE = "Image";
	public static final String KEY_ROUTE_TITLE = "RouteTitle";
	public static final String KEY_VEHICLE_TITLE = "VehicleTitle";
	public static final String KEY_AUTHOR = "Author";
	public static final String KEY_COMMENT = "Comment";
	public static final String[] _COMMENT_PREFIX = new String[]{"#", ";"};

	//Charset cs;

	HashMap<File, Double> routes = new HashMap<File, Double>();
	HashMap<File, Double> vehicles = new HashMap<File, Double>();
	String title;
	String image;
	String routeTitle;
	String vehicleTitle;
	String author;
	String comment;

	String commentout;
	String comment_prefix = _COMMENT_PREFIX[0];

	/**
	 * シナリオファイルの文字セットを取得します。
	 * @return 文字セット
	 */
	/*public Charset getCharset() {
		return cs;
	}*/

	/**
	 * シナリオファイルの文字セットを設定します。
	 * @param cs 文字セット
	 */
	/*public void setCharset(Charset cs) {
		this.cs = cs;
	}*/

	/**
	 * マップファイルと重み係数を取得します。
	 * @return マップファイル(キー)と重み係数(値)
	 */
	public HashMap<File, Double> getRoutes() {
		return routes;
	}

	/**
	 * マップファイルと重み係数を設定します。重み係数が大きいほど、そのマップが選ばれる確率が高くなります。
	 * @param routes マップファイル(キー)と重み係数(値)
	 */
	public void setRoutes(HashMap<File, Double> routes) {
		this.routes = routes;
	}

	/**
	 * シナリオ開始時に選ばれる 1 つのマップファイルがランダムに選ばれます。
	 * @return マップファイル(マップファイルが無い場合はnull)
	 */
	public File getRandomRoute() {
		double a = 0.0;
		File[] keys = routes.keySet().toArray(new File[0]);
		for (int b = 0; b < keys.length; b++) {
			a += routes.get(keys[b]);
		}
		a = new Random().nextDouble() * a;
		double b = 0.0;
		for (int c = 0; c < keys.length; c++) {
			b += routes.get(keys[c]);
			if (a < b) {
				return keys[c];
			}
		}
		return null;
	}

	/**
	 * 車両ファイルと重み係数を取得します。
	 * @return 車両ファイル(キー)と重み係数(値)
	 */
	public HashMap<File, Double> getVehicles() {
		return vehicles;
	}

	/**
	 * 車両ファイルと重み係数を設定します。重み係数が大きいほど、そのファイルが選ばれる確率が高くなります。
	 * @param vehicles 車両ファイル(キー)と重み係数(値)
	 */
	public void setVehicles(HashMap<File, Double> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * シナリオ開始時に選ばれる 1 つの車両ファイルがランダムに選ばれます。
	 * @return 車両ファイル(車両ファイルが無い場合はnull)
	 */
	public File getRandomVehicle() {
		double a = 0.0;
		File[] keys = vehicles.keySet().toArray(new File[0]);
		for (int b = 0; b < keys.length; b++) {
			a += vehicles.get(keys[b]);
		}
		a = new Random().nextDouble() * a;
		double b = 0.0;
		for (int c = 0; c < keys.length; c++) {
			b += vehicles.get(keys[c]);
			if (a < b) {
				return keys[c];
			}
		}
		return null;
	}

	/**
	 * シナリオタイトルを取得します。
	 * @return シナリオタイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * シナリオタイトルを設定します。
	 * @param title シナリオタイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * サムネイル画像を取得します。
	 * @return サムネイル画像
	 */
	public String getImage() {
		return image;
	}

	/**
	 * サムネイル画像を設定します。画像ファイルの相対パスを記述します。
	 * @param image サムネイル画像
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 路線名を取得します。
	 * @return 路線名
	 */
	public String getRouteTitle() {
		return routeTitle;
	}

	/**
	 * 路線名を設定します。
	 * @param routeTitle 路線名
	 */
	public void setRouteTitle(String routeTitle) {
		this.routeTitle = routeTitle;
	}

	/**
	 * 車両名を取得します。
	 * @return 車両名
	 */
	public String getVehicleTitle() {
		return vehicleTitle;
	}

	/**
	 * 車両名を設定します。
	 * @param vehicleTitle 車両名
	 */
	public void setVehicleTitle(String vehicleTitle) {
		this.vehicleTitle = vehicleTitle;
	}

	/**
	 * 路線および車両の作者を取得します。
	 * @return 作者名
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 路線および車両の作者を設定します。路線と車両とで作者が異なる場合、両方の名前を記述します。
	 * @param author 作者名
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * シナリオの説明を取得します。
	 * @return シナリオの説明
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * シナリオの説明を設定します。
	 * @param comment シナリオの説明
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * シナリオファイル読み込み時に確認されたすべてのコメントアウト文字列を取得します。
	 * @return コメントアウト文字列
	 */
	public String getCommentOut() {
		return commentout;
	}

	/**
	 * コメントアウト文字列を設定します。
	 * @param commentout コメントアウト文字列
	 */
	public void setCommentOut(String commentout) {
		this.commentout = commentout;
	}

	/**
	 * コメントアウトの接頭辞を取得します。シナリオファイル読み込み時には自動的に一番使用された接頭辞が設定されています。
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

	@Override
	public String toString() {
		return getTitle();
	}

	/**
	 * シナリオファイルを読み込みます。
	 * @param file ファイル
	 * @return シナリオデータ
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static Scenario read(File file) throws IOException {
		//ファイルが読み込み可能か、使用可能なバージョンかは確認していない。



		Charset cs = DEFAULT_CHARSET;

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

		Scenario scenario = new Scenario();
		HashMap<File, Double> routes = new HashMap<File, Double>();
		HashMap<File, Double> vehicles = new HashMap<File, Double>();
		String commentout = "";



		int[] a = new int[_COMMENT_PREFIX.length];
		for (int b = 0; b < a.length; b++) {
			a[b] = 0;
		}

		String str;
		for (int b = 0; (str = br.readLine()) != null; b++) {
			if (!str.equals(BVE5RouteEditor.HEADER_BVE5RE)) {
				for (int c = 0; c < _COMMENT_PREFIX.length; c++) {
					int d = str.indexOf(_COMMENT_PREFIX[c]);
					if (d != -1) {
						a[c]++;
						if (!commentout.isEmpty()) {
							commentout += System.getProperty("line.separator");
						}
						commentout += str.substring(d + 1);
						str = str.substring(0, d).trim();
						break;
					}
				}

				/*if (a == 0) {
					if ((b = str.indexOf(':')) != -1) {
						scenario.setCharset(Charset.forName(str.substring(b + 1)));
					}
				} else {*/
				if (b != 0) {
					if (str.startsWith(KEY_ROUTE_TITLE)) {
						int c = str.indexOf('=', KEY_ROUTE_TITLE.length());
						if (c != -1) {
							scenario.setRouteTitle(str.substring(c + 1).trim());
						}
					} else if (str.startsWith(KEY_VEHICLE_TITLE)) {
						int c = str.indexOf('=', KEY_VEHICLE_TITLE.length());
						if (c != -1) {
							scenario.setVehicleTitle(str.substring(c + 1).trim());
						}
					} else if (str.startsWith(KEY_ROUTE)) {
						int c = str.indexOf('=', KEY_ROUTE.length());
						if (c != -1) {
							String[] d = str.substring(c + 1).split("[*|]");
							File e = null;
							for (int f = 0; f < d.length; f++) {
								try {
									double g = Double.valueOf(d[f].trim());
									if (e == null) {
										System.out.println("シナリオファイルに構文エラーがあります。 " + (b + 1) + "行目");
									} else {
										routes.put(e, g);
									}
								} catch (NumberFormatException x) {
									routes.put(e = new File(file.getParent(), d[f].trim()), 1.0);
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
										System.out.println("シナリオファイルに構文エラーがあります。 " + (b + 1) + "行目");
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
							scenario.setTitle(str.substring(c + 1).trim());
						}
					} else if (str.startsWith(KEY_IMAGE)) {
						int c = str.indexOf('=', KEY_IMAGE.length());
						if (c != -1) {
							scenario.setImage(str.substring(c + 1).trim());
						}
					} else if (str.startsWith(KEY_AUTHOR)) {
						int c = str.indexOf('=', KEY_AUTHOR.length());
						if (c != -1) {
							scenario.setAuthor(str.substring(c + 1).trim());
						}
					} else if (str.startsWith(KEY_COMMENT)) {
						int c = str.indexOf('=', KEY_COMMENT.length());
						if (c != -1) {
							scenario.setComment(str.substring(c + 1).trim());
						}
					} else if (!str.isEmpty()) {
						System.out.println("シナリオファイルに構文エラーがあります。 " + (b + 1) + "行目");
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
		scenario.setComment_prefix(_COMMENT_PREFIX[b]);

		scenario.setRoutes(routes);
		scenario.setVehicles(vehicles);
		scenario.setCommentOut(commentout);
		return scenario;
	}

	/**
	 * シナリオファイルに書き込みます。
	 * @param scenario シナリオデータ
	 * @param file ファイル
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public static void write(Scenario scenario, File file) throws IOException {
		/*Charset cs = scenario.getCharset();
		if (cs == null) {
			cs = DEFAULT_CHARSET;
		}
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), cs));*/
		PrintWriter pw = new PrintWriter(file);

		pw.println(HEADER);

		pw.println(BVE5RouteEditor.HEADER_BVE5RE);

		String a = KEY_ROUTE + " = ";
		File[] routes = scenario.getRoutes().keySet().toArray(new File[0]);
		for (int b = 0; b < routes.length; b++) {
			if (b != 0) {
				a += " | ";
			}
			a += file.getParentFile().toPath().relativize(routes[b].toPath());
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
		pw.println(KEY_COMMENT + " = " + scenario.getComment());

		if (!(a = scenario.getCommentOut()).isEmpty()) {
			String[] b = a.split(System.getProperty("line.separator"));
			for (int c = 0; c < b.length; c++) {
				pw.println(scenario.getComment_prefix() + b[c]);
			}
		}

		pw.close();
	}

}
