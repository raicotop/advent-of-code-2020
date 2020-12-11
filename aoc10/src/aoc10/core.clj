(ns aoc10.core
   (:require [clojure.math.combinatorics :as combo]))

(defn sol
  [s]
  (->> (clojure.string/split-lines s)
       (map #(Integer/parseInt %))
       (#(conj % 0 (+ 3 (reduce max %))))
       (sort)))

(defn sol1
  [s]
  (->> (sol s)
       (#(map (fn [i] (- (nth % (inc i)) (nth % i)))(range (dec (count %)))))
       (frequencies)
       (vals)
       (reduce *)))

(defn comb
  [[lmin combs rmax]]
  (->> (combo/subsets combs)
       (map #(concat (list lmin) % (list rmax)))
       (map #(->> (range (dec (count %)))
                  (map (fn [i] (- (nth % (inc i))
                                  (nth % i))))))
       (map #(reduce max %))
       (filter #(< % 4))
       (count)))

(defn sol2
  [s]
  (->> (sol s)
       (#(map (fn [i] 
                (vector 
                 (nth % i) 
                 (or (= i 0)
                     (= i (dec (count %))) 
                     (= 3 (- (nth % i) (nth % (dec i))))
                     (= 3 (- (nth % (inc i)) (nth % i))))))                    
              (range (count %))))
       (partition-by second)
       (map (fn [group] (map first group)))
       (#(map (fn [i] (vector (last (nth % (dec i)))
                              (nth % i)
                              (first (nth % (inc i)))))
              (range 1 (dec (count %)) 2)))
       (map comb)
       (reduce *)))

(comment
 (sol1 (slurp "resources/input.txt"))
 (sol2 (slurp "resources/input.txt")))