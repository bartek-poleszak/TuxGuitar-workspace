package org.herac.tuxguitar.app.view.component.tab;

import org.herac.tuxguitar.app.TuxGuitar;
import org.herac.tuxguitar.document.TGDocumentContextAttributes;
import org.herac.tuxguitar.graphics.TGPainter;
import org.herac.tuxguitar.graphics.control.TGLayout;
import org.herac.tuxguitar.graphics.control.TGMeasureImpl;
import org.herac.tuxguitar.graphics.control.TGTrackSpacing;
import org.herac.tuxguitar.song.models.TGBeat;
import org.herac.tuxguitar.song.models.TGMeasure;
import org.herac.tuxguitar.song.models.TGTrack;

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
		if (initial.getMeasure().getNumber() < beat.getMeasure().getNumber()) {
			start = initial;
			end = beat;
		}
		else {
			start = beat;
			end = initial;
		}
	}

	public TGBeat getStartBeat() {
		return start;
	}

	public TGBeat getEndBeat() {
		return end;
	}

	public void paintSelectedArea(TGLayout viewLayout, TGPainter painter) {
		int startMeasureNumber = start.getMeasure().getNumber();
		int endMeasureNumber = end.getMeasure().getNumber();
		TGTrack track = initial.getMeasure().getTrack();
		for (int i = startMeasureNumber-1; i < endMeasureNumber; i++) {
			TGMeasureImpl measure = (TGMeasureImpl) track.getMeasure(i);
			float scale = viewLayout.getScale();
			float width = measure.getWidth(viewLayout) + measure.getSpacing();
			float y1 = measure.getPosY();
			float y2 = measure.getPosY();
			int style = viewLayout.getStyle();
			if ((style & (TGLayout.DISPLAY_SCORE | TGLayout.DISPLAY_TABLATURE)) == (TGLayout.DISPLAY_SCORE | TGLayout.DISPLAY_TABLATURE)) {
				y1 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_SCORE_MIDDLE_LINES) - viewLayout.getScoreLineSpacing());
				y2 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_TABLATURE) + measure.getTrackImpl().getTabHeight() + viewLayout.getStringSpacing());
			} else if ((style & TGLayout.DISPLAY_SCORE) != 0) {
				y1 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_SCORE_MIDDLE_LINES) - viewLayout.getScoreLineSpacing());
				y2 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_SCORE_MIDDLE_LINES) + (viewLayout.getScoreLineSpacing() * 5));
			} else if ((style & TGLayout.DISPLAY_TABLATURE) != 0) {
				y1 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_TABLATURE) - viewLayout.getStringSpacing());
				y2 += (measure.getTs().getPosition(TGTrackSpacing.POSITION_TABLATURE) + measure.getTrackImpl().getTabHeight() + viewLayout.getStringSpacing());
			}
			viewLayout.setMeasurePlayingStyle(painter);
			painter.setLineWidth(1f * scale);
			painter.setBackground(painter.createColor(0, 0, 255));
			painter.initPath();
			painter.setAntialias(false);
			painter.addRectangle(measure.getPosX() + (5f * scale), y1, width - (10f * scale), (y2 - y1));
			painter.closePath();
		}
	}
}
