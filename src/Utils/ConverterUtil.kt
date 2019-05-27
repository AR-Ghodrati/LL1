package Utils

object ConverterUtil {

    fun toRules(rules: MutableList<String>): HashMap<State, Set<Pair<State, Int>>> {
        val ret: HashMap<State, Set<Pair<State, Int>>> = HashMap()
        var ruleNumber = 0

        rules.forEach { rule ->
            val data: HashSet<Pair<State, Int>> = HashSet()
            val RHS = rule.split('→').last().split('|')

            RHS.forEach {
                data.add(it to (++ruleNumber))
            }

            ret[rule.split('→').first().trim()] = data
        }
        return ret
    }

    fun getStartRule(rules: MutableList<String>): State {
        rules.forEach { rule ->
            return rule.split('→').first().trim()
        }
        return ""
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