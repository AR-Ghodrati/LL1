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

    fun getChildren(state: State): Set<State> {
        return state.trim().split(' ').toSet()
    }


}