package me.pandamods.pandalib.client.screen.elements;

import java.util.List;

public interface GUIImpl extends ElementImpl {
	List<ElementImpl> elements();
	void init();
	default <E extends ElementImpl> E registerElement(E element) {
		elements().add(element);
		return element;
	}
}
