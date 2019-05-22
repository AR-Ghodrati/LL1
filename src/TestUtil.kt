import Utils.GeneratorUtil

fun main() {

    val nonTerminal = setOf("Program", "Statement", "Expression", "Term", "Block", "Statements")

    val t = GeneratorUtil.generateFirst(
        hashMapOf(
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
        , nonTerminal
    )

    print(t)
}