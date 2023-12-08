(ns aoc2023.day2e
  (:require clojure.string)
  )

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

(defn get-max [game key]
  (apply max
    (for
      [set (:sets game)]
      (if (contains? set key)
        (key set)
        0
        )
      )
    )
  )

(defn get-power [game]
  (reduce *
    (for [key [:red :green :blue]]
      (get-max game key)
      )
    )
  )

(defn solve [input]
  (reduce +
    (for
      [line (filter #(-> % clojure.string/blank? not) input)]
      (-> line parse-line get-power)
      )
    )
  )
