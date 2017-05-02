package io.github.densyakun.bve5routeeditor.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.mozilla.universalchardet.UniversalDetector;

import io.github.densyakun.bve5routeeditor.common.linear.Linear;

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
	public static final String HEADER_ = "BveTs Map ";
	public static final String HEADER = HEADER_ + VERSION;
	public static final String SUFFIX = ";";
	public static final String[] _COMMENT_PREFIX = new String[]{"#", "//"};

	private HashMap<Double, ArrayList<RouteMapStatement>> statements = new HashMap<Double, ArrayList<RouteMapStatement>>();
	private HashMap<Double, String> commentout = new HashMap<Double, String>();
	private String comment_prefix = _COMMENT_PREFIX[0];
	private Linear own_linear = null;

	/**
	 * ステートメントを取得します。
	 * @return 距離程(キー)とステートメント(値)
	 */
	public HashMap<Double, ArrayList<RouteMapStatement>> getStatements() {
		return statements;
	}

	/**
	 * ステートメントを設定します。
	 * @param statements 距離程(キー)とステートメント(値)
	 */
	public void setStatements(HashMap<Double, ArrayList<RouteMapStatement>> statements) {
		this.statements = statements;
	}

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

	public Linear getOwn_Linear() {
		if (own_linear == null) {
			load_own_linear();
		}
		return own_linear;
	}

	//TODO
	/*public void setOwn_Linear(Linear own_linear) {
		this.own_linear = own_linear;
	}*/

	private void load_own_linear() {
		own_linear = new Linear();
		HashMap<Double, Double> curvePoints = new HashMap<Double, Double>();

		Double[] a = statements.keySet().toArray(new Double[0]);
		for (int b = 0; b < a.length; b++) {
			ArrayList<RouteMapStatement> c = statements.get(a[b]);
			for (int d = 0; d < c.size(); d++) {
				RouteMapStatement e = c.get(d);
				if (e.getElement() == RouteMapElement.Curve) {
					if (e.getFunction().equalsIgnoreCase("BeginCircular") || e.getFunction().equalsIgnoreCase("Begin")) {
						curvePoints.put(a[b], (Double) e.getArguments()[0]);
					} else if (e.getFunction().equalsIgnoreCase("End")) {
						curvePoints.put(a[b], 0.0);
					}
				}
			}
		}

		own_linear.setCurvePoints(curvePoints);
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

		RouteMap map = new RouteMap();
		HashMap<Double, ArrayList<RouteMapStatement>> statements = new HashMap<Double, ArrayList<RouteMapStatement>>();
		HashMap<Double, String> commentout = new HashMap<Double, String>();



		int[] a = new int[_COMMENT_PREFIX.length];
		for (int b = 0; b < a.length; b++) {
			a[b] = 0;
		}

		double distance = 0.0;
		String statement = "";

		String str;
		for (int b = 0; (str = br.readLine()) != null; b++) {
			if (!str.equals(BVE5RouteEditor.HEADER_BVE5RE)) {
				str = str.trim();

				//コメントアウト文の処理(1)
				String commentout2 = null;
				for (int c = 0; c < _COMMENT_PREFIX.length; c++) {
					int d = str.indexOf(_COMMENT_PREFIX[c]);
					if (d != -1) {
						a[c]++;
						if (commentout2 == null) {
							commentout2 = "";
						} else {
							commentout2 += System.getProperty("line.separator");
						}
						commentout2 += str.substring(d + 1);
						str = str.substring(0, d).trim();
						break;
					}
				}

				//ヘッダー判定
				//TODO 文字コードの判定はシナリオから
				if (b == 0) {
					/*TODO 何故か動かない
					if (!str.startsWith(HEADER_)) {
						System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
						break;
					}*/
				} else {

					//ステートメントの読み込み
					String[] c = str.split(SUFFIX);
					for (int d = 0; d < c.length; d++) {
						statement += c[d].trim();
						try {
							//TODO 変数などに対応させるため引数と同じような処理にする
							//距離程
							distance = Double.valueOf(statement);
						} catch (NumberFormatException e1) {

							//ステートメント
							if (!statement.isEmpty()) {

								//TODO 念のため、文字列に反応しないようにする
								int f = statement.indexOf('(');
								int g = statement.indexOf(')');
								if (f != -1 && g != -1) {
									String[] h = statement.substring(0, f).split("\\.");
									if (2 <= h.length && h.length <= 3) {
										int i = statement.indexOf('[');
										int j = statement.indexOf(']');
										String k = h[0];
										if (i != -1) {
											k = k.substring(0, i);
										}

										//マップ要素の判定
										boolean l = true;
										RouteMapElement[] m = RouteMapElement.values();
										for (int n = 0; n < m.length; n++) {
											if (m[n].name().equalsIgnoreCase(k)) {
												l = false;

												//引数の読み込み
												String[] o = statement.substring(f + 1, g).split(",");
												Object[] args;
												if (o.length == 1 && o[0].isEmpty()) {
													args = new Object[0];
												} else {
													args = new Object[o.length];
													for (int p = 0; p < o.length; p++) {
														String q = o[p].trim();
														if (q.isEmpty()) {
															args[p] = null;
														} else if (q.startsWith("'") && q.endsWith("'")) {
															args[p] = q.substring(1, q.length() - 1);
														} else {
															try {
																args[p] = Double.valueOf(q);
															} catch (NumberFormatException e3) {
																if (q.isEmpty()) {
																	args[p] = null;
																} else {
																	//構文エラー
																	args = null;
																}
															}
														}
													}
												}

												if (args == null) {
													System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
												} else {
													//キー要素(第二マップ要素)の読み込み
													String element2 = null;
													if (2 < h.length) {
														boolean p = true;
														String[] q = RouteMapElement.getElements2(m[n]);
														for (int r = 0; r < q.length; r++) {
															if (q[r].equalsIgnoreCase(h[1])) {
																p = false;

																element2 = q[r];
																break;
															}
														}
														if (p) {
															System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
														}
													}

													//関数の判定
													boolean p = true;
													String[] q = RouteMapElement.getFunctions(m[n], element2);
													for (int r = 0; r < q.length; r++) {
														if (q[r].equalsIgnoreCase(h[h.length - 1])) {
															p = false;

															//キーが設定されている場合はキーを読み込む
															String key = null;

															if (i != -1 && j != -1) {
																key = statement.substring(i + 1, j);
																if (key.startsWith("'") && key.endsWith("'")) {
																	key = key.substring(1, key.length() - 1);
																}
															}

															//キーが必要か
															if (RouteMapElement.isRequiredKey(m[n], element2, q[r]) == (key != null)) {

																//引数が正しいか
																if (RouteMapElement.isCollectArguments(m[n], element2, q[r], args)) {

																	//ステートメントの登録
																	RouteMapStatement s = new RouteMapStatement(m[n], element2, q[r], args);

																	// -μ- .oO zZＺ...
																	//キーの設定
																	s.setElement_key(key);

																	//同じ距離程にあるステートメントに追加
																	ArrayList<RouteMapStatement> t = statements.get(distance);
																	if (t == null) {
																		t = new ArrayList<RouteMapStatement>();
																	}
																	t.add(s);

																	statements.put(distance, t);
																} else {
																	System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
																}
															} else {
																System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
															}
															break;
														}
													}
													if (p) {
														System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
													}
												}
												break;
											}
										}
										if (l) {
											System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
										}
									} else {
										System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
									}
								} else {
									System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
								}
							}
						}

						//コメントアウト文の処理(2)
						if (commentout2 != null) {
							String value = commentout.get(distance);
							if (value == null) {
								value = "";
							} else {
								value += System.getProperty("line.separator");
							}
							commentout.put(distance, value + commentout2);
						}

						statement = "";
					}
				}
			}
		}
		br.close();

		//コメントアウト接頭辞の判定
		int b = 0;
		for (int c = 0; c < a.length; c++) {
			if (a[b] < a[c]) {
				b = c;
			}
		}
		map.setComment_prefix(_COMMENT_PREFIX[b]);

		//マップデータの設定
		map.setStatements(statements);
		map.setCommentOut(commentout);

		return map;
	}

	/**
	 * マップファイルに書き込みます。
	 * @param scenario シナリオデータ
	 * @param file ファイル
	 * @throws IOException 入出力エラーが発生した場合
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

		ArrayList<Double> a = new ArrayList<Double>(map.statements.keySet());
		Double[] b = map.getCommentOut().keySet().toArray(new Double[0]);
		for (int c = 0; c < b.length; c++) {
			if (!a.contains(b[c])) {
				a.add(b[c]);
			}
		}
		Collections.sort(a);
		for (int c = 0; c < a.size(); c++) {
			double d = a.get(c);
			if (d != 0.0) {
				if (d == (int) d) {
					pw.println((int) d + SUFFIX);
				} else {
					pw.println(d + SUFFIX);
				}
			}
			String e = map.commentout.get(d);
			if (e != null && !e.isEmpty()) {
				String[] f = e.split(System.getProperty("line.separator"));
				for (int g = 0; g < f.length; g++) {
					pw.println(map.getComment_prefix() + f[g]);
				}
			}
			ArrayList<RouteMapStatement> f = map.statements.get(d);
			if (f != null) {
				for (int g = 0; g < f.size(); g++) {
					pw.println(f.get(g) + SUFFIX);
				}
			}
		}

		pw.close();
	}
}
