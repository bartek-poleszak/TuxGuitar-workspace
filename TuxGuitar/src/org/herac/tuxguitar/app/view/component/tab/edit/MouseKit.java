package org.herac.tuxguitar.app.view.component.tab.edit;

import org.herac.tuxguitar.app.TuxGuitar;
import org.herac.tuxguitar.app.action.impl.edit.tablature.TGMenuShownAction;
import org.herac.tuxguitar.app.action.impl.edit.tablature.TGMouseClickAction;
import org.herac.tuxguitar.app.action.impl.edit.tablature.TGMouseExitAction;
import org.herac.tuxguitar.app.action.impl.edit.tablature.TGMouseMoveAction;
import org.herac.tuxguitar.app.action.impl.selector.TGSetSelectionBeginingAction;
import org.herac.tuxguitar.app.action.impl.selector.TGUpdateSelectionAction;
import org.herac.tuxguitar.app.action.listener.gui.TGActionProcessingListener;
import org.herac.tuxguitar.editor.TGEditorManager;
import org.herac.tuxguitar.editor.action.TGActionProcessor;
import org.herac.tuxguitar.player.base.MidiPlayer;
import org.herac.tuxguitar.ui.event.*;
import org.herac.tuxguitar.ui.resource.UIPosition;
import org.herac.tuxguitar.util.TGContext;

public class MouseKit implements UIMouseDownListener, UIMouseUpListener, UIMouseDragListener, UIMouseMoveListener,
								UIMouseExitListener, UIMenuShowListener, UIMenuHideListener {
	
	private EditorKit kit;
	private UIPosition position;
	private boolean menuOpen;
	
	public MouseKit(EditorKit kit){
		this.kit = kit;
		this.position = new UIPosition();
		this.menuOpen = false;
	}
	
	public boolean isBusy() {
		TGContext context = this.kit.getTablature().getContext();
		return (TGEditorManager.getInstance(context).isLocked() || MidiPlayer.getInstance(context).isRunning());
	}
	
	public void executeAction(String actionId, UIPosition position, boolean byPassProcessing) {
		TGActionProcessor tgActionProcessor = new TGActionProcessor(this.kit.getTablature().getContext(), actionId);
		tgActionProcessor.setAttribute(EditorKit.ATTRIBUTE_X, position.getX());
		tgActionProcessor.setAttribute(EditorKit.ATTRIBUTE_Y, position.getY());
		tgActionProcessor.setAttribute(TGActionProcessingListener.ATTRIBUTE_BY_PASS, byPassProcessing);
		tgActionProcessor.process();
	}
	
	public void onMouseDown(UIMouseEvent event) {
		this.position.set(event.getPosition());
		this.executeAction(TGSetSelectionBeginingAction.NAME, event.getPosition(), false);
	}

	public void onMouseUp(UIMouseEvent event) {
		this.position.set(event.getPosition());
		this.executeAction(TGMouseClickAction.NAME, event.getPosition(), false);
	}

	public void onMouseDrag(UIMouseEvent event) {
//		this.position.set(event.getPosition());
//		this.executeAction(TGUpdateSelectionAction.NAME, event.getPosition(), false);
	}

	public void onMouseMove(UIMouseEvent event) {
		if(!this.menuOpen && this.kit.isMouseEditionAvailable() && !this.isBusy()){
			this.executeAction(TGMouseMoveAction.NAME, event.getPosition(), true);
		}
	}

	public void onMouseExit(UIMouseEvent event) {
		if(!this.menuOpen && this.kit.isMouseEditionAvailable()) {
			this.executeAction(TGMouseExitAction.NAME, event.getPosition(), true);
		}
	}

	public void onMenuShow(UIMenuEvent event) {
		this.menuOpen = true;
		this.executeAction(TGMenuShownAction.NAME, this.position, false);
	}

	public void onMenuHide(UIMenuEvent event) {
		this.menuOpen = false;
		TuxGuitar.getInstance().updateCache(true);
	}
}
