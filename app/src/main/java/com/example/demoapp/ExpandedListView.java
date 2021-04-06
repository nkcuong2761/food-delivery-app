/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
 * Section: Section 2
 * Date: 4/4/2021
 * Time: 11:38 AM
 *
 * Project: DemoApp
 * Package: com.example.demoapp
 * Class: ExpandedListView
 *
 * Description:
 *
 * ****************************************
 */
package com.example.demoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExpandedListView extends ListView {
	private android.view.ViewGroup.LayoutParams params;
	private int old_count = 0;

	public ExpandedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getCount() != old_count) {
			old_count = getCount();
			params = getLayoutParams();
			params.height = getCount() * (old_count > 0 ? getChildAt(0).getHeight() : 0);
			setLayoutParams(params);
		}

		super.onDraw(canvas);
	}
}
