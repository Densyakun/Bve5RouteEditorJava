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

		double b = 0.0;
		double c = 0.0;
		ArrayList<Double> d = new ArrayList<Double>(curvePoints.keySet());
		Collections.sort(d);
		for (int e = 0; e < d.size(); e++) {
			double f = d.get(e);
			if (c != 0.0) {
				double g = curvePoints.get(c);
				double h = curvePoints.get(f);
				double i = c - f / h;

				// true=cからeまで直線 false=cからeまで曲線
				if (g == h) {
					a.add(new Vector2D(0.0, Math.min(f, distance) - c).rotate(b + i));
					if (distance < f) {
						break;
					}
				} else {
					if (distance < f) {

						break;
					}
				}

				b += i;
			}
			c = f;
		}

		return a;
	}
}
