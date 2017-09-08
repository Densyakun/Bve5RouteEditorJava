package io.github.densyakun.bve5routeeditor.common.linear;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 線路の線形情報。
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public class Linear implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	HashMap<Double, Double> curvePoints = new HashMap<Double, Double>();

	public Linear() {
	}

	public HashMap<Double, Double> getCurvePoints() {
		return curvePoints;
	}

	public void setCurvePoints(HashMap<Double, Double> curvePoints) {
		this.curvePoints = curvePoints;
	}

	public Vector2D getPosition(double distance) {
		Vector2D a = new Vector2D();

		ArrayList<Double> b = new ArrayList<Double>(curvePoints.keySet());
		Collections.sort(b);
		double d1 = 0.0;//距離
		double r1 = 0.0;//曲線半径
		double rad1 = 0.0;//現在の角度
		for (int e = 0; e < b.size(); e++) {
			double d2 = Math.min(b.get(e), distance);//次の点の距離
			double r2 = curvePoints.get(d2);//次の点からの曲線半径
			//System.out.println(e + "=" + d2);
			if (e == 0 && d1 > d2) {
				d1 = d2;
			}

			if (r1 == 0.0)
				a = a.add(Vector2D.UP.multiply(d2 - d1).rotate(rad1));
			else {
				double r11 = Math.abs(r1);//半径
				double w = (d2 - d1) / r11;//弧の長さ
				double x = Math.cos(w) - 1;
				a = a.add(new Vector2D(0 <= r1 ? x : -x, Math.sin(w)));
			}

			if (distance == d2)
				break;

			d1 = d2;
			r1 = r2;
			double rad2 = r2 == 0.0 ? 0.0 : (d2 - d1) / Math.abs(r2);
			rad1 += rad2;
		}

		return a;
	}
}
