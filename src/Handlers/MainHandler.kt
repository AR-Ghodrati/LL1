package Handlers

import Utils.State
import Utils.Terminal

object MainHandler {

    fun run(
        rules: HashMap<State, Set<State>>
        , nonTerminal: Set<Terminal>
        , follows: HashMap<String, Set<String>>
        , firsts: HashMap<String, HashSet<String>>
    ) {


        println("rules : $rules")
        println("nonTerminal : $nonTerminal")
        println("follows : $follows")
        println("firsts : $firsts")


    }
}