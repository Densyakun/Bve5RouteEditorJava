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
		double d1 = 0.0;
		double r1 = 0.0;
		double rad = 0.0;
		for (int e = 0; e < b.size(); e++) {
			double d2 = Math.min(b.get(e), distance);
			double r2 = curvePoints.get(d2);
			if (r2 != 0.0) {
				r2 = (d2 - d1) / r2;
			}

			// true=cからeまで直線 false=cからeまで曲線
			if (r1 == 0) {
				a = a.add(Vector2D.UP.multiply(d2 - d1).rotate(rad));
				if (distance <= d1) {
					break;
				}
			} else {
				//a = a.add(Vector2D.UP.multiply(d2 - d1).rotate(rad + r2));
				if (distance <= d1) {
					break;
				}
			}

			d1 = d2;
			r1 = r2;
			rad += r2;
		}

		return a;
	}
}
