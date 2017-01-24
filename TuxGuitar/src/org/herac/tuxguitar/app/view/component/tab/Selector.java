package org.herac.tuxguitar.app.view.component.tab;

import org.herac.tuxguitar.graphics.TGPainter;
import org.herac.tuxguitar.graphics.control.TGLayout;
import org.herac.tuxguitar.graphics.control.TGTrackImpl;
import org.herac.tuxguitar.song.models.TGBeat;

/**
 * Created by tubus on 20.12.16.
 */
public class Selector {

	private TGBeat initial;
	private TGBeat start;
	private TGBeat end;

	public void initializeSelection(TGBeat beat) {
		initial = beat;
		start = beat;
		end = beat;
	}

	public void updateSelection(TGBeat beat) {

		if (initial.getMeasure().getNumber() < beat.getMeasure().getNumber() || initialIsEarlierInTheSameMeasure(beat)) {
			start = initial;
			end = beat;
		} else {
			start = beat;
			end = initial;
		}
	}

	private boolean initialIsEarlierInTheSameMeasure(TGBeat beat) {
		return initial.getMeasure().getNumber() == beat.getMeasure().getNumber() && initial.getStart() < beat.getStart();
	}

	public TGBeat getStartBeat() {
		return start;
	}

	public TGBeat getEndBeat() {
		return end;
	}

	public boolean isActive() {
		return start != end;
	}

	public void paintSelectedArea(TGLayout viewLayout, TGPainter painter) {
		if (isActive()) {
			TGTrackImpl track = (TGTrackImpl) initial.getMeasure().getTrack();
			track.paintBeatSelection(viewLayout, painter, start, end);
		}
	}
}
