package io.github.densyakun.bve5routeeditor.common;

import java.io.Serializable;

/**
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

	public RouteMapStatement(RouteMapElement element, String function, Object[] args) {
		setElement(element);
		setFunction(function);
		setArgs(args);
	}

	public RouteMapElement getElement() {
		return element;
	}

	public void setElement(RouteMapElement element) {
		this.element = element;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getElement2() {
		return element2;
	}

	public void setElement2(String element2) {
		this.element2 = element2;
	}

	public String getElement_key() {
		return element_key;
	}

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
				str += args[a];
			}
		}
		str += ")";
		return str;
	}

}
