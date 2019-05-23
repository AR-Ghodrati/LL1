package Utils

import java.util.HashSet
import kotlin.collections.HashMap
import kotlin.collections.set

object ConverterUtil {

    fun toRules(rules: MutableList<String>): HashMap<State, Set<State>> {
        val ret: HashMap<State, Set<State>> = HashMap()
        rules.forEach { rule ->
            ret[rule.split('→').first().trim()] = rule
                .split('→')
                .last()
                .trim()
                .split('|')
                .toSet()
        }
        return ret
    }

    fun toFollow(follows: MutableList<String>): HashMap<State, Set<State>> {
        val ret: HashMap<State, Set<State>> = HashMap()
        follows.forEach { follow ->
            ret[follow.split('→').first().trim()] = follow
                .split('→')
                .last()
                .trim()
                .split('|')
                .toSet()
        }
        return ret
    }

    fun toShowRule(rules: MutableList<String>): String {
        var rule = ""
        var ruleNumber = 0

        rules.forEachIndexed { _, s ->
            val LHS = s.split('→').first()
            val RHS = s.split('→').last().split('|')

            var RHSRules = ""

            RHS.forEach { rule ->
                RHSRules += "$rule   (${++ruleNumber})\n | "
            }
            rule += "$LHS → ${RHSRules.trim().removeSuffix("|")}\n"
        }
        return rule
    }

    fun toShowFirsts(firsts: HashMap<String, HashSet<String>>): String {
        var rule = ""
        firsts.forEach { t, u ->
            rule += "$t -> ${u
                .toString()
                .removePrefix("[")
                .removeSuffix("]")}" +
                    " \n"
        }
        return rule
    }


}