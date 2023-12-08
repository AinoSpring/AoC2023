(ns aoc2023.day3e
  (:require clojure.string)
  )

(defn parse-line [line idx]
  (let [matcher (re-matcher #"(\d+|[\*])" line)]
    (loop [result []]
      (if (. matcher find)
        (recur (conj result (cond
          (re-matches #"\d+" (. matcher group)) {:is-number true :coord [(. matcher start) idx] :val (read-string (. matcher group))}
          :else {:is-number false :coord [(. matcher start) idx] :val (. matcher group)}
          )))
        result
        )
      )
    )
  )

(defn parse-input [input]
  (flatten (for [idx (-> input count range)]
    (parse-line (nth input idx) idx)
    ))
  )

(defn get-numbers [coords]
  (filter #(:is-number %) coords)
  )

(defn get-gears [coords]
  (filter #(-> % :is-number not) coords)
  )

(defn coord-next-to-symbol [number symbol x-offset]
  (and (< (abs (- (+ (nth (:coord number) 0) x-offset) (nth (:coord symbol) 0))) 2) (< (abs (- (nth (:coord number) 1) (nth (:coord symbol) 1))) 2))
  )

(defn is-next-to-symbol [number symbol]
  (some #(coord-next-to-symbol number symbol %) (-> number :val str count range))
  )

(defn get-adjacent-numbers [gear numbers]
  (filter #(is-next-to-symbol % gear) numbers)
  )

(defn get-gear-ratio [gear numbers]
  (let [numbers (get-adjacent-numbers gear numbers)]
    (if (> (count numbers) 1)
      (* (-> numbers first :val) (-> numbers last :val))
      0
      )
    )
  )

(defn get-gear-ratios [numbers gears]
  (for [gear gears] (get-gear-ratio gear numbers))
  )

(defn solve [input]
  (let [parsed (let [skipped-input (filter #(-> % clojure.string/blank? not) input)] (parse-input skipped-input))]
    (reduce + (get-gear-ratios (get-numbers parsed) (get-gears parsed)))
    )
  )
