(ns aoc2023.day2
  (:require clojure.string)
  )

(defonce max-values {:red 12 :green 13 :blue 14})

(defn parse-set [set-string]
    (apply merge (for [set-part (clojure.string/split set-string #", ")] (let [set-part-parts (clojure.string/split set-part #" ")]
                                                                          {(case (nth set-part-parts 1)
                                                                              "red" :red
                                                                              "green" :green
                                                                              "blue" :blue
                                                                            )
                                                                            (read-string (nth set-part-parts 0))}
                                                                          )))
  )

(defn parse-sets [sets-string]
  (for [set-string (clojure.string/split sets-string #"; ")] (parse-set set-string))
  )

(defn parse-line [line]
  (let [parts (clojure.string/split line #" ")]
    {:game (read-string (apply str (drop-last (nth parts 1)))) :sets (parse-sets (clojure.string/join " " (subvec parts 2)))}
    )
  )

(defn game-invalid [game max-vals]
  (some 
    (fn [set] 
      (some (fn [key] 
        (if (contains? set key)
          (> (key set) (key max-vals))
          false
          )
        )
      (keys set)
      ))
    (:sets game)
    )
  )

(defn solve [input]
  (reduce + (for [game (filter (fn [game] (not (game-invalid game max-values))) (for [line (filter (fn [x] (not (clojure.string/blank? x))) input)] (parse-line line)))] (:game game)))
  )
