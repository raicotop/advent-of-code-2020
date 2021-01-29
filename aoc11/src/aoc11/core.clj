(ns aoc11.core)

(def task 
  "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")

(defn create-model-table
  [s]
  (->> (clojure.string/split-lines s)
       (map vec)))

(defn create-new-model
  [old-model]
  (let [max-x (count (first old-model))
        max-y (count old-model)
        nes (for [y (range max-y)
                  x (range max-x)]
              (->> (for [addx (range -1 2)
                         addy (range -1 2)
                         :when (not= addx addy 0)]
                     [addx addy])
                   (map #(vector (+ x (first %)) (+ y (second %))))
                   (map (fn [[locx locy]]
                          (if (and
                               (< -1 locx max-x)
                               (< -1 locy max-y))
                            (nth (nth old-model locy) locx)
                            \.)))
                   (frequencies)
                   (vector (nth (nth old-model y) x))))]
    (->> (map
          (fn [[c m]]
            (cond
              (and (= c \L) (= 0 (get m \# 0))) \#
              (and (= c \#) (<= 4 (get m \# 0))) \L
              :else c))
          nes)
         (partition (count (first old-model)))
         (map vec))))
    

(defn sol1-table
  [s]
  (let [model (create-model-table s)]
    (loop [old-model model]
      (let [new-model (create-new-model old-model)]
        (if (= old-model new-model)
          (->> (flatten old-model)
               (filter #{\#})
               (count))          
          (recur new-model))))))

(defn seat-in-direction
  [model [x y] [direction-x direction-y]]
  (let [max-x (count (first model))
        max-y (count model)]
    (loop [d-x direction-x
           d-y direction-y]
      (let [new-x (+ x d-x)
            new-y (+ y d-y)
            sym (if (and
                     (< -1 new-x max-x)
                     (< -1 new-y max-y))
                  (nth (nth model new-y) new-x)
                  :out-of-bounds)]
        (condp = sym
          :out-of-bounds \.
          \# \#
          \L \L
          \. (recur (+ d-x direction-x)
                    (+ d-y direction-y)))))))

(defn create-new-model-2
  [old-model]
  (let [max-x (count (first old-model))
        max-y (count old-model)
        nes (for [y (range max-y)
                  x (range max-x)]
              (->> (for [addx (range -1 2)
                         addy (range -1 2)
                         :when (not= addx addy 0)]
                     [addx addy])
                   (map #(seat-in-direction old-model [x y] %))
                   (frequencies)
                   (vector (nth (nth old-model y) x))))]
    (->> (map
          (fn [[c m]]
            (cond
              (and (= c \L) (= 0 (get m \# 0))) \#
              (and (= c \#) (<= 5 (get m \# 0))) \L
              :else c))
          nes)
         (partition (count (first old-model)))
         (map vec))))


(defn sol2-table
  [s]
  (let [model (create-model-table s)]
    (loop [old-model model]
      (let [new-model (create-new-model-2 old-model)]
        (if (= old-model new-model)
          (->> (flatten old-model)
               (filter #{\#})
               (count))
          (recur new-model))))))
                          
(comment
 (get {:a 9} :b 0)

 (create-model-table task)
 (create-new-model 
  '([\L \. \L \L \. \L \L \. \L \L]
    [\L \L \L \L \L \L \L \. \L \L]))
 (create-model-table (slurp "resources/input.txt"))
  
 (create-new-model (create-model-table task))

 (sol1-table task)
 (sol1-table (slurp "resources/input.txt")) ;=> 2346

 (sol2-table task)
 (sol2-table (slurp "resources/input.txt")) ;=> 2111

 (sort [[0 0] [0 1] [2 2] [3 1] [1 3]])

 (print-model (create-model task))
 (print-model (create-model (slurp "resources/input.txt"))))