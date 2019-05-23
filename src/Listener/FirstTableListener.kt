package Listener

import javax.swing.table.DefaultTableModel

interface FirstTableListener {
    fun onDone(model: DefaultTableModel)
}