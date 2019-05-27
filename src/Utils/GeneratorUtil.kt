package Utils


object GeneratorUtil {

    fun generateFirst(rules: HashMap<State, Set<Pair<State, Int>>>, nonTerminal: Set<Terminal>): FirstTable {
        val table: FirstTable = HashMap()

        rules.forEach { leftSide, rightSide ->
            rightSide
                // To Sort Terminal Elements In First Of List
                .sortedBy { state -> nonTerminal.any { it == getChildren(state.first).first() } }
                .forEach { itemRight ->
                //getChildren(itemRight).first() for FirstSet
                    var first = getChildren(itemRight.first).first()
                if (first !in nonTerminal) {
                    if (!table.any { it.key == leftSide }) // Create New Item in Table
                        table[leftSide] = HashSet()
                    table[leftSide]?.add(first)
                } else {
                    while (first in nonTerminal) {
                        val neededRule =
                            rules[first]?.sortedBy { state -> nonTerminal.any { it == getChildren(state.first).first() } }
                        for (rule in neededRule!!) {
                            first = getChildren(rule.first).first()
                            if (first !in nonTerminal) {
                                if (!table.any { it.key == leftSide })  // Create New Item in Table
                                    table[leftSide] = HashSet()
                                table[leftSide]?.add(first)
                            } else break
                        }
                    }
                }
            }
        }

        return table
    }

    fun generateFollow(
        rules: HashMap<State, Set<Pair<State, Int>>>
        , nonTerminal: Set<Terminal>
        , startRule: State
        , firstTable: FirstTable
    ): FollowTable {

        val table: FollowTable = HashMap()

        if (Have_Lambda(firstTable)) {

            table[startRule] = HashSet()
            table[startRule]?.add("$")

            rules.forEach { LHS, _ ->
                rules.forEach { _LHS, _RHS ->
                    _RHS.forEach { Rule ->

                        if (Rule.first.contains(LHS) && LHS != _LHS) {
                            if (!table.containsKey(LHS)) table[LHS] = HashSet()
                            val follow = getFollow(_LHS to Rule.first, firstTable, nonTerminal, LHS)

                            if (follow != null)
                                table[LHS]?.addAll(follow)
                            else
                                table[_LHS]?.let { table[LHS]?.addAll(it) }
                        }
                    }
                }
            }
        }

        return table
    }

    fun generateNonTerminal(rules: HashMap<State, Set<Pair<State, Int>>>): HashSet<State> {
        return rules.keys.toHashSet()
    }


    fun generateTerminal(rules: HashMap<State, Set<Pair<State, Int>>>): HashSet<State> {
        val ret: HashSet<Terminal> = HashSet()
        rules.values.forEach { states ->
            states.forEach { state ->
                if (getChildren(state.first).first()[0].isLowerCase()
                    || !getChildren(state.first).first()[0].isLetter()
                )
                    ret.add(getChildren(state.first).first())
            }
        }
        return ret
    }


    fun generateColumns(rules: HashMap<State, Set<Pair<State, Int>>>): MutableList<String> {
        val terminal = generateTerminal(rules)
        terminal.add("$")
        return terminal.toMutableList()
    }

    fun getChildren(state: State): MutableList<State> {
        return state.trim().split(' ').toMutableList()
    }

    private fun Have_Lambda(firstTable: FirstTable): Boolean {
        for ((_, RHS) in firstTable)
            if (RHS.contains("ε"))
                return true
        return false
    }

    private fun getFollow(
        rule: Pair<State, State>
        , firstTable: FirstTable
        , nonTerminal: Set<Terminal>
        , itemFollow: State
    ): Set<State>? {


        var next = ""
        val children = getChildren(rule.second)

        if (children.size > children.indexOf(itemFollow) + 1
            && children.indexOf(itemFollow) >= 0
        )
            next = getChildren(rule.second)[children.indexOf(itemFollow) + 1]

        if (next.isNotEmpty()) {
            return if (nonTerminal.any { it == next })
                firstTable[next]?.apply { remove("ε") }?.toSet()
            else
                setOf(next)

        }
        return null
    }


}