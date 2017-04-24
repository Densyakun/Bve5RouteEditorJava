package io.github.densyakun.bve5routeeditor.common;

/**
 * BveTs Map 2.02
 *
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public enum RouteMapElement {

	Curve, Gradient, Track, Structure, Repeater, Background, Station, Section, Signal, Beacon, SpeedLimit, PreTrain, Light, Fog, DrawDistance, CabIlluminance, Irregularity, Adhesion, Sound, Sound3D, RollingNoise, FlangeNoise, JointNoise, Train;

	public static String[] getFunctions(RouteMapElement element) {
		switch (element) {
		case Adhesion:
			break;
		case Background:
			break;
		case Beacon:
			break;
		case CabIlluminance:
			break;
		case Curve:
			return new String[] { "Gauge", "SetGauge", "SetCenter", "SetFunction", "BeginTransition", "BeginCircular",
					"Begin", "End", "Interpolate", "Change" };
		case DrawDistance:
			break;
		case FlangeNoise:
			break;
		case Fog:
			break;
		case Gradient:
			break;
		case Irregularity:
			break;
		case JointNoise:
			break;
		case Light:
			break;
		case PreTrain:
			break;
		case Repeater:
			break;
		case RollingNoise:
			break;
		case Section:
			break;
		case Signal:
			break;
		case Sound:
			break;
		case Sound3D:
			break;
		case SpeedLimit:
			break;
		case Station:
			break;
		case Structure:
			break;
		case Track:
			break;
		case Train:
			break;
		default:
			break;
		}
		return new String[0];
	}

	public static Object[][] getArguments(RouteMapElement element, String function) {
		switch (element) {
		case Adhesion:
			break;
		case Background:
			break;
		case Beacon:
			break;
		case CabIlluminance:
			break;
		case Curve:
			if (function.equalsIgnoreCase("Gauge") || function.equalsIgnoreCase("SetGauge") || function.equalsIgnoreCase("SetCenter") || function.equalsIgnoreCase("Change")) {
				return new Object[][] { { 0.0 } };
			}
			if (function.equalsIgnoreCase("SetFunction")) {
				return new Object[][] { { 0 } };
			}
			if (function.equalsIgnoreCase("BeginTransition") || function.equalsIgnoreCase("End")) {
				return new Object[][] { {} };
			}
			if (function.equalsIgnoreCase("BeginCircular")) {
				return new Object[][] { { 0.0, 0.0 } };
			}
			if (function.equalsIgnoreCase("Begin")) {
				return new Object[][] { { 0.0, 0.0 }, { 0.0 } };
			}
			if (function.equalsIgnoreCase("Interpolate")) {
				return new Object[][] { { 0.0, 0.0 }, { 0.0 }, {} };
			}
			break;
		case DrawDistance:
			break;
		case FlangeNoise:
			break;
		case Fog:
			break;
		case Gradient:
			break;
		case Irregularity:
			break;
		case JointNoise:
			break;
		case Light:
			break;
		case PreTrain:
			break;
		case Repeater:
			break;
		case RollingNoise:
			break;
		case Section:
			break;
		case Signal:
			break;
		case Sound:
			break;
		case Sound3D:
			break;
		case SpeedLimit:
			break;
		case Station:
			break;
		case Structure:
			break;
		case Track:
			break;
		case Train:
			break;
		default:
			break;
		}
		return new Object[0][];
	}

}
