(ns aoc5.core)

(defn position
  [s]
  (let [row-part (->> (drop-last 3 s)
                      (map {\B 1 \F 0}))
        col-part (->> (take-last 3 s)
                      (map {\L 0 \R 1}))]
    (->> [row-part col-part] 
         (map #(apply str %))
         (map #(Integer/parseInt % 2)))))
  
(defn ids
  [s]
  (->> (clojure.string/split-lines s) 
       (map position)
       (map (fn [[row col]] (+ col (* 8 row))))))

(defn sol1 
  [s]
  (->> (ids s) 
       (reduce max)))

(defn sol2
  [s]
  (let [sorted-ids (sort (ids s))]
    (->> (range (dec (count sorted-ids)))
         (remove #(= (inc (nth sorted-ids %)) 
                     (nth sorted-ids (inc %))))
         (map #(nth sorted-ids %))
         (first)
         (inc))))

(comment
  (sol1 (slurp "resources/input.txt")) ;=> 816
  (sol2 (slurp "resources/input.txt")));=> 539