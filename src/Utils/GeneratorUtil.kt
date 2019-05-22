package Utils


object GeneratorUtil {

    fun generateFirst(rules: HashMap<State, Set<State>>, nonTerminal: Set<Terminal>): FirstTable {
        val table: FirstTable = HashMap()

        rules.forEach { leftSide, rightSide ->
            rightSide
                // To Sort Terminal Elements In First Of List
                .sortedBy { state -> nonTerminal.any { it == getChildren(state).first() } }
                .forEach { itemRight ->
                //getChildren(itemRight).first() for FirstSet
                    var first = getChildren(itemRight).first()
                if (first !in nonTerminal) {
                    if (!table.any { it.key == leftSide }) // Create New Item in Table
                        table[leftSide] = HashSet()
                    table[leftSide]?.add(first)
                } else {
                    while (first in nonTerminal) {
                        val neededRule =
                            rules[first]?.sortedBy { state -> nonTerminal.any { it == getChildren(state).first() } }
                        for (rule in neededRule!!) {
                            first = getChildren(rule).first()
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


    private fun getChildren(state: State): Set<State> {
        return state.split(' ').toSet()
    }


}