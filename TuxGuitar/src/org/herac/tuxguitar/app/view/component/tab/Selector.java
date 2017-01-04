package org.herac.tuxguitar.app.view.component.tab;

import org.herac.tuxguitar.graphics.TGPainter;
import org.herac.tuxguitar.graphics.control.TGLayout;

/**
 * Created by tubus on 20.12.16.
 */
public class Selector {

	private int initialMeasureNumber;
	private int startMeasureNumber;
	private int endMeasureNumber;

	public void initializeSelection(int selectionStartMeasureNumber) {
		System.out.println("Initial selection: " + selectionStartMeasureNumber);
		this.initialMeasureNumber = selectionStartMeasureNumber;
		this.startMeasureNumber = selectionStartMeasureNumber;
		this.endMeasureNumber = selectionStartMeasureNumber;
	}

	public void updateSelection(int selectionMeasureNumber) {
		if (initialMeasureNumber < selectionMeasureNumber) {
			startMeasureNumber = initialMeasureNumber;
			endMeasureNumber = selectionMeasureNumber;
		}
		else {
			startMeasureNumber = selectionMeasureNumber;
			endMeasureNumber = initialMeasureNumber;
		}

		System.out.println("Selection set to " + startMeasureNumber + " - " + endMeasureNumber);
	}

	public int getStartMeasureNumber() {
		return startMeasureNumber;
	}

	public int getEndMeasureNumber() {
		return endMeasureNumber;
	}

	public void paintSelectedArea(TGLayout viewLayout, TGPainter painter) {

	}
}
