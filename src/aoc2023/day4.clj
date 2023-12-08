(ns aoc2023.day4
  (:require clojure.string)
  (:require clojure.math)
  )

(defn parse-numbers [numbers]
  (for [number-string (clojure.string/split numbers #" ")] (read-string number-string))
  )

(defn parse-line [line]
  (let [parts (clojure.string/split (clojure.string/replace line #" +" " ") #"((: )| [\|] )")]
    {:winning-numbers (parse-numbers (nth parts 1)) :chosen-numbers (parse-numbers (nth parts 2))}
    )
  )

(defn is-winning-number [card number]
  (some #(= % number) (:winning-numbers card))
  )

(defn eval-card [card]
  (let [winning-count (count (filter #(is-winning-number card %) (:chosen-numbers card)))]
    (if (= winning-count 0) 
      0
      (clojure.math/pow 2 (- winning-count 1))
      )
    )
  )

(defn solve [input]
  (int (reduce + (for [line (filter #(-> % clojure.string/blank? not) input)] (-> line parse-line eval-card))))
  )
