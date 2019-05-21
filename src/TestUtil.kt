import Utils.GeneratorUtil

fun main() {

    val nonTerminal = arrayListOf("Program", "Statement", "Expression", "Term", "Block", "Statements")

    val t = GeneratorUtil.GenerateFrists(
        hashMapOf(
            "Program" to mutableListOf("Statement")
            , "Statement" to mutableListOf(
                "if Expression then Block",
                "while Expression do Block",
                "Expression"
            )
            , "Expression" to mutableListOf(
                "Term => identifier",
                "isZero? Term",
                "not Expression",
                "++ identifier",
                "-- identifier"
            )
            , "Term" to mutableListOf("constant")
            , "Block" to mutableListOf("Statement", "{ Statements }")
            , "Statements" to mutableListOf("Statement", "Statements", "ε")
        )
        , nonTerminal
    )

    print(t)
}