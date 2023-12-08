(ns aoc2023.day4e
  (:require clojure.string)
  (:require clojure.math)
  )

(defn parse-numbers [numbers]
  (for [number-string (clojure.string/split numbers #" ")] (read-string number-string))
  )

(defn parse-line [line]
  (let [parts (clojure.string/split (clojure.string/replace line #" +" " ") #"((: )| [\|] )")]
    {:winning-numbers (parse-numbers (nth parts 1)) :chosen-numbers (parse-numbers (nth parts 2)) :idx (-> (clojure.string/split (first parts) #" ") second read-string) :copies [] :count 1}
    )
  )

(defn parse-input [input]
  (for [line input] (parse-line line))
  )

(defn is-winning-number [card number]
  (some #(= % number) (:winning-numbers card))
  )

(defn eval-card [card]
  (count (filter #(is-winning-number card %) (:chosen-numbers card)))
  )

(defn get-card-by-idx [cards idx]
  (loop [c-idx 0]
    (let [card (nth cards c-idx)]
      (if (= (:idx card) idx)
        card
        (recur (inc c-idx))
        )
      )
    )
  )

(defn eval-copies [cards]
  (loop [new-cards cards c-idx 0]
    (if (= (count cards) c-idx)
      new-cards
      (let [card (nth cards c-idx) value (eval-card (nth cards c-idx))]
        (recur (for [c-card new-cards] (if (< (:idx card) (:idx c-card) (+ (:idx card) value 1))
          (assoc c-card :copies (conj (:copies c-card) (:idx card)))
          c-card
          )) (inc c-idx))
        )
      )
    )
  )

(defn eval-cards [cards]
  (loop [c-cards (sort-by :idx cards) c-idx 0]
    (if (= (count cards) c-idx)
      c-cards
      (recur (assoc (vec c-cards) c-idx (assoc (nth c-cards c-idx) :count (inc (reduce + (for [copy (:copies (nth c-cards c-idx))] (:count (get-card-by-idx c-cards copy))))))) (inc c-idx))
      )
    )
  )

(defn solve [input]
  (reduce + (for [card (-> (filter #(-> % clojure.string/blank? not) input) parse-input eval-copies eval-cards)] (:count card)))
  ;(int (reduce + (for [line (filter #(-> % clojure.string/blank? not) input)] (-> line parse-line eval-card))))
  )
