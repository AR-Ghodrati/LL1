package Handlers

import Listener.FirstTableListener
import Listener.LL1Listener
import Utils.State
import Utils.Terminal
import java.util.*
import javax.swing.table.DefaultTableModel

object MainHandler {

    fun run(
        rules: HashMap<State, Set<State>>
        , nonTerminal: Set<String>
        , Terminal: Set<Terminal>
        , follows: HashMap<String, Set<String>>
        , firsts: HashMap<String, HashSet<String>>
        , listener: LL1Listener
    ) {


        println("rules : $rules")
        println("nonTerminal : $nonTerminal")
        println("Terminal : $Terminal")
        println("follows : $follows")
        println("firsts : $firsts")


    }


    fun generateFirstTable(firsts: HashMap<String, HashSet<String>>, listener: FirstTableListener) {
        val def = DefaultTableModel()
        firsts.forEach { t, u ->
            def.addColumn(t, u.toArray())
        }
        listener.onDone(def)
    }

    fun generateFollowTable(follow: HashMap<String, Set<String>>, listener: FirstTableListener) {
        val def = DefaultTableModel()
        follow.forEach { t, u ->
            def.addColumn(t, u.toTypedArray())
        }
        listener.onDone(def)
    }
}