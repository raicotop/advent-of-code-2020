(ns aoc9.core)

(defn sum-of-previous?
  [[xs x]]
  (let [sums (->> (for [i xs
                        j xs
                        :when (< i j)]
                    [i j])
                  (map #(reduce + %))
                  (set))]
    (contains? sums x)))              

(defn sol
  [s preamble]
  (let [numbers (->> (clojure.string/split-lines s)
                     (map #(Long/parseLong %))
                     (vec))]
    (->> (range preamble (count numbers))
         (map #(vector (->> (range (- % preamble) %)
                            (map (fn [i] (nth numbers i))))
                       %))
         (remove #(sum-of-previous? (vector (first %) (nth numbers (second %)))))
         (map second)
         (#(vector numbers (first %))))))

(defn sol1
  [s preamble]
  (->> (sol s preamble)
       (#(nth (first %) (second %)))))

(defn sol2
  [s preamble]
  (let [[numbers i] (->> (sol s preamble))]                      
    (->> (for [x (range i)
               y (range i)
               :when (< x (dec y))]
           [x y])
         (map #(range (first %) (second %)))
         (map #(map (fn [i] (nth numbers i)) %))
         (filter #(= (nth numbers i)
                     (reduce + %)))
         (first)
         (#(vector (reduce min %) (reduce max %)))
         (reduce +))))

(comment
 (sol1 (slurp "resources/input.txt") 25) ;=> 105950735
 (sol2 (slurp "resources/input.txt") 25));=> 13826915