package org.hyperskill.simplebankmanager

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerAdapter(context: Context, resource: Int, items: Array<String>) : ArrayAdapter<String>(context, resource, items) {
    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}