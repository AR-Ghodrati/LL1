package Utils

typealias State = String
typealias Terminal = String

typealias Rules = HashMap<State, HashSet<State>>
typealias FirstTable = HashMap<State, HashSet<State>>
typealias FollowTable = HashMap<State, HashSet<State>>
