package Utils

import java.util.HashSet
import kotlin.collections.HashMap
import kotlin.collections.MutableList
import kotlin.collections.Set
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.last
import kotlin.collections.set
import kotlin.collections.toSet

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
        rules.forEachIndexed { _, s ->
            rule += " $s \n"
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