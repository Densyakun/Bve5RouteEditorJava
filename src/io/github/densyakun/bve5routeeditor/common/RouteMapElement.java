package io.github.densyakun.bve5routeeditor.common;

/**
 * マップファイルのステートメントのマップ要素や関数、引数の情報。
 * BveTs Map 2.02
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public enum RouteMapElement {

	Curve, Gradient, Track, Structure, Repeater, Background, Station, Section, Signal, Beacon, SpeedLimit, PreTrain, Light, Fog, DrawDistance, CabIlluminance, Irregularity, Adhesion, Sound, Sound3D, RollingNoise, FlangeNoise, JointNoise, Train;

	/**
	 * ステートメントの関数を返します。
	 * @param element マップ要素
	 * @param element2 キー要素(第二マップ要素)
	 * @return 関数
	 */
	public static String[] getFunctions(RouteMapElement element, String element2) {
		switch (element) {
		case Adhesion:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case Background:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case Beacon:
			if (element2 == null) {
				return new String[] { "Put" };
			}
			break;
		case CabIlluminance:
			if (element2 == null) {
				return new String[] { "Set", "Interpolate" };
			}
			break;
		case Curve:
			if (element2 == null) {
				return new String[] { "Gauge", "SetGauge", "SetCenter", "SetFunction", "BeginTransition", "BeginCircular",
						"Begin", "End", "Interpolate", "Change" };
			}
			break;
		case DrawDistance:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case FlangeNoise:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case Fog:
			if (element2 == null) {
				return new String[] { "Set", "Interpolate" };
			}
			break;
		case Gradient:
			if (element2 == null) {
				return new String[] { "BeginTransition", "BeginConst", "Begin", "End", "Interpolate" };
			}
			break;
		case Irregularity:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case JointNoise:
			if (element2 == null) {
				return new String[] { "Play" };
			}
			break;
		case Light:
			if (element2 == null) {
				return new String[] { "Ambient", "Diffuse", "Direction" };
			}
			break;
		case PreTrain:
			if (element2 == null) {
				return new String[] { "Pass" };
			}
			break;
		case Repeater:
			if (element2 == null) {
				return new String[] { "Begin", "Begin0", "End" };
			}
			break;
		case RollingNoise:
			if (element2 == null) {
				return new String[] { "Change" };
			}
			break;
		case Section:
			if (element2 == null) {
				return new String[] { "BeginNew", "Begin", "SetSpeedLimit" };
			}
			break;
		case Signal:
			if (element2 == null) {
				return new String[] { "SpeedLimit", "Load", "Put" };
			}
			break;
		case Sound:
			if (element2 == null) {
				return new String[] { "Load", "Play" };
			}
			break;
		case Sound3D:
			if (element2 == null) {
				return new String[] { "Load", "Put" };
			}
			break;
		case SpeedLimit:
			if (element2 == null) {
				return new String[] { "Begin", "End" };
			}
			break;
		case Station:
			if (element2 == null) {
				return new String[] { "Load", "Put" };
			}
			break;
		case Structure:
			if (element2 == null) {
				return new String[] { "Load", "Put", "Put0", "PutBetween" };
			}
			break;
		case Track:
			if (element2 == null) {
				return new String[] { "Position", "Gauge", "Cant" };
			} else if (element2 == "X") {
				return new String[] { "Interpolate" };
			} else if (element2 == "Y") {
				return new String[] { "Interpolate" };
			} else if (element2 == "Cant") {
				return new String[] { "SetGauge", "SetCenter", "SetFunction", "BeginTransition", "Begin", "End", "Interpolate" };
			}
			break;
		case Train:
			if (element2 == null) {
				return new String[] { "Add", "Load", "Enable", "Stop" };
			}
			break;
		default:
			break;
		}
		return new String[0];
	}

	/**
	 * ステートメントのキーが必要な場合はtrue、キーが必要ない場合はfalseを返します。
	 * @param element マップ要素
	 * @param element2 キー要素(第二マップ要素)
	 * @param function 関数
	 * @return キーが必要な場合はtrue、キーが必要ない場合はfalseを返します。
	 */
	public static boolean isRequiredKey(RouteMapElement element, String element2, String function) {
		switch (element) {
		case Repeater:
			if (function.equalsIgnoreCase("Begin") || function.equalsIgnoreCase("Begin0") || function.equalsIgnoreCase("End")) {
				return true;
			}
			break;
		case Signal:
			if (function.equalsIgnoreCase("Put")) {
				return true;
			}
			break;
		case Sound:
			if (function.equalsIgnoreCase("Play")) {
				return true;
			}
			break;
		case Sound3D:
			if (function.equalsIgnoreCase("Put")) {
				return true;
			}
			break;
		case Station:
			if (function.equalsIgnoreCase("Put")) {
				return true;
			}
			break;
		case Structure:
			if (function.equalsIgnoreCase("Put") || function.equalsIgnoreCase("Put0") || function.equalsIgnoreCase("PutBetween")) {
				return true;
			}
			break;
		case Track:
			return true;
		case Train:
			if (function.equalsIgnoreCase("Load") || function.equalsIgnoreCase("Enable") || function.equalsIgnoreCase("Stop")) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * ステートメントの引数が正しい場合はtrue、正しくない場合はfalseを返します。
	 * @param element マップ要素
	 * @param element2 キー要素(第二マップ要素)
	 * @param function 関数
	 * @param args 引数
	 * @return 引数が正しい場合はtrue、正しくない場合はfalseを返します。
	 */
	public static boolean isCollectArguments(RouteMapElement element, String element2, String function, Object[] args) {
		Object[][] a = null;
		switch (element) {
		case Adhesion:
			if (function.equalsIgnoreCase("Change")) {
				a = new Object[][] { { 0.0 }, { 0.0, 0.0, 0.0 } };
			}
			break;
		case Background:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Change")) {
					a = new Object[][] { { "" } };
				}
			}
			break;
		case Beacon:
			if (function.equalsIgnoreCase("Put")) {
				a = new Object[][] { { 0, 0, 0 } };
			}
			break;
		case CabIlluminance:
			if (function.equalsIgnoreCase("Set")) {
				a = new Object[][] { { 0.0 } };
			}
			if (function.equalsIgnoreCase("Interpolate")) {
				a = new Object[][] { { 0.0 }, {} };
			}
			break;
		case Curve:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Gauge") || function.equalsIgnoreCase("SetGauge") || function.equalsIgnoreCase("SetCenter") || function.equalsIgnoreCase("Change")) {
					a = new Object[][] { { 0.0 } };
				}
				if (function.equalsIgnoreCase("SetFunction")) {
					a = new Object[][] { { 0 } };
				}
				if (function.equalsIgnoreCase("BeginTransition") || function.equalsIgnoreCase("End")) {
					a = new Object[][] { {} };
				}
				if (function.equalsIgnoreCase("BeginCircular")) {
					a = new Object[][] { { 0.0, 0.0 } };
				}
				if (function.equalsIgnoreCase("Begin")) {
					a = new Object[][] { { 0.0, 0.0 }, { 0.0 } };
				}
				if (function.equalsIgnoreCase("Interpolate")) {
					a = new Object[][] { { 0.0, 0.0 }, { 0.0 }, {} };
				}
			}
			break;
		case DrawDistance:
			if (function.equalsIgnoreCase("Change")) {
				a = new Object[][] { { 0.0 } };
			}
			break;
		case FlangeNoise:
			if (function.equalsIgnoreCase("Change")) {
				a = new Object[][] { { 0 } };
			}
			break;
		case Fog:
			if (function.equalsIgnoreCase("Set")) {
				a = new Object[][] { { 0.0, 0.0, 0.0, 0.0 } };
			}
			if (function.equalsIgnoreCase("Interpolate")) {
				a = new Object[][] { { 0.0, 0.0, 0.0, 0.0 }, { 0.0 }, {} };
			}
			break;
		case Gradient:
			if (element2 == null) {
				if (function.equalsIgnoreCase("BeginTransition") || function.equalsIgnoreCase("End")) {
					a = new Object[][] { {} };
				}
				if (function.equalsIgnoreCase("BeginConst") || function.equalsIgnoreCase("Begin")) {
					a = new Object[][] { { 0.0 } };
				}
				if (function.equalsIgnoreCase("Interpolate")) {
					a = new Object[][] { { 0.0 }, {} };
				}
			}
			break;
		case Irregularity:
			if (function.equalsIgnoreCase("Change")) {
				a = new Object[][] { { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } };
			}
			break;
		case JointNoise:
			if (function.equalsIgnoreCase("Play")) {
				a = new Object[][] { { 0 } };
			}
			break;
		case Light:
			if (function.equalsIgnoreCase("Ambient")) {
				a = new Object[][] { { 0.0, 0.0, 0.0 } };
			}
			if (function.equalsIgnoreCase("Diffuse")) {
				a = new Object[][] { { 0.0, 0.0, 0.0 } };
			}
			if (function.equalsIgnoreCase("Direction")) {
				a = new Object[][] { { 0.0, 0.0 } };
			}
			break;
		case PreTrain:
			if (function.equalsIgnoreCase("Pass")) {
				a = new Object[][] { { "" }, { 0 } };
			}
			break;
		case Repeater:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Begin")) {
					a = new Object[][] { { "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0, 0.0, "" } };
				}
				if (function.equalsIgnoreCase("Begin0")) {
					a = new Object[][] { { "", 0, 0.0, 0.0, "" } };
				}
				if (function.equalsIgnoreCase("End")) {
					a = new Object[][] { {} };
				}
			}
			break;
		case RollingNoise:
			if (function.equalsIgnoreCase("Change")) {
				a = new Object[][] { { 0 } };
			}
			break;
		case Section:
			if (element2 == null) {
				if (function.equalsIgnoreCase("BeginNew") || function.equalsIgnoreCase("Begin")) {
					a = new Object[][] { { 0 } };
				}
				if (function.equalsIgnoreCase("SetSpeedLimit")) {
					a = new Object[][] { { 0.0 } };
				}
			}
			break;
		case Signal:
			if (element2 == null) {
				if (function.equalsIgnoreCase("SpeedLimit")) {
					a = new Object[][] { { 0.0 } };
				}
				if (function.equalsIgnoreCase("Load")) {
					a = new Object[][] { { "" } };
				}
				if (function.equalsIgnoreCase("Put")) {
					a = new Object[][] { { 0, "", 0.0, 0.0 }, { 0, "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0 } };
				}
			}
			break;
		case Sound:
			if (function.equalsIgnoreCase("Load")) {
				a = new Object[][] { { "" } };
			}
			if (function.equalsIgnoreCase("Play")) {
				a = new Object[][] { {} };
			}
			break;
		case Sound3D:
			if (function.equalsIgnoreCase("Load")) {
				a = new Object[][] { { "" } };
			}
			if (function.equalsIgnoreCase("Put")) {
				a = new Object[][] { { 0.0, 0.0 } };
			}
			break;
		case SpeedLimit:
			if (function.equalsIgnoreCase("Begin")) {
				a = new Object[][] { { 0.0 } };
			}
			if (function.equalsIgnoreCase("End")) {
				a = new Object[][] { {} };
			}
			break;
		case Station:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Load")) {
					a = new Object[][] { { "" } };
				}
				if (function.equalsIgnoreCase("Put")) {
					a = new Object[][] { { 0, 0.0, 0.0 } };
				}
			}
			break;
		case Structure:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Load")) {// (=_= )...
					a = new Object[][] { { "" } };
				}
				if (function.equalsIgnoreCase("Put")) {
					a = new Object[][] { { "", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0.0 } };
				}
				if (function.equalsIgnoreCase("Put0")) {
					a = new Object[][] { { "", 0, 0.0 } };
				}
				if (function.equalsIgnoreCase("PutBetween")) {
					a = new Object[][] { { "", "", 0 } };
				}
			}
			break;
		case Track:
			if (element2 == null) {
				if (function.equalsIgnoreCase("Position")) {
					a = new Object[][] { { 0.0, 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0 }, { 0.0, 0.0 } };
				}
				if (function.equalsIgnoreCase("Gauge") || function.equalsIgnoreCase("Cant")) {
					a = new Object[][] { { 0.0 } };
				}
			} else if (element2.equalsIgnoreCase("X") || element2.equalsIgnoreCase("Y")) {
				if (function.equalsIgnoreCase("Interpolate")) {
					a = new Object[][] { { 0.0, 0.0 }, { 0.0 }, {} };
				}
			} else if (element2.equalsIgnoreCase("Cant")) {
				if (function.equalsIgnoreCase("SetGauge") || function.equalsIgnoreCase("SetCenter") || function.equalsIgnoreCase("Begin")) {
					a = new Object[][] { { 0.0 } };
				}
				if (function.equalsIgnoreCase("SetFunction")) {
					a = new Object[][] { { 0 } };
				}
				if (function.equalsIgnoreCase("BeginTransition") || function.equalsIgnoreCase("End")) {
					a = new Object[][] { {} };
				}
				if (function.equalsIgnoreCase("Interpolate")) {
					a = new Object[][] { { 0.0 }, {} };
				}
			}
			break;
		case Train:
			if (function.equalsIgnoreCase("Add")) {
				a = new Object[][] { { "", "", "", 0 } };
			}
			if (function.equalsIgnoreCase("Load")) {
				a = new Object[][] { { "", "", 0 } };
			}
			if (function.equalsIgnoreCase("Enable")) {
				a = new Object[][] { { "" }, { 0.0 } };
			}
			if (function.equalsIgnoreCase("Stop")) {
				a = new Object[][] { { 0.0, 0.0, 0.0, 0.0 } };
			}
			break;
		default:
			break;
		}
		if (a != null) {

			//型が使用可能か判定。型が違う場合でも可能なケースに対応
			for (int b = 0; b < a.length; b++) {
				boolean c = true;
				if ( ( element == RouteMapElement.Repeater &&
						(function.equalsIgnoreCase("Begin") || function.equalsIgnoreCase("Begin0")) ||
						element == RouteMapElement.Section ||
						element == RouteMapElement.Signal && function.equalsIgnoreCase("SpeedLimit") ) &&
						a[b].length <= args.length ||
						a[b].length == args.length) {

					for (int d = 0; d < args.length; d++) {
						Object e = null;
						if (d < a[b].length) {
							if (a[b][d] != null) {
								e = a[b][d];
							}
						} else if (a[b][a[b].length - 1] != null) {
							e = a[b][a[b].length - 1];
						}

						if (e instanceof Integer && args[d] instanceof Double && (Double) args[d] == ((Double) args[d]).intValue() && (Double) args[d] == 0) {
							args[d] = ((Double) args[d]).intValue();
						}

						//軌道名の入力に対応
						if (
								((element == RouteMapElement.Signal && function.equalsIgnoreCase("Put")) ? d == 1 : (element == RouteMapElement.Structure && function.equalsIgnoreCase("PutBetween")) ? d == 0 || d == 1 : d == 0) &&
								(element == RouteMapElement.Structure && (function.equalsIgnoreCase("Put") || function.equalsIgnoreCase("Put0") || function.equalsIgnoreCase("PutBetween")) ||
								element == RouteMapElement.Repeater && (function.equalsIgnoreCase("Begin") || function.equalsIgnoreCase("Begin0")) ||
								element == RouteMapElement.Signal && function.equalsIgnoreCase("Put"))
								) {
							if (args[d] != null && !(args[d] instanceof String || args[d] instanceof Double && (Double) args[d] == ((Double) args[d]).intValue() && (Double) args[d] == 0)) {
								c = false;
								break;
							}
						} else if (args[d] != null && !((args[d] instanceof Double && e instanceof Integer) || e.getClass().isInstance(args[d]))) {
							c = false;
							break;
						}
					}
				} else {
					c = false;
				}
				if (c) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ステートメントのキー要素(第二マップ要素)を返します。
	 * @param element
	 * @return キー要素(第二マップ要素)
	 */
	public static String[] getElements2(RouteMapElement element) {
		switch (element) {
		case Track:
			return new String[] { "X", "Y", "Cant" };
		default:
			return new String[0];
		}
	}

}
