package Listener

import Utils.State
import javax.swing.table.DefaultTableModel


interface LL1Listener {
    fun done(tableModel: DefaultTableModel, LL1Table: HashMap<Pair<State, State>, Int>)
}