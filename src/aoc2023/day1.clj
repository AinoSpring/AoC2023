(ns aoc2023.day1
  (:require clojure.string)
  )

(defn parse-line [line]
  (let [digits (re-seq #"\d" line)]
    (read-string (str (first digits) (last digits)))
    )
  )

(defn solve [input]
  (reduce + (for [line (filter (fn [x] (not (clojure.string/blank? x))) input)] (parse-line line)))
  )

