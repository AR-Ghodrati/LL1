package Utils

typealias State = String
typealias Terminal = String

typealias First = HashMap<State, MutableList<State>>
typealias Follow = HashMap<State, MutableList<State>>

typealias FirstTable = MutableList<First>
typealias FollowTable = MutableList<Follow>
