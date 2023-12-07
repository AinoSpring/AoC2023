(ns aoc2023.loader
  (:require clojure.java.io)
  (:require clojure.string)
  )

(defn load-data [name]
  (clojure.string/split-lines (slurp (clojure.java.io/resource (str name ".txt"))))
  )
