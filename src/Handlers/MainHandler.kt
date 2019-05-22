package Handlers

import Listener.LL1Listener
import Utils.State
import Utils.Terminal

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
}