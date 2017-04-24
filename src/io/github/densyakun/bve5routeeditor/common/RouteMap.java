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

				if (b == 0) {
					/*TODO 動かない
					if (!str.startsWith(HEADER_)) {
						System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
						break;
					}*/
				} else {
					String[] c = str.split(SUFFIX);
					for (int d = 0; d < c.length; d++) {
						statement += c[d].trim();
						try {
							distance = Double.valueOf(statement);
						} catch (NumberFormatException e) {
							if (!statement.isEmpty()) {
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
										boolean l = true;
										RouteMapElement[] m = RouteMapElement.values();
										for (int n = 0; n < m.length; n++) {
											if (m[n].name().equalsIgnoreCase(k)) {
												l = false;

												String[] o = statement.substring(f + 1, g).split(",");
												Object[] p;
												if (o.length == 1 && o[0].isEmpty()) {
													p = new Object[0];
												} else {
													p = new Object[o.length];
													for (int q = 0; q < o.length; q++) {
														String r = o[q].trim();
														if (r.isEmpty()) {
															p[q] = null;
														} else if (r.startsWith("'") && r.endsWith("'")) {
															p[q] = r.substring(1, r.length() - 1);
														} else {
															try {
																p[q] = Double.valueOf(r);
															} catch (NumberFormatException ex) {
																try {
																	p[q] = Integer.valueOf(r);
																} catch (NumberFormatException x) {
																	System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
																}
															}
														}
													}
												}
												String q = null;
												if (2 < h.length) {
													boolean r = true;
													for (int s = 0; s < m.length; s++) {
														if (m[s].name().equalsIgnoreCase(h[1])) {
															r = false;

															q = m[s].name();
															break;
														}
													}
													if (r) {
														System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
													}
												}

												boolean u = true;
												String[] v = RouteMapElement.getFunctions(m[n]);
												for (int w = 0; w < v.length; w++) {
													if (v[w].equalsIgnoreCase(h[h.length - 1])) {
														u = false;

														Object[][] x = RouteMapElement.getArguments(m[n], v[w]);
														boolean y = true;
														for (int z = 0; z < x.length; z++) {
															boolean aa = true;
															if (p.length == x[z].length) {
																for (int ab = 0; ab < x[z].length; ab++) {
																	if (!(p[z].getClass().isInstance(x[z][ab]))) {
																		aa = false;
																		break;
																	}
																}
															} else {
																aa = false;
															}
															if (aa) {
																y = false;
																break;
															}
														}

														if (y) {
															System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
														} else {
															RouteMapStatement z = new RouteMapStatement(m[n], v[w], p);
															if (i != -1 && j != -1) {
																String aa = statement.substring(i + 1, j);
																if (aa.startsWith("'") && aa.endsWith("'")) {
																	z.setElement_key(aa.substring(1, aa.length() - 1));
																}
															}
															if (q != null) {
																z.setElement2(q);
															}
															ArrayList<RouteMapStatement> aa = statements.get(distance);
															if (aa == null) {
																aa = new ArrayList<RouteMapStatement>();
															}
															aa.add(z);
															statements.put(distance, aa);
														}
														break;
													}
												}
												if (u) {
													System.out.println("マップファイルに構文エラーがあります。 " + (b + 1) + "行目");
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
						statement = "";
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
		map.setComment_prefix(_COMMENT_PREFIX[b]);

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
