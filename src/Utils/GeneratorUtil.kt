package Utils


object GeneratorUtil {

    fun GenerateFrists(rules: HashMap<State, MutableList<State>>, nonTerminal: MutableList<State>): FirstTable {

        var table: FirstTable = ArrayList()

        rules.forEach { leftSide, rightSide ->

            //println(leftSide + rightSide)
            rightSide.forEach { itemRight ->

                //getChildren(itemRight).first() for FirstSet
                val first = getChildren(itemRight).first()

                if (first !in nonTerminal) {
                    if (!table.any { it.containsKey(leftSide) }) { // Create New Item in Table
                        val f = First().apply {
                            put(leftSide, ArrayList())
                        }
                        table.add(f)
                    }

                    if (first != "ε")
                        table
                            .find { it.containsKey(leftSide) }
                            ?.get(leftSide)
                            ?.add(first)

                } else {
                    var isNotFound = true
                    var First = first

                    while (isNotFound)
                        rules[First]?.forEach { states ->
                            //getChildren(itemRight).first() for FirstSet
                            First = getChildren(states).first()
                            if (First !in nonTerminal) {
                                if (!table.any { it.containsKey(leftSide) }) { // Create New Item in Table
                                    val f = First().apply {
                                        put(leftSide, ArrayList())
                                    }
                                    table.add(f)
                                }

                                if (First != "ε")
                                    table
                                        .find { it.containsKey(leftSide) }
                                        ?.get(leftSide)
                                        ?.add(First)

                                isNotFound = false

                            }
                        }
                }
            }
        }
        return table
    }

    private fun getChildren(state: State): MutableList<State> {
        return state.split(' ').toMutableList()
    }


}