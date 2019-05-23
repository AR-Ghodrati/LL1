package Handlers

import Listener.FirstTableListener
import Listener.LL1Listener
import Listener.ParserListener
import Utils.GeneratorUtil
import Utils.State
import Utils.Terminal
import javax.swing.table.DefaultTableModel

object MainHandler {

    fun run(
        rules: HashMap<State, Set<Pair<State, Int>>>
        , nonTerminal: Set<String>
        , Terminal: Set<Terminal>
        , follows: HashMap<String, Set<String>>
        , firsts: HashMap<String, HashSet<String>>
        , listener: LL1Listener
    ) {


        val data: HashMap<Pair<State, State>, Int> = HashMap()
        val tableData: HashMap<State, MutableList<Int>> = HashMap()
        val tableLHS: HashSet<State> = HashSet()


        println("rules : $rules")
        println("nonTerminal : $nonTerminal")
        println("Terminal : $Terminal")
        println("follows : $follows")
        println("firsts : $firsts")


        val table = DefaultTableModel()


        rules.forEach { LHS, RHS ->
            RHS.forEach { Rule ->
                val first = GeneratorUtil.getChildren(Rule.first).first()

                if (Terminal.any { it == first })
                    data[LHS to first] = Rule.second
                else {
                    val _firsts = firsts[first]
                    _firsts?.forEach {
                        data[LHS to it] = Rule.second
                    }
                }
            }
        }

        //println("Data : $data")


        // Generate NonTerminal
        data.keys.forEach {
            if (!tableLHS.contains(it.first)) tableLHS.add(it.first)
        }

        table.addColumn("", tableLHS.toTypedArray()) // Add NonTerminal


        // Generate Table Data
        data.forEach { LHS, RHS ->
            if (!tableData.containsKey(LHS.second)) tableData[LHS.second] = FillList(nonTerminal.size)
            tableData[LHS.second]?.set(tableLHS.indexOf(LHS.first), RHS)
        }


        //println("tableData : $tableData")

        // Set Table Data
        tableData.forEach { Name, ColumnSet ->

            // Remove MIN_VALUE in Table
            val Column: MutableList<Any> = ArrayList()
            for (i in ColumnSet) {
                if (i == Int.MIN_VALUE) Column.add("")
                else Column.add(i)
            }

            table.addColumn(Name, Column.toTypedArray())
        }

        listener.done(table, data)
    }


    fun check(
        data: String
        , start: State
        , table: HashMap<Pair<State, State>, Int>
        , rules: HashMap<State, Set<Pair<State, Int>>>
        , listener: ParserListener
    ) {

        var ruleStep = "$start $".trim()
        var inputStep = "$data $".trim()

        print("\n\n\n\n")
        println("data : $inputStep")
        println("start : $ruleStep")
        println("table : $table")
        println("rules : $rules")


        while (true) {

            val RF = getFirst(ruleStep)
            val IF = getFirst(inputStep)

            println("RF : $RF")
            println("IF : $IF")

            if (ruleStep == "$" && inputStep == "$") {
                listener.onResult(true)
                break
            } else if (ruleStep == "$" && inputStep != "$") {
                listener.onResult(false)
                break
            } else if (RF == IF) {
                ruleStep = ruleStep.removePrefix(RF).trim()
                inputStep = inputStep.removePrefix(IF).trim()
            } else {
                val nextRuleNum = table[RF to IF]
                println("nextRuleNum : $nextRuleNum")

                if (nextRuleNum == null) {
                    listener.onResult(false)
                    break
                }

                ruleStep = ruleStep.removePrefix(RF).trim()
                println("RemovePrefixRuleStep : $ruleStep")
                ruleStep = String("${getRuleValue(rules, nextRuleNum)} $ruleStep".toByteArray())
                println("NewRuleStep : $ruleStep")

            }
        }
    }

    private fun getFirst(str: String): String {
        return str.split(' ').first()
    }

    private fun getRuleValue(
        rules: HashMap<State, Set<Pair<State, Int>>>
        , ruleNum: Int
    )
            : String {
        var ret = ""
        for ((_, RHS) in rules) {
            for (rule in RHS) {
                if (rule.second == ruleNum) {
                    ret = rule.first
                    break
                }
                if (ret.isNotEmpty()) break
            }
        }
        return ret.trim()

    }

    private fun FillList(count: Int): MutableList<Int> {
        val list: MutableList<Int> = ArrayList()
        for (i in 0 until count) list.add(Int.MIN_VALUE)
        return list
    }


    fun generateFirstTable(firsts: HashMap<State, HashSet<State>>, listener: FirstTableListener) {
        val def = DefaultTableModel()
        firsts.forEach { t, u ->
            def.addColumn(t, u.toArray())
        }
        listener.onDone(def)
    }

    fun generateFollowTable(follow: HashMap<State, Set<State>>, listener: FirstTableListener) {
        val def = DefaultTableModel()
        follow.forEach { t, u ->
            def.addColumn(t, u.toTypedArray())
        }
        listener.onDone(def)
    }
}