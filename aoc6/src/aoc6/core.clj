(ns aoc6.core
  (:require clojure.set))

(defn group-answers1
  [answers]
  (->> (clojure.string/replace answers #"\n" "")
       (set)))

(defn group-answers2
  [answers]
  (->> (clojure.string/split-lines answers)
       (map set)
       (reduce clojure.set/intersection)))

(defn sol
  [s group-answers]
  (->> (clojure.string/split s #"\n\n")
       (map group-answers)
       (map count)
       (reduce +)))

(defn sol1
  [s]
  (sol s group-answers1))

(defn sol2
  [s]
  (sol s group-answers2))

(comment
  (sol1 (slurp "resources/input.txt")) ;=> 6587
  (sol2 (slurp "resources/input.txt")));=> 3235