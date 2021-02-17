(ns aoc20.core)

(defn parse
  [s]
  (->> (clojure.string/split-lines s)
       (remove #{""})
       (partition 11)
       (map (fn [rows]
              {(first rows)
               (vector (second rows)
                       (apply str (map first (rest rows)))
                       (last rows)
                       (apply str (map last (rest rows))))}))
       (reduce into)))

(defn have-common-edge?
  [a b]
  (->> (set a)
       (clojure.set/union (set (map #(apply str (reverse %)) a)))
       (clojure.set/intersection (set b))
       (empty?)
       (not)))

(defn neighbors
  [m]
  (->> (map (fn [[k v]]
              (->> (vals m)
                   (filter #(have-common-edge? v %))
                   (remove #{v})
                   (hash-map k)))
            m)
       (reduce into)))

(defn sol-1
  [s]
  (->> (parse s)
       (neighbors)
       (map (fn [[k v]]
              (hash-map k (count v))))
       (reduce into)
       (filter (fn [[k v]] (= v 2)))
       (map first)
       (map #(take-last 5 %))
       (map #(drop-last 1 %))
       (map #(reduce str %))
       (map #(Integer/parseInt %))
       (reduce *)))

(comment

  (sol-1 (slurp "resources/input.txt")) ;=> 17148689442341
 
  ,)
