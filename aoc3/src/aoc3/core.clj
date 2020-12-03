(ns aoc3.core)

(defn sol-part
  [m r d]
  (let [lines (clojure.string/split-lines m)]
    (->> (range 0 (count lines) d)
         (map #(vector % (rem (* r (/ % d)) (-> lines first count))))
         (map #(nth (nth lines (first %)) (second %)))
         (filter #{\#})
         (count))))

(defn sol1
  [m]
  (sol-part m 3 1))

(defn sol2 
  [m]
  (->> [[1 1][3 1][5 1][7 1][1 2]]
       (map #(sol-part m (first %) (second %)))
       (reduce *)))

(comment
  (sol1 (slurp "resources/input.txt")) ;=> 211
  (sol2 (slurp "resources/input.txt")));=> 3584591857