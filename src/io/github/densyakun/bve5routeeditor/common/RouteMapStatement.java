package io.github.densyakun.bve5routeeditor.common;

import java.io.Serializable;

/**
 * マップファイルのステートメント
 * BveTs Map 2.02
 * @author Densyakun
 * @version BVE5RouteEditor0.001alpha
 */
public class RouteMapStatement implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private RouteMapElement element;
	private String function;
	private Object[] args;
	private String element2;
	private String element_key;

	/**
	 *
	 * @param element マップ要素
	 * @param function 関数
	 * @param args 引数
	 */
	public RouteMapStatement(RouteMapElement element, String function, Object[] args) {
		this(element, null, function, args);
	}

	/**
	 *
	 * @param element マップ要素
	 * @param element2 キー要素(第二マップ要素)
	 * @param function 関数
	 * @param args 引数
	 */
	public RouteMapStatement(RouteMapElement element, String element2, String function, Object[] args) {
		setElement(element);
		setElement2(element2);
		setFunction(function);
		setArguments(args);
	}

	/**
	 * マップ要素を返します。
	 * @return マップ要素
	 */
	public RouteMapElement getElement() {
		return element;
	}

	/**
	 * マップ要素を設定します。
	 * @param element マップ要素
	 */
	public void setElement(RouteMapElement element) {
		this.element = element;
	}

	/**
	 * 関数を返します。
	 * @return 関数
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * 関数を設定します。
	 * @param function 関数
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * 引数を返します。
	 * @return 引数
	 */
	public Object[] getArguments() {
		return args;
	}

	/**
	 * 引数を設定します。
	 * @param args 引数
	 */
	public void setArguments(Object[] args) {
		this.args = args;
	}

	/**
	 * キー要素(第二マップ要素)を返します。
	 * @return キー要素(第二マップ要素)
	 */
	public String getElement2() {
		return element2;
	}

	/**
	 * キー要素(第二マップ要素)を設定します。
	 * @param element2 キー要素(第二マップ要素)
	 */
	public void setElement2(String element2) {
		this.element2 = element2;
	}

	/**
	 * キーを返します。
	 * @return キー
	 */
	public String getElement_key() {
		return element_key;
	}

	/**
	 * キーを設定します。
	 * @param element_key キー
	 */
	public void setElement_key(String element_key) {
		this.element_key = element_key;
	}

	@Override
	public String toString() {
		String str = element.name();
		if (element_key != null) {
			str += "['" + element_key + "']";
		}
		if (element2 != null) {
			str += "." + element2;
		}
		str += "." + function + "(";
		for (int a = 0; a < args.length; a++) {
			if (a != 0) {
				str += ", ";
			}
			if (args[a] != null) {
				if (args[a] instanceof String) {
					str += "'" + args[a] + "'";
				} else {
					str += args[a];
				}
			}
		}
		str += ")";
		return str;
	}

}
