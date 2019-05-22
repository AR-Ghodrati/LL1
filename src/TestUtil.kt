import Utils.GeneratorUtil

fun main() {


    val rules = hashMapOf(
        "Program" to setOf("Statement")
        , "Statement" to setOf(
            "if Expression then Block",
            "while Expression do Block",
            "Expression"
        )
        , "Expression" to setOf(
            "Term => identifier",
            "isZero? Term",
            "not Expression",
            "++ identifier",
            "-- identifier"
        )
        , "Term" to setOf("identifier", "constant")
        , "Block" to setOf("Statement", "{ Statements }")
        , "Statements" to setOf("Statement", "Statements", "ε")
    )


    val nonTerminal = GeneratorUtil.generateNonTerminal(rules)
    val t = GeneratorUtil.generateFirst(rules, nonTerminal)

    print(t)
}