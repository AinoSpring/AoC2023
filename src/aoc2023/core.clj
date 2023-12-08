(ns aoc2023.core
  (:gen-class)
  (:require aoc2023.day1)
  (:require aoc2023.day1e)
  (:require aoc2023.day2)
  (:require aoc2023.day2e)
  (:require aoc2023.day3)
  (:require aoc2023.day3e)
  (:require aoc2023.day4)
  (:require aoc2023.day4e)
  (:require aoc2023.loader)
  )

(defn -main
  [& args]
  (println (aoc2023.day1/solve (aoc2023.loader/load-data "day1")))
  (println (aoc2023.day1e/solve (aoc2023.loader/load-data "day1")))
  (println (aoc2023.day2/solve (aoc2023.loader/load-data "day2")))
  (println (aoc2023.day2e/solve (aoc2023.loader/load-data "day2")))
  (println (aoc2023.day3/solve (aoc2023.loader/load-data "day3")))
  (println (aoc2023.day3e/solve (aoc2023.loader/load-data "day3")))
  (println (aoc2023.day4/solve (aoc2023.loader/load-data "day4")))
  (println (aoc2023.day4e/solve (aoc2023.loader/load-data "day4")))
  )
