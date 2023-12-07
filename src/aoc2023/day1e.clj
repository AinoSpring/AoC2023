(ns aoc2023.day1e
  (:require clojure.string)
  )

(defonce number-words ["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"])

(defn re-seq-pos [pattern string] 
  (let [m (re-matcher pattern string)] 
    ((fn step [] 
      (when (. m find) 
        (cons {:start (. m start) :end (. m end) :group (. m group)} 
          (lazy-seq (step))
          )
        )
      ))
    )
  )

(defn find-simple-numbers [line]
  (for [match (re-seq-pos #"\d" line)] {:idx (:start match) :value (:group match)})
  )

(defn find-word-numbers [line]
  (for [match (flatten (filter (fn [x] (not (nil? x))) (for [number-word number-words] (re-seq-pos (re-pattern number-word) line))))] {:idx (:start match) :value (str (.indexOf number-words (:group match)))})
  )

(defn find-numbers [line]
  (sort-by (fn [x] (:idx x)) (concat (find-simple-numbers line) (find-word-numbers line)))
  )

(defn parse-line [line]
  (let [numbers (find-numbers line)]
      (read-string (str (:value (first numbers)) (:value (last numbers))))
    )
  )

(defn solve [input]
  (reduce + (for [line (filter (fn [x] (not (clojure.string/blank? x))) input)] (parse-line line)))
  )

