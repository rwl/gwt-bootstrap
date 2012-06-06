package com.github.gwtbootstrap.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DirectionalTextHelper;

/**
 * RadioButton widgets.
 * <p>
 * Re-design for Bootstrap.
 * </p>
 * 
 * @since 2.0.3.0
 * @author ohashi keisuke
 */
public class RadioButton extends CheckBox {

	public static final DirectionEstimator DEFAULT_DIRECTION_ESTIMATOR = DirectionalTextHelper.DEFAULT_DIRECTION_ESTIMATOR;

	private Boolean oldValue;

	/**
	 * Creates a new radio associated with a particular group name. All radio
	 * buttons associated with the same group name belong to a
	 * mutually-exclusive set.
	 * 
	 * Radio buttons are grouped by their name attribute, so changing their name
	 * using the setName() method will also change their associated group.
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 */
	@UiConstructor
	public RadioButton(String name) {
		super(DOM.createInputRadio(name));

		sinkEvents(Event.ONCLICK);
		sinkEvents(Event.ONMOUSEUP);
		sinkEvents(Event.ONBLUR);
		sinkEvents(Event.ONKEYDOWN);
	}

	/**
	 * Creates a new radio associated with a particular group, and initialized
	 * with the given HTML label. All radio buttons associated with the same
	 * group name belong to a mutually-exclusive set.
	 * 
	 * Radio buttons are grouped by their name attribute, so changing their name
	 * using the setName() method will also change their associated group.
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's html label
	 */
	public RadioButton(String name,
		SafeHtml label) {
		this(name, label.asString(), true);
	}

	/**
	 * @see #RadioButton(String, SafeHtml)
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's html label
	 * @param dir
	 *            the text's direction. Note that {@code DEFAULT} means
	 *            direction should be inherited from the widget's parent
	 *            element.
	 */
	public RadioButton(String name,
		SafeHtml label,
		Direction dir) {
		this(name);
		setHTML(label, dir);
	}

	/**
	 * @see #RadioButton(String, SafeHtml)
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's html label
	 * @param directionEstimator
	 *            A DirectionEstimator object used for automatic direction
	 *            adjustment. For convenience,
	 *            {@link #DEFAULT_DIRECTION_ESTIMATOR} can be used.
	 */
	public RadioButton(String name,
		SafeHtml label,
		DirectionEstimator directionEstimator) {
		this(name);
		setDirectionEstimator(directionEstimator);
		setHTML(label.asString());
	}

	/**
	 * Creates a new radio associated with a particular group, and initialized
	 * with the given HTML label. All radio buttons associated with the same
	 * group name belong to a mutually-exclusive set.
	 * 
	 * Radio buttons are grouped by their name attribute, so changing their name
	 * using the setName() method will also change their associated group.
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's label
	 */
	public RadioButton(String name,
		String label) {
		this(name);
		setText(label);
	}

	/**
	 * @see #RadioButton(String, SafeHtml)
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's label
	 * @param dir
	 *            the text's direction. Note that {@code DEFAULT} means
	 *            direction should be inherited from the widget's parent
	 *            element.
	 */
	public RadioButton(String name,
		String label,
		Direction dir) {
		this(name);
		setText(label, dir);
	}

	/**
	 * @see #RadioButton(String, SafeHtml)
	 * 
	 * @param name
	 *            the group name with which to associate the radio button
	 * @param label
	 *            this radio button's label
	 * @param directionEstimator
	 *            A DirectionEstimator object used for automatic direction
	 *            adjustment. For convenience,
	 *            {@link #DEFAULT_DIRECTION_ESTIMATOR} can be used.
	 */
	public RadioButton(String name,
		String label,
		DirectionEstimator directionEstimator) {
		this(name);
		setDirectionEstimator(directionEstimator);
		setText(label);
	}

	/**
	 * Creates a new radio button associated with a particular group, and
	 * initialized with the given label (optionally treated as HTML). All radio
	 * buttons associated with the same group name belong to a
	 * mutually-exclusive set.
	 * 
	 * Radio buttons are grouped by their name attribute, so changing their name
	 * using the setName() method will also change their associated group.
	 * 
	 * @param name
	 *            name the group with which to associate the radio button
	 * @param label
	 *            this radio button's label
	 * @param asHTML
	 *            <code>true</code> to treat the specified label as HTML
	 */
	public RadioButton(String name,
		String label,
		boolean asHTML) {
		this(name);
		if (asHTML) {
			setHTML(label);
		} else {
			setText(label);
		}
	}

	/**
	 * Overridden to send ValueChangeEvents only when appropriate.
	 */
	@Override
	public void onBrowserEvent(Event event) {

		switch (DOM.eventGetType(event)) {
		case Event.ONMOUSEUP:
		case Event.ONBLUR:
		case Event.ONKEYDOWN:
			// Note the old value for onValueChange purposes (in ONCLICK case)
			oldValue = getValue();
			break;

		case Event.ONCLICK:
			EventTarget target = event.getEventTarget();
			
			if (Element.is(target) 
				&& !Element.as(target).getTagName().toUpperCase().equals("INPUT")
				&& asLabel().isOrHasChild(Element.as(target))) {
				GWT.log("test");
				// They clicked the label. Note our pre-click value, and
				// short circuit event routing so that other click handlers
				// don't hear about it
				oldValue = getValue();
				return;
			}

			// It's not the label. Let our handlers hear about the
			// click...
			super.onBrowserEvent(event);
			// ...and now maybe tell them about the change
			ValueChangeEvent.fireIfNotEqual(RadioButton.this, oldValue, getValue());
			return;
		}
		super.onBrowserEvent(event);
	}

	/**
	 * Change the group name of this radio button.
	 * 
	 * Radio buttons are grouped by their name attribute, so changing their name
	 * using the setName() method will also change their associated group.
	 * 
	 * If changing this group name results in a new radio group with multiple
	 * radio buttons selected, this radio button will remain selected and the
	 * other radio buttons will be unselected.
	 * 
	 * @param name
	 *            name the group with which to associate the radio button
	 */
	@Override
	public void setName(String name) {
		// Just changing the radio button name tends to break groupiness,
		// so we have to replace it. Note that replaceInputElement is careful
		// not to propagate name when it propagates everything else
		replaceInputElement(DOM.createInputRadio(name));
	}

	@Override
	public void sinkEvents(int eventBitsToAdd) {
		super.sinkEvents(eventBitsToAdd);
	}

	/**
	 * No-op. CheckBox's click handler is no good for radio button, so don't use
	 * it. Our event handling is all done in {@link #onBrowserEvent}
	 */
	@Override
	protected void ensureDomEventHandlers() {
	}
}
